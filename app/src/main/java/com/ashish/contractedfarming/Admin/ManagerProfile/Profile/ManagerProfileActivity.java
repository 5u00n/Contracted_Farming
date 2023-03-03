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
    DatabaseReference reference,reff_alluser;


    Button confirm, reject;

    Intent intent;
    String userID, usertype;

    Boolean not_completed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_verification);

        confirm = findViewById(R.id.agent_verification_button_next);
        reject = findViewById(R.id.agent_verification_button_reject);

        intent = getIntent();
        if (intent != null) {
            userID = intent.getStringExtra("userUID");
            usertype = intent.getStringExtra("usertype");
            not_completed = intent.getBooleanExtra("not_completed", false);
        }

        viewPager = findViewById(R.id.agent_verification_viewpager);
        tabLayout = findViewById(R.id.agent_verification_tab_layout);

        if (usertype.equals("manager")) {
            confirm.setVisibility(View.GONE);
            reject.setVisibility(View.VISIBLE);
            tabLayout.addTab(tabLayout.newTab().setText("Farmers"));
        }

        tabLayout.addTab(tabLayout.newTab().setText("Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Identity"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        if (usertype.equals("manager")) {
            viewPager.setCurrentItem(1);
        }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        reff_alluser= database.getReference("all-users");

        reference.child(usertype).child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    viewPager.setAdapter(new ManagerProfileAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), snapshot));
                    if (usertype.equals("manager")) {
                        viewPager.setCurrentItem(1);
                    }
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
                if (tab.getPosition() == 0) {
                    confirm.setText("Next");
                    reject.setVisibility(View.GONE);
                }
                if (tab.getPosition() == 1) {
                    if (usertype.equals("manager") || not_completed)
                        confirm.setVisibility(View.GONE);
                    confirm.setText("Confirm");

                    if (!not_completed)
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
                if (confirm.getText().toString().equals("Next")) {
                    Log.d("from click", confirm.getText().toString());
                    confirm.setText("Confirm");
                    viewPager.setCurrentItem(1);
                    reject.setVisibility(View.VISIBLE);
                } else {

                    reference.child(usertype).child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                reference.child("manager").child(userID).setValue(snapshot.getValue());
                                reference.child(usertype).child(userID).removeValue();
                                reff_alluser.child(userID).child("usertype").setValue("manager");
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(usertype).child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            reference.child("rej-manager").child(userID).setValue(snapshot.getValue());
                            reference.child(usertype).child(userID).removeValue();
                            reff_alluser.child(userID).child("usertype").setValue("rej-manager");
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }
}



