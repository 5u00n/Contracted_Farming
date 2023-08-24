package com.ashish.contractedfarming.Admin.Requests;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.ImageView;

import com.ashish.contractedfarming.Admin.AdminProfile.AdminProfileActivity;
import com.ashish.contractedfarming.Admin.Chat.AdminMessageActivity;
import com.ashish.contractedfarming.Admin.Notification.AdminNotificationActivity;
import com.ashish.contractedfarming.R;

public class AdminRequestActivity extends AppCompatActivity {

    ImageView message, request,noti, home,profile;
    Context context;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_request);

        context=getBaseContext();
        initNavBottom();

    }

    private void initNavBottom() {
        message = findViewById(R.id.admin_message_tab);
        request=findViewById(R.id.admin_requests_tab);
        noti = findViewById(R.id.admin_notification_tab);
        profile=findViewById(R.id.admin_profile_tab);


        home = findViewById(R.id.admin_home_tab);

        ImageView currentTab=request;
        currentTab.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                currentTab.setBackgroundColor(Color.TRANSPARENT);
                startActivity(new Intent(context, AdminRequestActivity.class));
                finish();
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });



        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                currentTab.setBackgroundColor(Color.TRANSPARENT);
                startActivity(new Intent(context, AdminRequestActivity.class));
                finish();
                overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                currentTab.setBackgroundColor(Color.TRANSPARENT);
                startActivity(new Intent(context, AdminMessageActivity.class));
                finish();
                overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noti.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                currentTab.setBackgroundColor(Color.TRANSPARENT);
                startActivity(new Intent(context, AdminNotificationActivity.class));

                finish();
                overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                currentTab.setBackgroundColor(Color.TRANSPARENT);
                startActivity(new Intent(context, AdminProfileActivity.class));
                finish();
                overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }




}