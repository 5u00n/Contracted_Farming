package com.ashish.contractedfarming.Admin.ManagerProfile;

import android.os.Bundle;

import com.ashish.contractedfarming.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class AdminManagerControlActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_agent_control);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference();





        ViewPager viewPager = findViewById(R.id.admin_agent_control_viewpager);

        TabLayout tabLayout = findViewById(R.id.admin_agent_control_tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.addTab(tabLayout.newTab().setText("Managers"));
        tabLayout.addTab(tabLayout.newTab().setText("Rejected"));
        tabLayout.addTab(tabLayout.newTab().setText("Connect"));



        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager.setAdapter(new ManagerControlAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
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

