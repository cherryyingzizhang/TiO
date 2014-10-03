package com.example.cherry_zhang.androidbeaconexample.LoginAndRegistration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.cherry_zhang.androidbeaconexample.R;


/**
 * Created by geoffreyheath on 2014-09-08.
 */
public class SummaryOfAppFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_summary, container, false);
        Bundle args = getArguments();

        //the page number
        int tabNum = args.getInt(ARG_SECTION_NUMBER);

        if(tabNum==2) {
            ((LinearLayout) rootView.findViewById(R.id.loginBackground)).setBackgroundResource(R.drawable.top_image2);

        } else if(tabNum==1) {
            ((LinearLayout) rootView.findViewById(R.id.loginBackground)).setBackgroundResource(R.drawable.top_image1);
        }


        return rootView;
    }
}
