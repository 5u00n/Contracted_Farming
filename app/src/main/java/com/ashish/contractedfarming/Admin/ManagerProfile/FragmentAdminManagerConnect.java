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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

public class FragmentAdminManagerConnect extends Fragment {


    public FragmentAdminManagerConnect() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.fragment_admin_new_agents, container, false);


        ListView listView= v.findViewById(R.id.admin_new_agent_control_list);
        SearchView searchView = v.findViewById(R.id.admin_agent_control_agentlist_search);


        FirebaseDatabase database=  FirebaseDatabase.getInstance();
        DatabaseReference reference= database.getReference("users").child("new-manager");


        ArrayList<AdminManagerModel> arrayList= new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (!searchView.hasFocus()) {
                        arrayList.removeAll(arrayList);
                        for (DataSnapshot ds : snapshot.getChildren()) {

                            if (ds.child("approved_num").exists()) {
                                if (Integer.parseInt(ds.child("approved_num").getValue().toString()) < 2) {
                                    arrayList.add(new AdminManagerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("address").child("village").getValue().toString(), ds.child("img_url").getValue().toString()));
                                }
                            }
                        }
                        AdminManagerAdapter adapter = new AdminManagerAdapter(getContext(), arrayList);
                        if (adapter != null) {
                            listView.setAdapter(adapter);
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
                                    if (Integer.parseInt(ds.child("approved_num").getValue().toString()) < 2) {
                                        if (newText.length() <= ds.child("username").toString().length()) {
                                            if (ds.child("username").toString().toLowerCase().contains(newText.toString().toLowerCase())) {
                                                arrayList.add(new AdminManagerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("address").child("village").getValue().toString(), ds.child("img_url").getValue().toString()));
                                            }
                                        }
                                    }
                                }
                            }
                            AdminManagerAdapter adapter = new AdminManagerAdapter(getContext(), arrayList);
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
                AdminManagerAdapter ob = (AdminManagerAdapter)adapterView.getAdapter();

                Intent intent = new Intent(getContext(), ManagerProfileActivity.class);
                intent.putExtra("userUID",ob.getItem(i).getId());
                intent.putExtra("usertype","new-manager");
                intent.putExtra("not_completed",true);
                startActivity(intent);
            }
        });

        return v;
    }
}