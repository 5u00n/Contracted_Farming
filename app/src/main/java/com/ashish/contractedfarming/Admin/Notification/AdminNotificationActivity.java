package com.ashish.contractedfarming.Admin.Notification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.contractedfarming.Admin.AdminProfile.AdminProfileActivity;
import com.ashish.contractedfarming.Admin.Chat.AdminMessageActivity;
import com.ashish.contractedfarming.Admin.Dashboard.AdminDashboardActivity;
import com.ashish.contractedfarming.Admin.Requests.AdminRequestActivity;
import com.ashish.contractedfarming.Farmer.Notification.FarmerNotificationAdapter;
import com.ashish.contractedfarming.Models.NotificationModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdminNotificationActivity extends AppCompatActivity {


    ImageView message, request,noti, home,profile,currentTab;
    Context context;
    Toolbar toolbar;

    RecyclerView rv;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<NotificationModel> list ;


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification);

        context=getBaseContext();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        rv = findViewById(R.id.admin_notification_rv);


        initNavBottom();



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    DataSnapshot notification_snaps=snapshot.child("users").child("admin").child(auth.getUid()).child("notifications");
                    //DataSnapshot sender_snaps= snapshot.child("all-users").child()
                    list= new ArrayList<>();
                    for (DataSnapshot ds : notification_snaps.getChildren()) {
                        list.add(new NotificationModel(ds.child("not_id").getValue().toString(),snapshot.child("all-users").child(ds.child("creator").getValue().toString()).child("usertype").getValue().toString(),snapshot.child("all-users").child(ds.child("creator").getValue().toString()).child("username").getValue().toString(), ds.child("message").getValue().toString(), ds.child("date_created").getValue().toString(), ds.child("type").getValue().toString(),ds.child("seen").getValue().toString()));
                    }

                    Collections.sort(list, new Comparator<NotificationModel>() {
                        @Override
                        public int compare(NotificationModel o1, NotificationModel o2) {
                            return o1.getDate_created().compareTo(o2.getDate_created());
                        }
                    });


                    FarmerNotificationAdapter adapter = new FarmerNotificationAdapter(context, list);



                    adapter.setOnItemClickListener(new FarmerNotificationAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClicked(String not_id,String notification_type) {
                            reference.child("users").child("farmer").child(auth.getUid()).child("notifications").child(not_id).child("seen").setValue("true");
                            switch (notification_type) {
                                case "farms":
                                    gotoHome("farms");
                                    break;
                                case "farmer_plants":
                                    gotoHome("farmer_plants");
                                    break;
                                case "plants":
                                    gotoHome("plants");
                                    break;
                                case "posts":
                                    gotoHome();
                                    break;
                                case "conference":
                                    gotoConf();
                                    break;
                                case "news":
                                    gotoNews();
                                    break;
                            }
                        }
                    });
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);


                    rv.setLayoutManager(layoutManager);
                    rv.setAdapter(adapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void gotoNews() {
    }

    private void gotoConf() {
        request.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
        currentTab.setBackgroundColor(Color.TRANSPARENT);
        startActivity(new Intent(context, AdminRequestActivity.class));
        finish();
        overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void gotoHome() {
        home.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
        currentTab.setBackgroundColor(Color.TRANSPARENT);
        startActivity(new Intent(context, AdminDashboardActivity.class));
        finish();
        overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right);
    }

    private void gotoHome(String farms) {
        
    }

    private void initNavBottom() {
        message = findViewById(R.id.admin_message_tab);
        request=findViewById(R.id.admin_requests_tab);
        noti = findViewById(R.id.admin_notification_tab);
        profile=findViewById(R.id.admin_profile_tab);


        home = findViewById(R.id.admin_home_tab);

        currentTab=noti;
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
                message.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                currentTab.setBackgroundColor(Color.TRANSPARENT);
                startActivity(new Intent(context, AdminMessageActivity.class));
                finish();
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noti.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                //currentTab.setBackgroundColor(Color.TRANSPARENT);
                //startActivity(new Intent(context, AdminNotificationActivity.class));

                //finish();
                //overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);
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