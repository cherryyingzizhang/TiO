package com.example.cherry_zhang.androidbeaconexample.TabActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.cherry_zhang.androidbeaconexample.TabActivity.TabsForCompany.MyBookings.MyBookings;
import com.example.cherry_zhang.androidbeaconexample.TabActivity.TabsForCompany.PostOffice.PostOffice1;
import com.example.cherry_zhang.androidbeaconexample.TabActivity.TabsForFreelance.ListOfNearbyOffice.ListOfNearbyOffices;
import com.example.cherry_zhang.androidbeaconexample.TabActivity.TabsForFreelance.MyListings.MyListings;
import com.example.cherry_zhang.androidbeaconexample.TabActivity.TabsForFreelance.Profile.Profile;

/**
 * Created by Cherry_Zhang on 2014-09-21.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {

    //class variable
    private static final String[] titlesForFreelance = {"Profile", "Offices" ,"Bookings"};
    private static final String[] titlesForCompany = {"Post", "Listings"};

    //instance variables
    private String freelanceOrCompany;

    public TabPagerAdapter(FragmentManager fm, String freelanceOrCompany)
    {
        super(fm);
        this.freelanceOrCompany = freelanceOrCompany;
    }

    @Override
    public Fragment getItem(int index)
    {
        if (freelanceOrCompany.contentEquals(TabConstants.FOR_FREELANCE))
        {
            switch (index)
            {
                case 0:
                    return new Profile();
                case 1:
                    return new ListOfNearbyOffices();
                case 2:
                    return new MyBookings();
                default:
                    return null;
            }
        }
        else if (freelanceOrCompany.contentEquals(TabConstants.FOR_COMPANY))
        {
            switch (index)
            {
                case 0:
                    return new PostOffice1();
                case 1:
                    return new MyListings();
                default:
                    return null;
            }
        }
        else
        {
            Log.e("Actual Error", "tabPagerAdapter1");
            return null;
        }

    }

    @Override
    public int getCount()
    {
        if (freelanceOrCompany.contentEquals(TabConstants.FOR_FREELANCE))
        {
            return 3;
        }
        else if (freelanceOrCompany.contentEquals(TabConstants.FOR_COMPANY))
        {
            return 2;
        }
        else
        {
            Log.e("Actual Error", "tabPagerAdapter2");
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        if (freelanceOrCompany.contentEquals(TabConstants.FOR_FREELANCE))
        {
            return titlesForFreelance[position];
        }
        else if (freelanceOrCompany.contentEquals(TabConstants.FOR_COMPANY))
        {
            return titlesForCompany[position];
        }
        else
        {
            Log.e("Actual Error", "tabPagerAdapter3");
            return "Error";
        }

    }
}
