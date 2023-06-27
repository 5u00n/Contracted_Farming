package com.ashish.contractedfarming.Manager.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.ashish.contractedfarming.Admin.FarmerControl.FarmerControlAdapter;
import com.ashish.contractedfarming.R;
import com.google.android.material.tabs.TabLayout;

public class ManagerDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dashboard);


        ViewPager viewPager = findViewById(R.id.manager_view_pager);

        TabLayout tabLayout = findViewById(R.id.manager_tabs_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Dashboard"));
        tabLayout.addTab(tabLayout.newTab().setText("Tasks"));
        tabLayout.addTab(tabLayout.newTab().setText("Requests"));
        tabLayout.addTab(tabLayout.newTab().setText("Connections"));
        tabLayout.addTab(tabLayout.newTab().setText("Requirments"));
        tabLayout.addTab(tabLayout.newTab().setText("My Area"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager.setAdapter(new ManagerDashboardAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));

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