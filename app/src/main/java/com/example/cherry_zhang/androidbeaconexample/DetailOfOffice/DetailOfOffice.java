package com.example.cherry_zhang.androidbeaconexample.DetailOfOffice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cherry_zhang.androidbeaconexample.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class DetailOfOffice extends Activity {
    GoogleMap map;

    private final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int FILL_PARENT = ViewGroup.LayoutParams.FILL_PARENT;

    String companyName;
    String price;
    String capacity;
    String latitude;
    String longitude;
    String[] amenities={"aaaaaaaaaaaa","bbb","dddddddddddd","ccccccc","e","fffff","ggg","hhh","jjjjjjjjjjjjj"};
    String[] strings = { "てｓｔ１", "てｓｔ２", "てｓｔ３", "てｓｔ４", "てｓｔ５", "てｓｔ６", "てｓｔ７", "てｓｔ８", "てｓｔ９", "てｓｔ１０", "てｓｔ１１１１１１a", "てｓｔ１１１", "てｓｔ２３４"};

    String Description;

    String officeId;

    boolean isDL;


    TextView textViewCompanyName;
    TextView textViewOpenEndTime;
    TextView textViewPrice;
    TextView textViewCapacity;
    TextView textViewCatchCopy;
    TextView textViewDescription;
    TextView textViewAddress;

    ImageView imageViewHeaderImage;

    LinearLayout amenitiesLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_of_office);

        Intent intent = getIntent();
        if (intent != null) {
            officeId = intent.getStringExtra("officeId");
        } else {
            officeId = "CzHBdgfU62";
        }


        textViewCompanyName = (TextView) findViewById(R.id.CompanyName);
        textViewOpenEndTime = (TextView) findViewById(R.id.OpenTime);
        textViewPrice = (TextView) findViewById(R.id.FeeParDay);
        textViewCapacity = (TextView) findViewById(R.id.capacity);
        textViewCatchCopy = (TextView) findViewById(R.id.catchcopy);
        textViewDescription = (TextView) findViewById(R.id.Description);
        textViewAddress = (TextView) findViewById(R.id.address);


        imageViewHeaderImage = (ImageView) findViewById(R.id.HeaderImage);


        amenitiesLayout = (LinearLayout) findViewById(R.id.amenitiesLayout);







        //Task start
        new CompanyDataGetTask(this).execute();


//parse test


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.detail_of_office, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class CompanyDataGetTask extends AsyncTask<Void, Void, Void> {

        private List<ParseObject> todos;
        ProgressDialog dialog;
        Context context;

        public CompanyDataGetTask(Context context) {
            super();
            this.context = context;
            Parse.initialize(context, "TsVbzF7jXzY1C0o86V2xxAxgSxvy4jmbyykOabPl", "VzamwWm4WswbDFxrxos2oSerQ2Av4RM6J5mNnNgr");
            ParseUser.enableAutomaticUser();
            ParseACL defaultACL = new ParseACL();
            // Optionally enable public read access.
            // defaultACL.setPublicReadAccess(true);
            ParseACL.setDefaultACL(defaultACL, true);
        }

        @Override
        protected Void doInBackground(Void... v) {
            isDL = true;
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Office");
            query.whereEqualTo("objectId", officeId);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> officeDataList, ParseException e) {
                    if (e == null) {
                        ParseObject obj = officeDataList.get(0);

                        textViewCompanyName.setText(obj.getString("name"));
                        textViewOpenEndTime.setText(obj.getString("hours"));
                        textViewPrice.setText("$" + obj.getNumber("price").toString());
                        textViewCapacity.setText(obj.getString("size"));
                        textViewCatchCopy.setText(obj.getString("catchCopy"));
                        textViewDescription.setText(obj.getString("description"));
                        textViewAddress.setText(obj.getString("address"));
                        MapFragment fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

                        //map make

                        map = fragment.getMap();

                        LatLng MY_PLACE = new LatLng(obj.getParseGeoPoint("location").getLatitude(), obj.getParseGeoPoint("location").getLongitude());
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(MY_PLACE)
                                .zoom(17)
                                .bearing(90)
                                .tilt(30)
                                .build();
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        MarkerOptions options = new MarkerOptions();
                        options.position(MY_PLACE);
                        options.title(textViewCompanyName.getText().toString());
                        map.addMarker(options);

                        //headerImg make


                        try {
                            JSONArray cast = obj.getJSONArray("images");
                            ParseQuery<ParseObject> query_image = null;
                            for (int i = 0; i < cast.length(); i++) {
                                JSONObject actor = cast.getJSONObject(i);
                                String name = actor.getString("objectId");
                                l("name=>" + i + ":" + name);


                                //img data get
                                query_image = ParseQuery.getQuery("Image");
                                query_image.whereEqualTo("objectId", name);

                            }

                            query_image.findInBackground(new FindCallback<ParseObject>() {
                                public void done(List<ParseObject> officeImgDataList, ParseException e) {
                                    if (e == null) {
                                        for (int j = 0; j < officeImgDataList.size(); j++) {
//                                            l("imgListData=>" + officeImgDataList.get(j).getParseFile("image").toString());
                                            ParseFile fileObject = (ParseFile) officeImgDataList.get(j).get("image");
                                            fileObject.getDataInBackground(new GetDataCallback() {
                                                public void done(byte[] data, ParseException e) {
                                                    if (e == null) {
                                                        Log.d("test", "We've got data in data.");
                                                        // use data for something
                                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                                        imageViewHeaderImage.setVisibility(View.VISIBLE);
                                                        imageViewHeaderImage.setImageBitmap(bitmap);
                                                        isDL = false;
                                                    } else {
                                                        Log.d("test", "There was a problem downloading the data.");
                                                    }
                                                }
                                            });
                                        }
                                    } else {
                                    }
                                }
                            });
                        } catch (Exception er) {
                            Log.v("aaa", "errororr:" + er.toString());
                        }

                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });


            while (isDL) {
                try {
                    wait(100);
                } catch (Exception eeerr) {
                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(context);
            dialog.setTitle("Please wait");
            dialog.setMessage("Loading data...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            dialog.show();
        }
    }

    public static final void l(String s) {
        Log.v("aaa", "s=>" + s);

    }


}
