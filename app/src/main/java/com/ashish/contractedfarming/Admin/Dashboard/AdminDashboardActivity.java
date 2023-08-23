package com.ashish.contractedfarming.Admin.Dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ashish.contractedfarming.Admin.Chat.AdminMessageActivity;
import com.ashish.contractedfarming.Admin.Notification.AdminNotificationActivity;
import com.ashish.contractedfarming.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminDashboardActivity extends AppCompatActivity {

    TabLayout tabLayout, tabLayoutNav;
    ViewPager viewPager;

    FirebaseDatabase database;
    DatabaseReference reference;

    DashboardAdapter adapter;




    CardView farmer,agent;
    ImageView message, noti, home;

    FirebaseAuth auth;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        auth=FirebaseAuth.getInstance();

        database.getReference().child("all-users").child(auth.getUid()).child("online_status").setValue("online");


        tabLayout = findViewById(R.id.tabs_layout);
        viewPager = findViewById(R.id.view_pager);



        message = findViewById(R.id.admin_message_tab);
        noti = findViewById(R.id.admin_notification_tab);

        home = findViewById(R.id.admin_home_tab);
        home.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                home.setBackgroundColor(Color.TRANSPARENT);
                startActivity(new Intent(AdminDashboardActivity.this, AdminMessageActivity.class));
                finish();
                overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noti.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                home.setBackgroundColor(Color.TRANSPARENT);
                startActivity(new Intent(AdminDashboardActivity.this, AdminNotificationActivity.class));

                finish();
                overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        //  tabLayoutNav = findViewById(R.id.tabs_layout_nav);

        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.addTab(tabLayout.newTab().setText("Conference"));
        tabLayout.addTab(tabLayout.newTab().setText("Requests"));
        tabLayout.addTab(tabLayout.newTab().setText("Plant"));
        tabLayout.addTab(tabLayout.newTab().setText("Farmer"));
        tabLayout.addTab(tabLayout.newTab().setText("Manager"));




        adapter = new DashboardAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = adapter.getItem(viewPager.getCurrentItem());
        ((Fragment) fragment).onActivityResult(requestCode, resultCode, data);
        //Log.d("Image data  ", String.valueOf(data));
    }

    public void updateUI() {

    }

    @Override
    protected void onResume() {
        super.onResume();
       // database.getReference().child("all-users").child(auth.getUid()).child("online_status").setValue("online");
    }

    @Override
    protected void onStop() {
        super.onStop();
       // database.getReference().child("all-users").child(auth.getUid()).child("online_status").setValue("offline");
    }


    @Override
    protected void onPause() {
        super.onPause();
        FirebaseDatabase.getInstance().getReference("all-users").child(FirebaseAuth.getInstance().getUid()).child("online_status").setValue("offline");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference("all-users").child(FirebaseAuth.getInstance().getUid()).child("online_status").setValue("online");
    }
}