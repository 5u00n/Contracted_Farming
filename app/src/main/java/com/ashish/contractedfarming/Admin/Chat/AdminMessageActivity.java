package com.ashish.contractedfarming.Admin.Chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ashish.contractedfarming.Admin.AdminProfile.AdminProfileActivity;
import com.ashish.contractedfarming.Admin.Dashboard.AdminDashboardActivity;
import com.ashish.contractedfarming.Admin.Notification.AdminNotificationActivity;
import com.ashish.contractedfarming.Admin.Requests.AdminRequestActivity;
import com.ashish.contractedfarming.Farmer.Chat.ChatHelper.FarmerSearchUserActivity;
import com.ashish.contractedfarming.Farmer.Chat.ChatHelper.FarmerSpecificChatActivity;
import com.ashish.contractedfarming.Farmer.Chat.FarmerChatActivity;
import com.ashish.contractedfarming.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminMessageActivity extends AppCompatActivity {


    TabLayout tabLayout, tabLayoutNav;
    ViewPager viewPager;

    Context context;

    FirebaseDatabase database;
    DatabaseReference reference;

    FloatingActionButton floatingActionButton;
    AdminMessageAdpter adminMessageAdpter;

    FirebaseAuth auth;

    ImageView message, request,noti, home,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_message);
        context=getBaseContext();

        database=FirebaseDatabase.getInstance();
        auth= FirebaseAuth.getInstance();
        database.getReference().child("all-users").child(auth.getUid()).child("online_status").setValue("online");



        initNavBottom();

        floatingActionButton= findViewById(R.id.admin_addchat_floating);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(context, FarmerSearchUserActivity.class),2006);
            }
        });

        ViewPager viewPager = findViewById(R.id.admin_message_view_pager);

        TabLayout tabLayout = findViewById(R.id.admin_message_tabs_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Chats"));
        tabLayout.addTab(tabLayout.newTab().setText("Status"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        adminMessageAdpter= new AdminMessageAdpter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adminMessageAdpter);
        viewPager.getCurrentItem();

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition()==1){
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
                else{
                    floatingActionButton.setVisibility(View.GONE);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }




    private void initNavBottom() {
        message = findViewById(R.id.admin_message_tab);
        request=findViewById(R.id.admin_requests_tab);
        noti = findViewById(R.id.admin_notification_tab);
        profile=findViewById(R.id.admin_profile_tab);


        home = findViewById(R.id.admin_home_tab);

        ImageView currentTab=message;
        currentTab.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                currentTab.setBackgroundColor(Color.TRANSPARENT);
                startActivity(new Intent(context, AdminDashboardActivity.class));
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
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //message.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                //currentTab.setBackgroundColor(Color.TRANSPARENT);
                //startActivity(new Intent(context, AdminMessageActivity.class));
                //finish();
                //overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);
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

    /*
    @Override
    protected void onStop() {
        super.onStop();
        DatabaseReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status","Offline").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Now User is Offline",Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        documentReference.update("status","Online").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Now User is Online",Toast.LENGTH_SHORT).show();
            }
        });

    }
    */
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if(requestCode==2006 && resultCode==2006){
        String receiver_id=data.getStringExtra("receiver_id");
        startActivity(new Intent(AdminMessageActivity.this, FarmerSpecificChatActivity.class).putExtra("receiver_id",receiver_id));
    }

    //Log.d("Image data  ", String.valueOf(data));
}


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //database.getReference().child("all-users").child(auth.getUid()).child("online_status").setValue("offline");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //database.getReference().child("all-users").child(auth.getUid()).child("online_status").setValue("offline");
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