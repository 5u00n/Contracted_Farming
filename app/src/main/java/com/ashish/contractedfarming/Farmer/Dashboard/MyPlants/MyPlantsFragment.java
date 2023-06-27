package com.ashish.contractedfarming.Farmer.Dashboard.MyPlants;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.contractedfarming.Farmer.Dashboard.MyPlants.FragmentHelper.FarmerFragmentMyPlantsAdapter;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MyPlantsFragment extends Fragment {

    RecyclerView recyclerView;

    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseAuth auth;

    ArrayList<MyPlantModel> plantModelArrayList;


    public MyPlantsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_plants, container, false);

        recyclerView=v.findViewById(R.id.farmer_my_plants_fragment_recycle_view);
        auth=FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        reference= database.getReference();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plantModelArrayList= new ArrayList<>();

                MyPlantModel plantModel=null;

                if(snapshot.child("users").child("farmer").child(auth.getUid()).child("myplant").hasChildren()){
                    for(DataSnapshot mpds :snapshot.child("users").child("farmer").child(auth.getUid()).child("my_plant").getChildren() ){
                        int statusDays = 0;
                        if(mpds.child("planted_time").exists() && snapshot.child("plants").child(mpds.child("plant_id").getValue().toString()).child("lifespan").exists()){
                            statusDays= (int) ((TimeUnit.MILLISECONDS.toDays(new Date(System.currentTimeMillis()).getTime()-new Date(Long.parseLong(mpds.child("planted_time").getValue().toString())).getTime()))/Long.parseLong(snapshot.child("plants").child(mpds.child("plant_id").getValue().toString()).child("lifespan").getValue().toString()))*100;
                        }else{
                            statusDays=1;
                        }

                        plantModel= new MyPlantModel(mpds.child("myPlant_id").getValue().toString(),mpds.child("plant_status").getValue().toString(),statusDays,snapshot.child("plants").child(mpds.child("plant_id").getValue().toString()).child("imgurl").getValue().toString(),snapshot.child("plants").child(mpds.child("plant_id").getValue().toString()).child("name").getValue().toString(),snapshot.child("users").child("farmer").child(auth.getUid()).child("plot").child(mpds.child("plot_id").getValue().toString()).child("name").getValue().toString());

                        plantModelArrayList.add(plantModel);

                    }
                }

                if(!plantModelArrayList.isEmpty()){
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                    FarmerFragmentMyPlantsAdapter adapter = new FarmerFragmentMyPlantsAdapter(plantModelArrayList,getContext());
                    recyclerView.setAdapter(adapter);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        return v;
    }
}