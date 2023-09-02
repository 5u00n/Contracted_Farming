package com.ashish.contractedfarming.Admin.Requests;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ashish.contractedfarming.Admin.AdminProfile.AdminProfileActivity;
import com.ashish.contractedfarming.Admin.Chat.AdminMessageActivity;
import com.ashish.contractedfarming.Admin.Dashboard.AdminDashboardActivity;
import com.ashish.contractedfarming.Admin.Dashboard.Farmer.FarmerProfile.Profile.FarmerPlant.AdminFarmerPlantActivity;
import com.ashish.contractedfarming.Admin.Dashboard.Manager.AdminManagerAdapter;
import com.ashish.contractedfarming.Admin.Dashboard.Manager.AdminManagerModel;
import com.ashish.contractedfarming.Admin.Dashboard.News.AdminNewsModel;
import com.ashish.contractedfarming.Admin.Notification.AdminNotificationActivity;
import com.ashish.contractedfarming.Farmer.Dashboard.MyRequest.FragmentHelper.FarmerMyRequestRecycleAdapter;
import com.ashish.contractedfarming.Models.NotificationModel;
import com.ashish.contractedfarming.Models.RequestModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdminRequestActivity extends AppCompatActivity {

    ImageView message, request,noti, home,profile;
    Context context;


    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    ArrayList<RequestModel> requestModelArrayList;

    RecyclerView recyclerView;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_request);

        context=AdminRequestActivity.this;
        initNavBottom();



        auth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        reference=database.getReference();

        recyclerView= findViewById(R.id.admin_request_rv);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotAll) {

                DataSnapshot snapshot=snapshotAll.child("requests");
                requestModelArrayList = new ArrayList<>();
                if (snapshot.hasChildren())
                    for (DataSnapshot fds : snapshot.getChildren()) {
                        RequestModel fm=null;
                        //Log.d("Admin Requests",new Gson().toJson(fds.getValue()));
                        if(fds.child("send_to").getValue().toString().toLowerCase().contains("admin")) {


                            fm = new RequestModel(fds.child("id").getValue().toString(), fds.child("sender_id").getValue().toString(),snapshotAll.child("all-users").child(fds.child("sender_id").getValue().toString()).child("username").getValue().toString()+"_"+snapshotAll.child("all-users").child(fds.child("sender_id").getValue().toString()).child("usertype").getValue().toString(),fds.child("send_to").getValue().toString(), fds.child("type").getValue().toString(), fds.child("type_id").getValue().toString(), fds.child("date_of_creation").getValue().toString(),fds.child("checked").getValue().toString(),fds.child("stared").getValue().toString(),fds.child("message").getValue().toString());
                            //Log.d("Admin Requests",new Gson().toJson(fm));
                        }
                        if(fm!=null) {
                            requestModelArrayList.add(fm);
                        }
                    }

                AdminRequestRecycleAdapter adapter1 = new AdminRequestRecycleAdapter(requestModelArrayList, context);


                adapter1.setOnItemClickListener(new AdminRequestRecycleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClicked(String type_id, String req_id, String sender_id,String sender_type,int checked,Boolean bool) {


                        if(type_id.contains("stared")) {

                            reference.child("requests").child(req_id).child("stared").setValue(bool ? "00" : "11");
                            reference.child("users").child(sender_type).child(sender_id).child("requests").child(req_id).child("stared").setValue(bool ? "00" : "11");
                            reference.child("users").child(sender_type).child(sender_id).child("notifications").child("noti_" + req_id).setValue(new NotificationModel("noti_" + req_id, "admin", auth.getUid(), (bool ? "removed star from" : "stared" + " your request on the date : ") + new SimpleDateFormat("dd MMM YYYY hh:mm a").format(Calendar.getInstance().getTime().getTime()) + " click to check ! ", String.valueOf(Calendar.getInstance().getTime().getTime() / 1000), "requests", "false"));
                        }
                        if(type_id.contains("farmer-plant")) {
                            Log.d("click requests farmer Plants",type_id+" "+req_id);
                            reference.child("requests").child(req_id).child("checked").setValue(checked == 11 || checked == 10 ? checked : checked + 10);
                            reference.child("users").child(sender_type).child(sender_id).child("requests").child(req_id).child("checked").setValue(checked == 11 || checked == 10 ? checked : checked + 10);
                            reference.child("users").child(sender_type).child(sender_id).child("notifications").child("noti_" + req_id).setValue(new NotificationModel("noti_" + req_id, "admin", auth.getUid(), "opened your request on the date : " + new SimpleDateFormat("dd MMM YYYY hh:mm a").format(Calendar.getInstance().getTime().getTime()) + " click to check ! ", String.valueOf(Calendar.getInstance().getTime().getTime() / 1000), "requests", "false"));
                            startActivity(new Intent(context, AdminFarmerPlantActivity.class).putExtra("farmer_plant_id", type_id));
                        }
                        if(type_id.contains("user")){
                            Log.d("click requests ",type_id+" "+req_id);
                                Toast.makeText(context,"View Clicked Users ",Toast.LENGTH_SHORT).show();
                        }
                        if(type_id.contains("new-manager")){
                            Log.d("click requests Manager ",type_id+" "+req_id);
                            reference.child("requests").child(req_id).child("checked").setValue(checked == 11 || checked == 10 ? checked : checked + 10);
                            reference.child("users").child(sender_type).child(sender_id).child("requests").child(req_id).child("checked").setValue(checked == 11 || checked == 10 ? checked : checked + 10);
                            reference.child("users").child(sender_type).child(sender_id).child("notifications").child("noti_" + req_id).setValue(new NotificationModel("noti_" + req_id, "admin", auth.getUid(), "opened your request on the date : " + new SimpleDateFormat("dd MMM YYYY hh:mm a").format(Calendar.getInstance().getTime().getTime()) + " click to check ! ", String.valueOf(Calendar.getInstance().getTime().getTime() / 1000), "requests", "false"));
                            //Toast.makeText(context,"View Clicked New Manager Request",Toast.LENGTH_SHORT).show();
                            LayoutInflater li = LayoutInflater.from(context);
                            View promptsView = li.inflate(R.layout.prompt_admin_select_manager, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);


                            final ListView listView=promptsView.findViewById(R.id.prompt_admin_select_manager_list);
                            List<AdminManagerModel> arrayList = new ArrayList<>();
                            reference.child("users").child("manager").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    AdminManagerAdapter adapter = new AdminManagerAdapter(context, arrayList);
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        arrayList.add(new AdminManagerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("address").child("village").getValue().toString(), ds.child("img_url").getValue().toString()));
                                    }
                                    if (adapter != null) {
                                        listView.setAdapter(adapter);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });



                            //alertDialogBuilder.setTitle(model.getTopic());

                            // set prompts.xml to alertdialog builder
                            alertDialogBuilder.setView(promptsView);

                            alertDialogBuilder.setCancelable(false).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            // show it
                            alertDialog.show();
                        }
                    }
                });


                LinearLayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager1);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(adapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                startActivity(new Intent(context, AdminDashboardActivity.class));
                finish();
                overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });



        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //request.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
                //currentTab.setBackgroundColor(Color.TRANSPARENT);
                //startActivity(new Intent(context, AdminRequestActivity.class));
                //finish();
                //overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);
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