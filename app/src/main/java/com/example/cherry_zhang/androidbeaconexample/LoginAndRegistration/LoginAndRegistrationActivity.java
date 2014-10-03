/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * ATTENTION !!!
 * This class also has information/swipe views
 */

package com.example.cherry_zhang.androidbeaconexample.LoginAndRegistration;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cherry_zhang.androidbeaconexample.R;
import com.parse.Parse;
import com.viewpagerindicator.CirclePageIndicator;

public class LoginAndRegistrationActivity extends FragmentActivity implements ActionBar.TabListener {

    public static View profile;
    public static View login;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;

    //the circle page indicator swipe thing
    public static CirclePageIndicator pageIndicator;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profile = LayoutInflater.from(this).inflate(R.layout.fragment_profile, null);
        login = LayoutInflater.from(this).inflate(R.layout.fragment_section_login_and_registration, null);

        setContentView(R.layout.activity_swipe_view_login_and_registration);

        //parse initialization
        Parse.initialize(this, "TsVbzF7jXzY1C0o86V2xxAxgSxvy4jmbyykOabPl",
                "VzamwWm4WswbDFxrxos2oSerQ2Av4RM6J5mNnNgr");

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        actionBar.hide();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

        ViewPager.SimpleOnPageChangeListener mPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        };

        //mViewPager.setOnPageChangeListener(

        pageIndicator = (CirclePageIndicator) findViewById(R.id.CPI_pageIndicator);
        pageIndicator.setViewPager(mViewPager);
        pageIndicator.setOnPageChangeListener(mPageChangeListener);
        pageIndicator.setCurrentItem(0);

        //TODO: make circle page indicator look better
        final float density = getResources().getDisplayMetrics().density;
        pageIndicator.setRadius(5 * density);
        pageIndicator.setPageColor(0xFF686868);
        pageIndicator.setFillColor(0xFFFFFFFF);
        pageIndicator.setStrokeColor(0xFF000000);
        pageIndicator.setStrokeWidth(1);


        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));

        }
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

}
