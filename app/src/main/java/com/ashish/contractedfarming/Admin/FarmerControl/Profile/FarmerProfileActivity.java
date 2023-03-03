package com.ashish.contractedfarming.Admin.FarmerControl.Profile;

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

public class FarmerProfileActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;

    FirebaseDatabase database;
    DatabaseReference reference, reff_alluser;


    Button confirm, reject;

    Intent intent;
    String userID, usertype;

    Boolean not_completed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_profile);

        confirm = findViewById(R.id.agent_verification_button_next);
        reject = findViewById(R.id.agent_verification_button_reject);

        intent = getIntent();
        if (intent != null) {
            userID = intent.getStringExtra("userUID");
            usertype = intent.getStringExtra("usertype");
            not_completed = intent.getBooleanExtra("not_completed", false);
        }

        viewPager = findViewById(R.id.Farmer_verification_viewPager);
        tabLayout = findViewById(R.id.farmer_verification_tabLayout);


        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("Plot"));
        tabLayout.addTab(tabLayout.newTab().setText("Identity"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        reff_alluser = database.getReference("all-users");


        reference.child(usertype).child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    viewPager.setAdapter(new FarmerProfileAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), snapshot));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // viewPager.setAdapter(new FarmerVerificationAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),UID));
        /// viewPager.getCurrentItem();

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0 || tab.getPosition() == 1) {
                    confirm.setText("Next");
                    reject.setVisibility(View.GONE);
                    confirm.setVisibility(View.VISIBLE);
                }
                if (tab.getPosition() == 2) {
                    if (usertype.equals("farmer") || not_completed)
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

                    if (viewPager.getCurrentItem() != 2) {
                        confirm.setText("Confirm");
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

                    }
                } else {

                    reference.child(usertype).child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                reference.child("farmer").child(userID).setValue(snapshot.getValue());
                                reference.child(usertype).child(userID).removeValue();
                                reff_alluser.child(userID).child("usertype").setValue("farmer");
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
                            reference.child("rej-farmer").child(userID).setValue(snapshot.getValue());
                            reference.child(usertype).child(userID).removeValue();
                            reff_alluser.child(userID).child("usertype").setValue("rej-farmer");
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
