package com.example.cherry_zhang.androidbeaconexample.SearchFeature;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.cherry_zhang.androidbeaconexample.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cherry_Zhang on 2014-09-20.
 */
public class SearchableActivity extends ListActivity {

    ArrayAdapter<String> adapter;
    ArrayList<String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable_activity);
        // Get the intent, verify the action and get the query

        getActionBar().setDisplayHomeAsUpEnabled(true);

        items = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        setListAdapter(adapter);

        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //TODO
            }
        });

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
//        else if(intent.getAction().equals(Intent.ACTION_VIEW)) {
//            Toast.makeText(getApplicationContext(),"Open Office detail",Toast.LENGTH_SHORT)
//                    .show();
//            //Intent countryIntent = new Intent(this, CountryActivity.class);
//            //countryIntent.setData(intent.getData());
//            //startActivity(countryIntent);
//            finish();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void doMySearch(String query)
    {
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Office");
        parseQuery.whereContains("name", query);
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject>offices, ParseException e) {
                if (e == null) {

                    for (ParseObject office : offices)
                    {
                        items.add(office.getString("name"));
                        adapter.notifyDataSetChanged();
                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }


}
