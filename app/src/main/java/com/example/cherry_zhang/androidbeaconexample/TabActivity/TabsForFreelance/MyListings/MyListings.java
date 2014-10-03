package com.example.cherry_zhang.androidbeaconexample.TabActivity.TabsForFreelance.MyListings;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.cherry_zhang.androidbeaconexample.DetailOfOffice.DetailOfOffice;
import com.example.cherry_zhang.androidbeaconexample.ListAdapter.ListOfOfficeAdapter;
import com.example.cherry_zhang.androidbeaconexample.Models.OfficeItem;
import com.example.cherry_zhang.androidbeaconexample.R;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MyListings extends android.support.v4.app.Fragment {

    ProgressBar pv_progressBar;

    ListView lv_listOfNearbyOffices;
    ListOfOfficeAdapter adapter;
    ArrayList<OfficeItem> items;
    ArrayList<String> itemOfficeIds;

    MyListings_Async load;

    public MyListings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = new ArrayList<OfficeItem>();
        itemOfficeIds = new ArrayList<String>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_office_list,container,false);

        lv_listOfNearbyOffices = (ListView) rootView.findViewById(R.id.lv_listOfNearbyOffices);

        pv_progressBar = (ProgressBar) rootView.findViewById(R.id.pv_progressBar);

        adapter = new ListOfOfficeAdapter(getActivity(), items,R.layout.list_item);
        lv_listOfNearbyOffices.setAdapter(adapter);
        lv_listOfNearbyOffices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //open item detail page
                Intent intent = new Intent(getActivity(), DetailOfOffice.class);
                intent.putExtra("officeId", itemOfficeIds.get(i));
                startActivity(intent);
            }
        });

        load = new MyListings_Async();
        load.execute();

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

    private class MyListings_Async extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {

            Log.e("MyListings", "doing stuff in background");

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Office");
            query.whereEqualTo("company", ParseUser.getCurrentUser().getParseObject("company"));
            query.include("images");
            query.include("image");
            query.include("company");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    if (e == null && !parseObjects.isEmpty())
                    {
                        for (final ParseObject office : parseObjects)
                        {
                            itemOfficeIds.add(office.getObjectId());
                            ArrayList<ParseObject> images = (ArrayList<ParseObject>) office.get("images");
                            ParseFile imageFile = images.get(0).getParseFile("image");
                            imageFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] imageBytes, ParseException e) {
                                    if (e == null)
                                    {
                                        Bitmap officeImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                        Log.e("is bitmap null","" + (officeImage == null));
                                        final String title = office.getString("name");
                                        final String address = office.getString("address");
                                        final String company = office.getParseObject("company").getString("name");
                                        final String price = office.getString("price");

                                        Log.e("MyListings", title + address + company + price);

                                        items.add(new OfficeItem(officeImage, title,
                                                address,
                                                company,
                                                "¥"+price));
                                        adapter.notifyDataSetChanged();
                                        Log.e("MyListings","done stuffs");
                                    }
                                    else
                                    {
                                        Log.e("MyListings blah",e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                    else if (e != null)
                    {
                        Log.e("MyListings blah2",e.getMessage());
                    }
                    else if (parseObjects.isEmpty())
                    {
                        Log.e("MyListings blah3","query empty");
                    }
                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
            Log.e("number of children in listview: ", "" + lv_listOfNearbyOffices.getChildCount());
            pv_progressBar.setVisibility(View.GONE);
        }
    }
}