package com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.PlantAddition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.PlantPopUp.PlantPopViewPagerAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.FarmerDashboardActivity;
import com.ashish.contractedfarming.Farmer.Dashboard.FarmerMyManager.FarmerManagerActivity;
import com.ashish.contractedfarming.Models.NotificationModel;
import com.ashish.contractedfarming.Models.RequestModel;
import com.ashish.contractedfarming.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FarmerExplorePlantsActivity extends AppCompatActivity {

    FirebaseAuth auth;

    FirebaseDatabase database;
    DatabaseReference reference,referenceManager;

    ViewPager pager;
    ImageButton imageButton;
    Button add_button;


    String plant_id,plant_name,plant_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_explore_plants);

        auth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        reference = database.getReference("plants");
        referenceManager=database.getReference("users").child("farmer").child(auth.getUid()).child("my_manager");

        plant_id=getIntent().getExtras().getString("plant_id");
        plant_url=getIntent().getExtras().getString("plant_url");
        plant_name=getIntent().getExtras().getString("plant_name");



        imageButton= findViewById(R.id.farmer_explore_plant_popup_back_button);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), FarmerDashboardActivity.class).putExtra("tab_open","plants"));
                finish();
            }
        });

        add_button= findViewById(R.id.farmer_explore_plant_popup_add_plant_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                referenceManager.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Intent i = new Intent(FarmerExplorePlantsActivity.this, FarmerSelectFarmActivity.class);
                            i.putExtra("plant_id",plant_id);
                            i.putExtra("plant_name",plant_name);
                            i.putExtra("plant_url",plant_url);
                            i.putExtra("manager_id",snapshot.child("manager_id").getValue().toString());
                            i.putExtra("manager_name",snapshot.child("manager_name").getValue().toString());
                            i.putExtra("manager_img_url",snapshot.child("manager_img_url").getValue().toString());
                            startActivity(i);
                            finish();
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FarmerExplorePlantsActivity.this);

                            builder.setTitle("Info : You don't have allotted Manger !");
                            builder.setMessage("You don't have allotted manager, Press OK to open New Manager Request !");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog
                                    startActivity(new Intent(FarmerExplorePlantsActivity.this,FarmerManagerActivity.class));
                                    dialog.dismiss();
                                }
                            });

                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // Do nothing
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });



        if(plant_id!=null) {
            reference.child(plant_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    PlantPopViewPagerAdapter adapter = new PlantPopViewPagerAdapter(FarmerExplorePlantsActivity.this, snapshot);
                    pager =findViewById(R.id.farmer_explore_plant_popup_viewpager);
                    TabLayout tabLayout = findViewById(R.id.farmer_explore_plant_popup_tablayout);
                    tabLayout.setupWithViewPager(pager);
                    pager.setAdapter(adapter);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }




    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}