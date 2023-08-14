package com.ashish.contractedfarming.Farmer.Dashboard.MyFarm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.contractedfarming.Farmer.Dashboard.MyFarm.FragmentHelper.FarmerFragmentMyFarmAdapter;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyFarmFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;


    ArrayList<FarmerMyfarmModel> farmerFarm_List;

    RecyclerView recyclerView;

    public MyFarmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_farm, container, false);

        auth= FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

        recyclerView= v.findViewById(R.id.farmer_my_farm_fragment_recycle_view);

        reference.child("users").child("farmer").child(auth.getUid()).child("plot").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                farmerFarm_List = new ArrayList<>();
                if (snapshot.hasChildren())
                    for (DataSnapshot fds : snapshot.getChildren()) {
                        FarmerMyfarmModel fm = new FarmerMyfarmModel(fds.child("plotID").getValue().toString(), fds.child("userUID").getValue().toString(), fds.child("area").getValue().toString(), fds.child("village").getValue().toString(), fds.child("taluka").getValue().toString(), fds.child("dist").getValue().toString(), fds.child("state").getValue().toString(), fds.child("gat_no").getValue().toString(), fds.child("sarvay_no").getValue().toString(), "Nil");
                        if (fds.child("plant_name").exists()) {
                            fm.setPlant(fds.child("plant_name").getValue().toString());
                        }
                        if (fds.child("plotName").exists()) {
                            fm.setPlotName(fds.child("plotName").getValue().toString());
                        }
                        farmerFarm_List.add(fm); }

                FarmerFragmentMyFarmAdapter adapter1 = new FarmerFragmentMyFarmAdapter(farmerFarm_List, getContext());
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager1);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(adapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;
    }
}