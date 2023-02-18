package com.ashish.contractedfarming.Admin.ManagerProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ashish.contractedfarming.Admin.Dashboard.Manager.AdminManagerAdapter;
import com.ashish.contractedfarming.Admin.Dashboard.Manager.AdminManagerModel;
import com.ashish.contractedfarming.Admin.ManagerProfile.Profile.ManagerProfileActivity;
import com.ashish.contractedfarming.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentAdminNewManager extends Fragment {


    public FragmentAdminNewManager() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.fragment_admin_new_agents, container, false);


        ListView listView= v.findViewById(R.id.admin_new_agent_control_list);


        FirebaseDatabase database=  FirebaseDatabase.getInstance();
        DatabaseReference reference= database.getReference("users").child("new-agent");


        ArrayList<AdminManagerModel> arrayList= new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                for(DataSnapshot snapshot:snapshot1.getChildren()) {
                    arrayList.add(new AdminManagerModel(snapshot.child("userUID").getValue().toString(), snapshot.child("username").getValue().toString(), snapshot.child("address").child("village").getValue().toString(), snapshot.child("img_url").getValue().toString()));
                }
                AdminManagerAdapter adapter= new AdminManagerAdapter(getContext(),arrayList);

                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AdminManagerAdapter ob = (AdminManagerAdapter)adapterView.getAdapter();

                Intent intent = new Intent(getContext(), ManagerProfileActivity.class);
                intent.putExtra("userUID",ob.getItem(i).getId());
                intent.putExtra("usertype","new-agent");
                startActivity(intent);
            }
        });

        return v;
    }
}