package com.ashish.contractedfarming.Admin.Dashboard.Farmer.FarmerProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.ashish.contractedfarming.Admin.Dashboard.Farmer.AdminFarmerAdapter;
import com.ashish.contractedfarming.Admin.Dashboard.Farmer.AdminFarmerModel;
import com.ashish.contractedfarming.Admin.Dashboard.Farmer.FarmerProfile.Profile.FarmerProfileActivity;
import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminConnectionsFarmerFragment extends Fragment {


    public AdminConnectionsFarmerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_connections, container, false);

        SearchView searchView = v.findViewById(R.id.admin_farmercontrol_con_farmer_search);
        ListView listView = v.findViewById(R.id.fragment_admin_con_farmer_rv);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("users").child("new-farmer");


        List<AdminFarmerModel> arrayList = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    if (!searchView.hasFocus()) {
                        arrayList.removeAll(arrayList);
                        for (DataSnapshot ds : snapshot.getChildren()) {


                                if (Integer.parseInt(ds.child("approved_num").getValue().toString()) < 3) {
                                    if (ds.child("approved_num").exists() && ds.child("address").exists()) {
                                    arrayList.add(new AdminFarmerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("address").child("village").getValue().toString(), ds.child("img_url").getValue().toString()));
                                }else if(Integer.parseInt(ds.child("approved_num").getValue().toString()) ==0){
                                        arrayList.add(new AdminFarmerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), "", ds.child("img_url").getValue().toString()));
                                    }
                            }
                        }
                        if (getContext() != null) {
                            AdminFarmerAdapter adapter = new AdminFarmerAdapter(getContext(), arrayList);
                            if (adapter != null) {
                                listView.setAdapter(adapter);
                            }
                        }
                    }
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {

                            arrayList.removeAll(arrayList);
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                if (ds.child("approved_num").exists()) {
                                    if (Integer.parseInt(ds.child("approved_num").getValue().toString()) < 3) {
                                        if (newText.length() <= ds.child("username").toString().length()) {
                                            if (ds.child("username").toString().toLowerCase().contains(newText.toString().toLowerCase())) {
                                                arrayList.add(new AdminFarmerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("address").child("village").getValue().toString(), ds.child("img_url").getValue().toString()));
                                            }
                                        }
                                    }
                                }
                            }
                            AdminFarmerAdapter adapter = new AdminFarmerAdapter(getContext(), arrayList);
                            if (adapter != null) {
                                listView.setAdapter(adapter);
                            }

                            return false;
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                AdminFarmerAdapter ob = (AdminFarmerAdapter) adapterView.getAdapter();
                //Log.d("Agent Clicks ", "ID : " + ob.getItem(i).getId()+ "    long: " + l);
                startActivity(new Intent(getContext(), FarmerProfileActivity.class).putExtra("userUID", ob.getItem(i).getId()).putExtra("usertype", "new-farmer").putExtra("not_completed", true));

            }
        });

        return v;

    }
}