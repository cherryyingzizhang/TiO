package com.example.cherry_zhang.androidbeaconexample.TabActivity.TabsForCompany.PostOffice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cherry_zhang.androidbeaconexample.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class PostOffice2 extends Activity implements View.OnClickListener {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_GALLERY = 2;
    private static final int REQUEST_AMENITIES = 3;

    ProgressDialog dialog;

    public static Uri fileUri;
    ParseObject image;
    ParseFile file;

    //TODO: change this
    private String address = "Midtown Roppongi";

    ImageView camera, gallery, iv_picture_1, iv_picture_2, iv_picture_3;
    EditText companyName, capacity, officeHour, price, description;
    TextView amenities;
    Button post;
    LinearLayout ll_amenities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_office2);


        Parse.initialize(this, "TsVbzF7jXzY1C0o86V2xxAxgSxvy4jmbyykOabPl",
                "VzamwWm4WswbDFxrxos2oSerQ2Av4RM6J5mNnNgr");

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        address = intent.getStringExtra("address");

        iv_picture_1 = (ImageView) findViewById(R.id.iv_picture_1);
        iv_picture_2 = (ImageView) findViewById(R.id.iv_picture_2);
        iv_picture_3 = (ImageView) findViewById(R.id.iv_picture_3);

        camera = (ImageView) findViewById(R.id.iv_camera);
        gallery = (ImageView) findViewById(R.id.iv_gallery);
        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);

        companyName = (EditText) findViewById(R.id.et_companyName);
        capacity = (EditText) findViewById(R.id.et_capacity);
        officeHour = (EditText) findViewById(R.id.et_officeHours);
        price = (EditText) findViewById(R.id.et_price);
        description = (EditText) findViewById(R.id.et_description);
        amenities = (TextView) findViewById(R.id.tv_amenities);

        ll_amenities = (LinearLayout) findViewById(R.id.ll_amenities);
        ll_amenities.setOnClickListener(this);

        post = (Button) findViewById(R.id.b_post);
        post.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_camera:
                PostOffice2.fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, PostOffice2.fileUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                break;
            case R.id.iv_gallery:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_GALLERY);
                break;
            case R.id.ll_amenities:
                Intent amenitiesActivity = new Intent(PostOffice2.this,ListOfAmenities.class);
                startActivityForResult(amenitiesActivity, REQUEST_AMENITIES);
                break;
            case R.id.b_post:
                checkIfItemParametersAreValidBeforePosting();
                break;
        }
    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    public static final int MEDIA_TYPE_IMAGE = 1;

    private static File getOutputMediaFile(int type){

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ "Givery_" + timeStamp + ".jpg");
        }
        else
        {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case REQUEST_IMAGE_CAPTURE:
                    Toast.makeText(this, "Image saved to:\n" +
                            fileUri, Toast.LENGTH_LONG).show();
                    iv_picture_1.setImageURI(fileUri);
                    break;
                case REQUEST_GALLERY:
                    iv_picture_1.setImageURI(data.getData());
                    break;
                case REQUEST_AMENITIES:
                    String amenitiesText = "";
                    ArrayList<String> selectedTags;
                    selectedTags = data.getStringArrayListExtra("tags");
//                    amenitiesObjectID = data.getStringArrayListExtra("tagsObjectID").toArray
//                            (new String[data.getStringArrayListExtra("tagsObjectID").size()]);
//                    amenitiesObjectID = data.getParcelableExtra("tagsObjectID");
                    for (int i = 0; i < selectedTags.size(); i++)
                    {
                        if (i == selectedTags.size()-1)
                        {
                            amenitiesText += selectedTags.get(i);
                        }
                        else
                        {
                            amenitiesText += selectedTags.get(i) + ", ";
                        }

                    }
                    amenities.setText(amenitiesText);
                    break;
                default:
                    Log.e("PostOffice2 onActivityResult","bug");
                    break;
            }
        }
    }

    public void checkIfItemParametersAreValidBeforePosting()
    {
        if (iv_picture_1.getDrawable() == null)
        {
            Toast.makeText(this,"Need Picture",Toast.LENGTH_SHORT).show();
        }
        else if (companyName.getText().toString().contentEquals("") ||
                companyName.getText() == null)
        {
            Toast.makeText(this,"Need Company Name",Toast.LENGTH_SHORT).show();
        }
        else if (capacity.getText().toString().contentEquals("") ||
                capacity.getText() == null)
        {
            Toast.makeText(this,"Need Capacity",Toast.LENGTH_SHORT).show();
        }
        else if (officeHour.getText().toString().contentEquals("") ||
                officeHour.getText() == null)
        {
            Toast.makeText(this,"Need Office Hour",Toast.LENGTH_SHORT).show();
        }
        else if (price.getText().toString().contentEquals("") ||
                price.getText() == null)
        {
            Toast.makeText(this,"Need Price",Toast.LENGTH_SHORT).show();
        }
        else if (description.getText().toString().contentEquals("") ||
                description.getText() == null)
        {
            Toast.makeText(this,"Need Description",Toast.LENGTH_SHORT).show();
        }
        else if (amenities.getText() == null || amenities.getText().toString().contentEquals(""))
        {
            Toast.makeText(this,"Need at least 1 amenity",Toast.LENGTH_SHORT).show();
        }
        else
        {
            postItem();
        }
    }

    public void postItem()
    {
        //TODO
        new PostOffice2_Async().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_office2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class PostOffice2_Async extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog = new ProgressDialog(PostOffice2.this);
            dialog.setTitle("Please wait");
            dialog.setMessage("Loading data...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            ParseObject company = new ParseObject("Company");

            company.put("name",companyName.getText().toString());
            company.saveInBackground();

            for (ParseObject amenity : ListOfAmenities.selectedItemsObjectID)
            {
                Log.e("amenities:",amenity.getObjectId());
            }

//            hashMap.put("location",new ParseGeoPoint(35.691588,139.701143));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bm = ((BitmapDrawable)iv_picture_1.getDrawable()).getBitmap();
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] data = stream.toByteArray();
            file = new ParseFile("imageName.PNG",data);
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    Log.e("file",""+(file.isDirty()));
                    Log.e("file",file.getName());

                    image = new ParseObject("Image");
                    image.put("image",file);

                    image.saveInBackground(new SaveCallback() {

                        @Override
                        public void done(ParseException e) {

                            Log.e("image objectid",image.getObjectId());

                            //String[] imageObjectIDs = {image.getObjectId()};

                            //hashMap.put("images",imageObjectIDs);

                            final ParseObject company = new ParseObject("Company");
                            company.put("name",companyName.getText().toString());
                            company.saveInBackground();

                            ParseObject office = new ParseObject("Office");
                            office.put("name",companyName.getText().toString());
                            office.put("company",company);
                            office.put("catchCopy","Awesome place!");
                            office.put("description", description.getText().toString());
                            office.put("hours",officeHour.getText().toString());
                            office.put("price",Integer.parseInt(price.getText().toString()));
                            office.put("capacity",Integer.parseInt(capacity.getText().toString()));
                            office.put("address","Midtown Roppongi");
//                            office.put("amenities",ListOfAmenities.selectedItemsObjectID.toArray
//                                       (new ParseObject[ListOfAmenities.selectedItemsObjectID.size()]));
                            office.put("images", Arrays.asList(new ParseObject[]{image}));
                            office.put("location",new ParseGeoPoint(35.691588,139.701143));

                            office.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    ParseUser user = ParseUser.getCurrentUser();
                                    user.put("company",company);
                                    user.saveInBackground();
                                    dialog.dismiss();
                                    Toast.makeText(PostOffice2.this,"Posted!",Toast.LENGTH_SHORT)
                                            .show();
                                    PostOffice2.this.finish();
                                }
                            });

                        }
                    });

                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
        }
    }
}