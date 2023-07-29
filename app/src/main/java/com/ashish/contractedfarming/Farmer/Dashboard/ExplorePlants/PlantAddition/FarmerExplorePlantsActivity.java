package com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.PlantAddition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.PlantPopUp.PlantPopViewPagerAdapter;
import com.ashish.contractedfarming.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FarmerExplorePlantsActivity extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference reference;

    ViewPager pager;
    ImageButton imageButton;
    Button add_button;


    String plant_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_explore_plants);

        database= FirebaseDatabase.getInstance();
        reference = database.getReference("plants");

        plant_id=getIntent().getExtras().getString("plant_id");

        imageButton= findViewById(R.id.farmer_explore_plant_popup_back_button);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add_button= findViewById(R.id.farmer_explore_plant_popup_add_plant_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FarmerExplorePlantsActivity.this, FarmerSelectFarmActivity.class);
                i.putExtra("plant_id",plant_id);
                startActivity(i);
                finish();
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