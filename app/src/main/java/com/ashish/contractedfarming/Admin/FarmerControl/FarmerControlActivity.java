package com.ashish.contractedfarming.Admin.FarmerControl;

import android.os.Bundle;

import com.ashish.contractedfarming.R;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class FarmerControlActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_control);


        ViewPager viewPager = findViewById(R.id.admin_farm_control_viewpager);

        TabLayout tabLayout = findViewById(R.id.admin_farmercontrol_tabnav);

        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.addTab(tabLayout.newTab().setText("Farmers"));
        tabLayout.addTab(tabLayout.newTab().setText("Rejected"));
        tabLayout.addTab(tabLayout.newTab().setText("Connect"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager.setAdapter(new FarmerControlAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));

        viewPager.getCurrentItem();


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

