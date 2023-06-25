package com.ashish.contractedfarming.Farmer.Dashboard.Story;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.contractedfarming.Farmer.Dashboard.Story.FragmentHelper.FarmerFragmentStoryAdapter;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoryFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;


    ArrayList<FarmerStoryModel> farmerStory_List;

    RecyclerView recyclerView;
    public StoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_story, container, false);

        auth= FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

        recyclerView= v.findViewById(R.id.farmer_dash_story_fragment_recycle_view);

        reference.child("users").child("farmer").child(auth.getUid()).child("story").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                farmerStory_List = new ArrayList<>();
                if (snapshot.hasChildren())
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        farmerStory_List.add(new FarmerStoryModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("user_img_url").getValue().toString(), ds.child("img_url").getValue().toString(), ds.child("story_text").getValue().toString(), ds.child("story_time").getValue().toString()));
                    }

                FarmerFragmentStoryAdapter adapter1 = new FarmerFragmentStoryAdapter(farmerStory_List, getContext());
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