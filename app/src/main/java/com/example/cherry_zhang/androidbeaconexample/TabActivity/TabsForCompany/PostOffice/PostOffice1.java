package com.example.cherry_zhang.androidbeaconexample.TabActivity.TabsForCompany.PostOffice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.cherry_zhang.androidbeaconexample.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class PostOffice1 extends android.support.v4.app.Fragment implements LocationListener{

    Button b_continueToPostOffice2;

    // ...
//    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
//    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap map;

    private LatLng nowLatlng;

    EditText name;

    public PostOffice1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_post_office, container, false);

        b_continueToPostOffice2 = (Button) rootView.findViewById(R.id.goToNext);
        b_continueToPostOffice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PostOffice2.class);
                intent.putExtra("address",name.getText().toString());
                startActivity(intent);
            }
        });

        name = (EditText) rootView.findViewById(R.id.addressEdit);
        name.setText("東京都渋谷区南平台町１５−１３　帝都渋谷ビル");
        Button addressButton = (Button) rootView.findViewById(R.id.addressButton);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGetLocation(name.getText().toString());
            }
        });

        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        //システムサービスのLOCATION_SERVICEからLocationManager objectを取得
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //retrieve providerへcriteria objectを生成
        Criteria criteria = new Criteria();

        //Best providerの名前を取得
        String provider = locationManager.getBestProvider(criteria, true);


        //現在位置を取得
        Location location = locationManager.getLastKnownLocation(provider);

        if(location!=null){
            onLocationChanged(location);
        }

        locationManager.requestLocationUpdates(provider, 20000, 0, this);

        nowLatlng =new LatLng(35.6553288,139.6953254);
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(MY_PLACE)
//                .zoom(17)
//                .bearing(90)
//                .tilt(30)
//                .build();

        Marker hamburg = map.addMarker(new MarkerOptions().position(nowLatlng)
                .title("Your Address"));
//

        // Move the camera instantly to hamburg with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(nowLatlng, 17));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);


//
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onGetLocation(String data) {
//        Geocoder gcoder = new Geocoder(getActivity(), Locale.getDefault());
//        List<Address> lstAddr;
        Log.v("aaa", "1a");
        String address = "http://maps.google.com/maps/api/geocode/json?address=" + data + "&sensor=false";
        address = address.replaceAll(" ", "%20");
        Log.v("aaa", "1a1");
//        Log.v("aaa", "1a1::::" + "http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
        new MyAsyncTask(getActivity()).execute(address);


    }

    public JSONObject getLocationInfo(String address, Context context) {
        StringBuilder stringBuilder = new StringBuilder();


//        try {

//            HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
//            HttpClient client = new DefaultHttpClient();
//            HttpResponse response;
//            stringBuilder = new StringBuilder();
//
//            Log.v("aaa", "1a2");
//            response = client.execute(httppost);
//            Log.v("aaa", "1a3");
//            HttpEntity entity = response.getEntity();
//            Log.v("aaa", "1a4");
//            InputStream stream = entity.getContent();
//            int b;
//            while ((b = stream.read()) != -1) {
//                stringBuilder.append((char) b);
//            }
//            Log.v("aaa", "1a5");
//        } catch (ClientProtocolException e) {
//            Log.v("aaa", "1a+error:" + e.toString());
//        } catch (IOException e) {
//            Log.v("aaa", "1a+error:" + e.toString());
//        }
//        Log.v("aaa", "1a5");
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject = new JSONObject(stringBuilder.toString());
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        Log.v("aaa", "1a6");
//        return jsonObject;
        return null;
    }


    public static dataPosition getLatLong(JSONObject jsonObject) {

        Double lon = new Double(0);
        Double lat = new Double(0);


        try {
            Log.v("aaa", "2a1");
            lon = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");
            Log.v("aaa", "2a2");
            lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");
            Log.v("aaa", "2a3");

        } catch (Exception e) {
            e.printStackTrace();

        }

        Log.v("aaa", "2a4");
        return new dataPosition(lon, lat);
    }

    public static class dataPosition {
        public Double lon;
        public Double lat;

        public dataPosition(double lon, double lat) {
            this.lon = lon;
            this.lat = lat;

        }


    }


    public class MyAsyncTask
            extends AsyncTask<String, Void, dataPosition> {

        final String TAG = "MyAsyncTask";
        ProgressDialog dialog;
        Context context;

        public MyAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute");
            dialog = new ProgressDialog(context);
            dialog.setTitle("Please wait");
            dialog.setMessage("Loading data...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected dataPosition doInBackground(String... params) {
            Log.v("aaa", 0000000 + "");
            Log.v("aaa", 010101010 + "");
            Log.v("aaa", 111122344 + "");
            StringBuilder stringBuilder = null;
            Log.v("aaa", 2222220 + "");
            try {
                Log.v("aaa", "1");
                HttpPost httppost = new HttpPost(params[0]);
                Log.v("aaa", "2");
                HttpClient client = new DefaultHttpClient();
                Log.v("aaa", "3");
                HttpResponse response;
                stringBuilder = new StringBuilder();
                Log.v("aaa", "4");
                Log.v("aaa", "1a2");
                response = client.execute(httppost);
                Log.v("aaa", "1a3");
                HttpEntity entity = response.getEntity();
                Log.v("aaa", "1a4");
                InputStream stream = entity.getContent();
                int b;
                while ((b = stream.read()) != -1) {
                    stringBuilder.append((char) b);
                }
                Log.v("aaa", "1a5");
            } catch (ClientProtocolException e) {
                Log.v("aaa", "1a+error:" + e.toString());
            } catch (IOException e) {
                Log.v("aaa", "1a+error:" + e.toString());
            }
            Log.v("aaa", "1a5");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(stringBuilder.toString());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Double lon = new Double(0);
            Double lat = new Double(0);

            try {
                Log.v("aaa", "2a1");
                lon = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lng");
                Log.v("aaa", "2a2");
                lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lat");
                Log.v("aaa", "2a3");

            } catch (Exception e) {
                e.printStackTrace();

            }

            return new dataPosition(lon, lat);
        }


        @Override
        protected void onPostExecute(dataPosition positon) {


            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(positon.lat, positon.lon), 15));
//            Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
//                    .title("Hamburg"));
            Marker kiel = map.addMarker(new MarkerOptions()
                    .position(new LatLng(positon.lat, positon.lon))
                    .title("Kiel")
                    .snippet("Kiel is cool")
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_launcher)));
            dialog.dismiss();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

//        nowLatlng =new LatLng(35.6553288,139.6953254);
//
//        Marker hamburg = map.addMarker(new MarkerOptions().position(nowLatlng)
//                .title("Hamburg"));
////
//
//        // Move the camera instantly to hamburg with a zoom of 15.
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(nowLatlng, 17));
//
//        // Zoom in, animating the camera.
//        map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);


    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
}
