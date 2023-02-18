package com.ashish.contractedfarming.Admin.FarmerControl.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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


    ViewPager viewPager;
    TabLayout tabLayout;

    Intent intent ;

    String UID,usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_profile);


        intent=getIntent();
        if (intent != null) {
            UID = intent.getStringExtra("userUID");
            usertype = intent.getStringExtra("usertype");
        }




        viewPager = findViewById(R.id.Farmer_verification_viewPager);

        tabLayout = findViewById(R.id.farmer_verification_tabLayout);Log.d("usertype",usertype);
        if(usertype.equals("farmer")) {

            tabLayout.addTab(tabLayout.newTab().setText("Plot"));
        }
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("Identity"));
        tabLayout.addTab(tabLayout.newTab().setText("Farm Doc"));



        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);




        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("users").child(usertype).child(UID);
        if(usertype.equals("farmer")) {
            viewPager.setCurrentItem(2);
        }

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viewPager.setAdapter(new FarmerProfileAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),snapshot));
                if(usertype.equals("farmer")) {
                    viewPager.setCurrentItem(1);

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

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });

}
}
