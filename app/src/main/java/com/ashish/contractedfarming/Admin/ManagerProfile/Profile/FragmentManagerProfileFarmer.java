package com.ashish.contractedfarming.Admin.ManagerProfile.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;

public class FragmentManagerProfileFarmer extends Fragment {


    private final DataSnapshot snapshot;

    public FragmentManagerProfileFarmer(DataSnapshot snapshot) {
        // Required empty public constructorthi
        this.snapshot = snapshot;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manager_profile_farmer, container, false);
    }
}