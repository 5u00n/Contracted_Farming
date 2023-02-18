package com.ashish.contractedfarming.Admin.ManagerProfile.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ashish.contractedfarming.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class ManagerProfileActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    FirebaseDatabase database;
    DatabaseReference reference;


    Button confirm, reject;

    Intent intent;
    String userID, usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_verification);

        confirm= findViewById(R.id.agent_verification_button_next);
        reject= findViewById(R.id.agent_verification_button_reject);

        intent = getIntent();
        if (intent != null) {
            userID = intent.getStringExtra("userUID");
            usertype = intent.getStringExtra("usertype");
        }

        viewPager = findViewById(R.id.agent_verification_viewpager);
        tabLayout = findViewById(R.id.agent_verification_tab_layout);

        if(usertype.equals("manager")){
            tabLayout.addTab(tabLayout.newTab().setText("Farmers"));
        }

        tabLayout.addTab(tabLayout.newTab().setText("Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Identity"));




        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        if(usertype.equals("manager")) {
            viewPager.setCurrentItem(1);
        }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users").child(usertype).child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viewPager.setAdapter(new ManagerProfileAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), snapshot));
                if(usertype.equals("manager")) {
                    viewPager.setCurrentItem(1);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        viewPager.getCurrentItem();

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    confirm.setText("Next");
                    reject.setVisibility(View.GONE);
                }
                if(tab.getPosition()==1){
                    confirm.setText("Confirm");
                    reject.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirm.getText().toString().equals("Next")){
                    Log.d("from click",confirm.getText().toString());
                    confirm.setText("Confirm");
                    viewPager.setCurrentItem(1);
                    reject.setVisibility(View.VISIBLE);
                }else{


                }
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}



