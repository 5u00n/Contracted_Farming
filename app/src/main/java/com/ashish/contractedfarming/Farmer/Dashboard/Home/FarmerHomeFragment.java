package com.ashish.contractedfarming.Farmer.Dashboard.Home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.AdminPlantsModel;
import com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.FarmerExploreplantsAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.FarmerDashboardActivity;
import com.ashish.contractedfarming.Farmer.Dashboard.MyFarm.FarmerMyfarmAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.MyFarm.FarmerMyfarmModel;
import com.ashish.contractedfarming.Farmer.Dashboard.MyPlants.FarmerMyplantsAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.FarmerStoryAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.FarmerStoryModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FarmerHomeFragment extends Fragment {

    Context context;

    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseAuth auth;


    RecyclerView farmerstoryRV, explorePlantsRv, myPlantsRv, myFarmRv;


    ArrayList<FarmerStoryModel> farmerstoryList;
    ArrayList<AdminPlantsModel> exploreplantList;
    ArrayList<AdminPlantsModel> myplantList;
    ArrayList<FarmerMyfarmModel> myfarmList;

    public FarmerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=getContext();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_farmer_home, container, false);

        database= FirebaseDatabase.getInstance();
        reference= database.getReference();
        auth= FirebaseAuth.getInstance();


        farmerstoryRV = v.findViewById(R.id.farmerstoryRV);
        //explorePlantsRv = v.findViewById(R.id.farmer_exploreplantsrv);
        myPlantsRv = v.findViewById(R.id.farmer_myplantsrv);
         myFarmRv = v.findViewById(R.id.farmer_myfarmRv);

         //initMyPlants();
        updateView();
        return v;
    }


    void updateView(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    if(snapshot.child("plants").exists()){
                        initExplorePlants(snapshot.child("plants"));
                    }
                    if(snapshot.child("users").child("farmer").child(auth.getUid()).child("plot").exists()){
                       initMyFarm(snapshot.child("users").child("farmer").child(auth.getUid()).child("plot"));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void initMyPlants(DataSnapshot myPlants){
        ArrayList<AdminPlantsModel> myplantList = new ArrayList<>();

        for(DataSnapshot p_ds : myPlants.getChildren()) {
            myplantList.add(new AdminPlantsModel(p_ds.child("id").getValue().toString(),p_ds.child("name").getValue().toString() ,p_ds.child("imgurl").getValue().toString()));

        }
        FarmerMyplantsAdapter adapter3 = new FarmerMyplantsAdapter(myplantList, context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        myPlantsRv.setLayoutManager(layoutManager);
        myPlantsRv.setNestedScrollingEnabled(false);
        myPlantsRv.setAdapter(adapter3);
    }


    public void initExplorePlants(DataSnapshot allPlants){



        ArrayList<AdminPlantsModel> exploreplantList = new ArrayList<>();


        for(DataSnapshot p_ds : allPlants.getChildren()) {
            exploreplantList.add(new AdminPlantsModel(p_ds.child("id").getValue().toString(),p_ds.child("name").getValue().toString() ,p_ds.child("imgurl").getValue().toString()));

        }
        FarmerExploreplantsAdapter adapter2 = new FarmerExploreplantsAdapter(exploreplantList, context);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        explorePlantsRv.setLayoutManager(layoutManager2);
        explorePlantsRv.setNestedScrollingEnabled(false);
        explorePlantsRv.setAdapter(adapter2);

    }

    public void initMyFarm(DataSnapshot myFarm){
        myfarmList = new ArrayList<>();

        for(DataSnapshot fds:myFarm.getChildren()) {
            FarmerMyfarmModel fm=new FarmerMyfarmModel(fds.child("plotID").getValue().toString(), fds.child("userUID").getValue().toString(), fds.child("area").getValue().toString(), fds.child("village").getValue().toString(), fds.child("taluka").getValue().toString(), fds.child("dist").getValue().toString(), fds.child("state").getValue().toString(), fds.child("gat_no").getValue().toString(), fds.child("sarvay_no").getValue().toString(), "Nil");
            if(fds.child("plant").exists()){
                fm.setPlant(fds.child("plant").getValue().toString());
            }
            myfarmList.add(fm);
               // myfarmList.add(new FarmerMyfarmModel("1", "11", "1", "pune", "pune", "pune", "maha", "55", "10", "tree"));
        }
        FarmerMyfarmAdapter adapter4 = new FarmerMyfarmAdapter(myfarmList, context);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        myFarmRv.setLayoutManager(layoutManager4);
        myFarmRv.setAdapter(adapter4);
    }

/*
    public void initStory(){


        constraintLayout= findViewById(R.id.addStory_layout);

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(FarmerDashboardActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.prompt_add_story);

                EditText story_disc;
                Button addStory,cancel;
                story_disc= dialog.findViewById(R.id.prompt_add_story_disc);
                story_img= dialog.findViewById(R.id.prompt_add_story_img);





                story_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, ADD_STORY_IMG);
                    }
                });


                addStory= dialog.findViewById(R.id.prompt_add_story_button_post);
                cancel= dialog.findViewById(R.id.prompt_add_story_button_cancel);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                addStory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String time_stamp=String.valueOf((System.currentTimeMillis() / 1000));
                        if(story_disc.getText().toString().isEmpty() ) {

                        }else{

                        }

                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });

        farmerstoryList = new ArrayList<>();
        farmerstoryList.add(new FarmerStoryModel("Ashish", "BB", "https://www.google.com/imgres?imgurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Fthumb%2Fmsid-97672613%2Cwidth-1200%2Cheight-900%2Cresizemode-4%2F97672613.jpg&imgrefurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Findia%2Fbreaking-news-live-updates-7-2-2023%2Fliveblog%2F97672613.cms&tbnid=KEbAPKnwf0U_zM&vet=12ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ..i&docid=KTKj333gdYGDjM&w=1200&h=900&q=news&ved=2ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ","hELLO",""));
        farmerstoryList.add(new FarmerStoryModel("Ashish", "BB", "https://www.google.com/imgres?imgurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Fthumb%2Fmsid-97672613%2Cwidth-1200%2Cheight-900%2Cresizemode-4%2F97672613.jpg&imgrefurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Findia%2Fbreaking-news-live-updates-7-2-2023%2Fliveblog%2F97672613.cms&tbnid=KEbAPKnwf0U_zM&vet=12ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ..i&docid=KTKj333gdYGDjM&w=1200&h=900&q=news&ved=2ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ","Found Business",""));


        FarmerStoryAdapter adapter1 = new FarmerStoryAdapter(farmerstoryList, context);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        farmerstoryRV.setLayoutManager(layoutManager1);
        farmerstoryRV.setNestedScrollingEnabled(false);
        farmerstoryRV.setAdapter(adapter1);

    }

 */




}