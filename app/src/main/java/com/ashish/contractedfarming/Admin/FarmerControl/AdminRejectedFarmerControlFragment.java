package com.ashish.contractedfarming.Admin.FarmerControl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.contractedfarming.R;

import androidx.fragment.app.Fragment;

/**
 */
public class AdminRejectedFarmerControlFragment extends Fragment {



    public AdminRejectedFarmerControlFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=null;
        view = inflater.inflate(R.layout.fragment_admin_rejected_farmer_control, container, false);

        return view;

    }
}