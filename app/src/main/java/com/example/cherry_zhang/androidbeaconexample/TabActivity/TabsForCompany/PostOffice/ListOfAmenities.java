package com.example.cherry_zhang.androidbeaconexample.TabActivity.TabsForCompany.PostOffice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cherry_zhang.androidbeaconexample.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ListOfAmenities extends Activity {

    ProgressDialog dialog;
    ListView lv_amenities;
    ArrayAdapter<String> adapter;
    ArrayList<String> items;
    ArrayList<ParseObject> itemsObjectID;
    public static ArrayList<ParseObject> selectedItemsObjectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_amenities);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        itemsObjectID = new ArrayList<ParseObject>();
        selectedItemsObjectID = new ArrayList<ParseObject>();
        items = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, items);
        lv_amenities = (ListView) findViewById(R.id.lv_amenities);
        lv_amenities.setAdapter(adapter);
        lv_amenities.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        new ListOfAmenities_Async().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_of_amenities, menu);
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
        if (id == android.R.id.home)
        {
            this.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        Intent data = new Intent();

        ArrayList<String> selectedTags = new ArrayList<String>();
        SparseBooleanArray checked = lv_amenities.getCheckedItemPositions();

        if (checked != null)
        {
            int n = 0;
            for (int i = 0; i < checked.size(); i++) {
                // Item position in adapter
                int position = checked.keyAt(i);
                // Add tag if it is checked i.e.) == TRUE!
                if (checked.valueAt(i))
                {
                    selectedTags.add(adapter.getItem(position));
                    //selectedItemsObjectID.add(itemsObjectID.get(i));
                    selectedItemsObjectID.add(itemsObjectID.get(i));
                    n++;
                }

            }

            data.putStringArrayListExtra("tags", selectedTags);
//            Bundle bundle = new Bundle();
//            bundle.(selectedItemsObjectID);
//            data.putStringArra("tagsObjectID", );
            setResult(RESULT_OK, data);
        }

        super.finish();
    }

    private class ListOfAmenities_Async extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog = new ProgressDialog(ListOfAmenities.this);
            dialog.setTitle("Please wait");
            dialog.setMessage("Loading data...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Amenity");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> amenityList, ParseException e) {
                    if (e == null) {
                        for (ParseObject amenity : amenityList)
                        {
                            items.add(amenity.getString("name"));
                            adapter.notifyDataSetChanged();
                            itemsObjectID.add(amenity);
                        }
                        dialog.dismiss();
                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
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
