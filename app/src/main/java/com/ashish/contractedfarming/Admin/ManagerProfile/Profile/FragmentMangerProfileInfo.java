package com.ashish.contractedfarming.Admin.ManagerProfile.Profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ashish.contractedfarming.Admin.Dashboard.Manager.AdminManagerModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class FragmentMangerProfileInfo extends Fragment {

    DataSnapshot snapshot;

    public FragmentMangerProfileInfo(DataSnapshot snapshot) {
        // Required empty public constructorthi
        this.snapshot = snapshot;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_agent_verification_one, container, false);

        Context context = getContext();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users").child("new-agent");

        List<AdminManagerModel> arrayList = new ArrayList<>();


        return v;
    }
}