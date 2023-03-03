package com.ashish.contractedfarming.Admin.ManagerProfile.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ashish.contractedfarming.Admin.Dashboard.Farmer.AdminFarmerAdapter;
import com.ashish.contractedfarming.Admin.Dashboard.Farmer.AdminFarmerModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
        View v = inflater.inflate(R.layout.fragment_manager_profile_farmer, container, false);


        ListView listView = v.findViewById(R.id.manager_profile_farmer_list_view);


        List<AdminFarmerModel> arrayList = new ArrayList<>();
        DataSnapshot childFarmerSnapshot=snapshot.child("farmers");
        if(childFarmerSnapshot.exists()) {
            for (DataSnapshot ds : childFarmerSnapshot.getChildren()) {
                arrayList.add(new AdminFarmerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("village").getValue().toString(), ds.child("img_url").getValue().toString()));
            }
            AdminFarmerAdapter adapter = new AdminFarmerAdapter(getContext(), arrayList);
            if (adapter != null) {
                listView.setAdapter(adapter);
            }
        }


        return v;
    }
}