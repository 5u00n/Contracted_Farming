package com.ashish.contractedfarming.Admin.ManagerProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.contractedfarming.R;

import androidx.fragment.app.Fragment;

public class FragmentAdminManagerConnect extends Fragment {


    public FragmentAdminManagerConnect() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v;
       v = inflater.inflate(R.layout.fragment_admin_agent_connect, container, false);
        return v;
    }
}