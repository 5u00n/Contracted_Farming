package com.ashish.contractedfarming.Admin.ManagerProfile.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;

import androidx.fragment.app.Fragment;

public class FragmentManagerProfileIdentity extends Fragment {
    DataSnapshot snapshot;
    public FragmentManagerProfileIdentity(DataSnapshot snapshot) {
        // Required empty public constructorthi
        this.snapshot=snapshot;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_agent_verification_two, container, false);
        return v;

    }
}