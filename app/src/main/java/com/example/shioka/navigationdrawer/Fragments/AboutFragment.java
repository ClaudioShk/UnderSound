package com.example.shioka.navigationdrawer.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shioka.navigationdrawer.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class AboutFragment extends android.support.v4.app.Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

}
