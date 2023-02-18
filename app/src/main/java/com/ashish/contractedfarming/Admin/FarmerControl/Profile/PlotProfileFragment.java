package com.ashish.contractedfarming.Admin.FarmerControl.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;

import androidx.fragment.app.Fragment;

public class PlotProfileFragment extends Fragment {


    DataSnapshot snapshot;
    public PlotProfileFragment(DataSnapshot snapshot) {
        // Required empty public constructor
        this.snapshot= snapshot;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plot_profile, container, false);
    }
}