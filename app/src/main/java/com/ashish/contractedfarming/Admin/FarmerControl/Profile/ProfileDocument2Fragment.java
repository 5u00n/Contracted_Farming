package com.ashish.contractedfarming.Admin.FarmerControl.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;

import androidx.fragment.app.Fragment;

public class ProfileDocument2Fragment extends Fragment {

    DataSnapshot ds;

    public ProfileDocument2Fragment(DataSnapshot ds) {
        // Required empty public constructor
        this.ds=ds;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;

        v = inflater.inflate(R.layout.fragment_farmer_profile_plot_document, container, false);
        return v;

    }
}