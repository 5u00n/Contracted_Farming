package com.ashish.contractedfarming.Farmer.Dashboard.MyRequest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.contractedfarming.Farmer.Dashboard.MyFarm.FarmerMyfarmModel;
import com.ashish.contractedfarming.Farmer.Dashboard.MyFarm.FragmentHelper.FarmerFragmentMyFarmAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.MyRequest.FragmentHelper.FarmerMyRequestRecycleAdapter;
import com.ashish.contractedfarming.Models.RequestModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyRequestFragment extends Fragment {


    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    ArrayList<RequestModel> requestModelArrayList;

    RecyclerView recyclerView;

    public MyRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_request, container, false);


        auth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        reference=database.getReference();

        recyclerView= view.findViewById(R.id.farmer_my_farm_fragment_recycle_view);

        reference.child("users").child("farmer").child(auth.getUid()).child("requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestModelArrayList = new ArrayList<>();
                if (snapshot.hasChildren())
                    for (DataSnapshot fds : snapshot.getChildren()) {
                        RequestModel fm = new RequestModel(fds.child("id").getValue().toString(), fds.child("sender_id").getValue().toString(),fds.child("send_to").getValue().toString(), fds.child("type").getValue().toString(), fds.child("type_id").getValue().toString(), fds.child("date_of_creation").getValue().toString(),fds.child("checked").getValue().toString(),fds.child("stared").getValue().toString(),fds.child("message").getValue().toString());
                        requestModelArrayList.add(fm);
                    }

                FarmerMyRequestRecycleAdapter adapter1 = new FarmerMyRequestRecycleAdapter(requestModelArrayList, getContext());
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager1);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(adapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;

    }
}