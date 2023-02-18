package com.ashish.contractedfarming.Admin.FarmerControl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.contractedfarming.R;

import androidx.fragment.app.Fragment;

public class AdminConnectionsFarmerFragment extends Fragment {


    public AdminConnectionsFarmerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view  = inflater.inflate(R.layout.fragment_admin_connections, container, false);
        return view;
    }
}