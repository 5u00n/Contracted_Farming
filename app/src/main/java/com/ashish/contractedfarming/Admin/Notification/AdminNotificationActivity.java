package com.ashish.contractedfarming.Admin.Notification;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ashish.contractedfarming.Admin.Chat.AdminMessageActivity;
import com.ashish.contractedfarming.Admin.Dashboard.AdminDashboardActivity;
import com.ashish.contractedfarming.R;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class AdminNotificationActivity extends AppCompatActivity {


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification);








        ImageView message, noti, home;
        message = findViewById(R.id.admin_message_tab);
        noti = findViewById(R.id.admin_notification_tab);
        home = findViewById(R.id.admin_home_tab);


        noti.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                home.setBackgroundColor(Color.TRANSPARENT);
                startActivity(new Intent(AdminNotificationActivity.this, AdminDashboardActivity.class));

                finish();
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noti.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                home.setBackgroundColor(Color.TRANSPARENT);
                startActivity(new Intent(AdminNotificationActivity.this, AdminMessageActivity.class));

                finish();
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });




        ViewPager viewPager = findViewById(R.id.admin_notification_viewpager);

        TabLayout tabLayout = findViewById(R.id.notificationtabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Farmer"));
        tabLayout.addTab(tabLayout.newTab().setText("Agent"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager.setAdapter(new AdminNotificationAdapter(getSupportFragmentManager(),tabLayout.getTabCount()));
        viewPager.getCurrentItem();


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

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