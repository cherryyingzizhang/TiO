package com.example.cherry_zhang.androidbeaconexample.LoginAndRegistration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

public class AppSectionsPagerAdapter extends FragmentPagerAdapter
{

    public AppSectionsPagerAdapter(android.support.v4.app.FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 2:
                //the third screen (index 2) will be the login and registration screen
                return new LoginAndRegistrationFragment();

            default:
                // The other sections of the app are dummy placeholders.
                Fragment fragment = new SummaryOfAppFragment();
                Bundle args = new Bundle();
                args.putInt(SummaryOfAppFragment.ARG_SECTION_NUMBER, i + 1);
                fragment.setArguments(args);
                return fragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Section " + (position + 1);
    }
}
