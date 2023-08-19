package com.ashish.contractedfarming.Farmer.Dashboard.MyPlants;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.ExplorePlantsFragment;
import com.ashish.contractedfarming.Farmer.Dashboard.MyPlants.FragmentHelper.FarmerFragmentMyPlantsAdapter;
import com.ashish.contractedfarming.Models.FarmerPlantModel;
import com.ashish.contractedfarming.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

    ArrayList<FarmerPlantModel> farmerPlantModelArrayList;

    CheckBox all,accepted,on_hold,rejected;

    FloatingActionButton floatingActionButton;


    public MyPlantsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_plants, container, false);

        floatingActionButton=v.findViewById(R.id.farmer_my_plant_fragment_add_floating_action_button);

        recyclerView=v.findViewById(R.id.farmer_my_plant_fragment_recycle_view);
        all=v.findViewById(R.id.farmer_my_plant_fragment_all_checkbox);
        accepted=v.findViewById(R.id.farmer_my_plant_fragment_accepted_checkbox);
        on_hold=v.findViewById(R.id.farmer_my_plant_fragment_on_hold_checkbox);
        rejected=v.findViewById(R.id.farmer_my_plant_fragment_rejected_checkbox);
        auth=FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        reference= database.getReference();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                farmerPlantModelArrayList= new ArrayList<>();

                FarmerPlantModel farmerPlantModel=null;

                if(snapshot.child("users").child("farmer").child(auth.getUid()).child("farmer_plants").hasChildren()){

                    for(DataSnapshot mpds :snapshot.child("users").child("farmer").child(auth.getUid()).child("farmer_plants").getChildren() ){
                        int statusDays = 0;
                        if(mpds.child("planted_time").exists() && snapshot.child("plants").child(mpds.child("plant_id").getValue().toString()).child("lifespan").exists()){
                            statusDays= (int) ((TimeUnit.MILLISECONDS.toDays(new Date(System.currentTimeMillis()).getTime()-new Date(Long.parseLong(mpds.child("planted_time").getValue().toString())).getTime()))/Long.parseLong(snapshot.child("plants").child(mpds.child("plant_id").getValue().toString()).child("lifespan").getValue().toString()))*100;
                        }else{
                            statusDays=1;
                        }

                        //Log.d("check data",mpds.getKey());

                        farmerPlantModel= new FarmerPlantModel(mpds.child("id").getValue().toString(),mpds.child("plot_id").getValue().toString(),mpds.child("plant_id").getValue().toString(),mpds.child("approval_admin").getValue().toString(),mpds.child("approval_manager").getValue().toString(),mpds.child("final_approval").getValue().toString(),mpds.child("date_added").getValue().toString(),mpds.child("date_accepted").getValue().toString(),mpds.child("planted_time").getValue().toString(),mpds.child("plant_img_url").getValue().toString(),mpds.child("plant_name").getValue().toString(),mpds.child("farm_img_url").getValue().toString(),mpds.child("farm_name").getValue().toString());
                        farmerPlantModelArrayList.add(farmerPlantModel);

                    }
                }
                if(!farmerPlantModelArrayList.isEmpty()){
                    //StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                    FarmerFragmentMyPlantsAdapter adapter = new FarmerFragmentMyPlantsAdapter(farmerPlantModelArrayList,getContext());
                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager1);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPager vp=getActivity().findViewById(R.id.farmer_view_pager);
                vp.setCurrentItem(2);
            }
        });
        return v;
    }
}