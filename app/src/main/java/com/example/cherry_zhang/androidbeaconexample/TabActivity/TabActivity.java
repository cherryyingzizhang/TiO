package com.example.cherry_zhang.androidbeaconexample.TabActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cherry_zhang.androidbeaconexample.R;
import com.parse.Parse;


public class TabActivity extends FragmentActivity {

    private String[] drawerTitles = {"Freelance", "Company"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    ViewPager viewPager;
    TabPagerAdapter tabPagerAdapter;

    private String freelanceOrCompany;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Intent intent = getIntent();
        this.freelanceOrCompany = intent.getStringExtra(TabConstants.FREELANCE_OR_COMPANY_KEY);

        //initialize PARSE
        Parse.initialize(this, "TsVbzF7jXzY1C0o86V2xxAxgSxvy4jmbyykOabPl",
                "VzamwWm4WswbDFxrxos2oSerQ2Av4RM6J5mNnNgr");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, drawerTitles) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };

        mDrawerList.setAdapter(adapter);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(TabActivity.this,TabActivity.class);

                if (drawerTitles[position].contentEquals("Freelance"))
                {
                    intent.putExtra(TabConstants.FREELANCE_OR_COMPANY_KEY,TabConstants.FOR_FREELANCE);
                    startActivity(intent);
                }
                else if (drawerTitles[position].contentEquals("Company"))
                {
                    intent.putExtra(TabConstants.FREELANCE_OR_COMPANY_KEY,TabConstants.FOR_COMPANY);
                    startActivity(intent);
                }
                else
                {
                    Log.e("drawer error", "drawer error");
                }
            }
        });

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), freelanceOrCompany);
        viewPager.setAdapter(tabPagerAdapter);
        PagerTitleStrip pagerTitleStrip = (PagerTitleStrip)findViewById(R.id.pager_title_strip);

        //TODO: customize pagerTitleStrip
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Toast.makeText(this, "search start!", Toast.LENGTH_SHORT).show();
            onSearchRequested();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
