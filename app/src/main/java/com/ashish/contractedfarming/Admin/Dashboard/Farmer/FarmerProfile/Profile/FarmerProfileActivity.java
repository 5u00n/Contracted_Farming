package com.ashish.contractedfarming.Admin.Dashboard.Farmer.FarmerProfile.Profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ashish.contractedfarming.Admin.Dashboard.Manager.AdminManagerAdapter;
import com.ashish.contractedfarming.Admin.Dashboard.Manager.AdminManagerModel;
import com.ashish.contractedfarming.Models.NotificationModel;
import com.ashish.contractedfarming.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FarmerProfileActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference, reff_alluser;


    Button confirm, reject;

    Intent intent;
    String userID, usertype;

    Boolean not_completed;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_profile);

        context = FarmerProfileActivity.this;

        confirm = findViewById(R.id.agent_verification_button_next);
        reject = findViewById(R.id.agent_verification_button_reject);

        intent = getIntent();
        if (intent != null) {
            userID = intent.getStringExtra("userUID");
            usertype = intent.getStringExtra("usertype");
            not_completed = intent.getBooleanExtra("not_completed", false);
        }

        viewPager = findViewById(R.id.Farmer_verification_viewPager);
        tabLayout = findViewById(R.id.farmer_verification_tabLayout);


        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("Plot"));
        tabLayout.addTab(tabLayout.newTab().setText("Identity"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        auth=FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        reff_alluser = database.getReference("all-users");


        reference.child(usertype).child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    viewPager.setAdapter(new FarmerProfileAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), snapshot));

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

                if (tab.getPosition() == 0 || tab.getPosition() == 1) {
                    confirm.setText("Next");
                    reject.setVisibility(View.GONE);
                    confirm.setVisibility(View.VISIBLE);
                }
                if (tab.getPosition() == 2) {
                    if (usertype.equals("farmer") || not_completed)
                        confirm.setVisibility(View.GONE);
                    confirm.setText("Confirm");

                    if (!not_completed)
                        reject.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirm.getText().toString().equals("Next")) {

                    if (viewPager.getCurrentItem() != 2) {
                        confirm.setText("Confirm");
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

                    }
                } else {


                    final Dialog dialog = new Dialog(FarmerProfileActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.prompt_admin_select_manager);




                    final ListView listView=dialog.findViewById(R.id.prompt_admin_select_manager_list);
                    final Button buttonCancel=dialog.findViewById(R.id.prompt_admin_select_manager_cancel);
                    List<AdminManagerModel> arrayList = new ArrayList<>();
                    reference.child(usertype).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                DataSnapshot snapshotFarmer=snapshot.child("farmer").child(userID);
                                DataSnapshot snapshotManager=snapshot.child("manager");


                                reference.child("farmer").child(userID).setValue(snapshotFarmer.getValue());
                                reference.child(usertype).child(userID).removeValue();
                                reff_alluser.child(userID).child("usertype").setValue("farmer");

                                ////////////////////////


                                        if(snapshotManager.hasChildren()) {
                                            for (DataSnapshot ds : snapshotManager.getChildren()) {

                                                //Log.d("Check if manager Exist for the farmer !",ds.child("address").child("village").getValue().toString().toLowerCase()+" : "+snapshotFarmer.child("address").child("village").getValue().toString()+ds.child("address").child("village").getValue().toString().toLowerCase().contains(snapshotFarmer.child("address").child("village").getValue().toString().toLowerCase()));
                                                if(ds.child("address").child("village").getValue().toString().toLowerCase().contains(snapshotFarmer.child("address").child("village").getValue().toString().toLowerCase())){
                                                    arrayList.add(new AdminManagerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("address").child("village").getValue().toString(), ds.child("img_url").getValue().toString()));
                                                }else if(ds.child("address").child("circle").getValue().toString().toLowerCase().contains(snapshotFarmer.child("address").child("circle").getValue().toString().toLowerCase())){
                                                    arrayList.add(new AdminManagerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("address").child("village").getValue().toString(), ds.child("img_url").getValue().toString()));
                                                } else if (ds.child("address").child("taluka").getValue().toString().toLowerCase().contains(snapshotFarmer.child("address").child("taluka").getValue().toString().toLowerCase())) {
                                                    arrayList.add(new AdminManagerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("address").child("village").getValue().toString(), ds.child("img_url").getValue().toString()));
                                                } else if (ds.child("address").child("dist").getValue().toString().toLowerCase().contains(snapshotFarmer.child("address").child("dist").getValue().toString().toLowerCase())) {
                                                    arrayList.add(new AdminManagerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("address").child("village").getValue().toString(), ds.child("img_url").getValue().toString()));
                                                }

                                            }
                                        }
                                        if(context!=null) {
                                            AdminManagerAdapter adapter = new AdminManagerAdapter(context, arrayList);

                                            if (adapter != null) {
                                                listView.setAdapter(adapter);
                                            }
                                        }
                                    }





                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            AdminManagerAdapter ob = (AdminManagerAdapter) adapterView.getAdapter();


                            //Log.d("Manager Clicked !",ob.getItem(i).getId()+" , "+ob.getItem(i).getName()+ " , "+ob.getItem(i).getImgurl());

                            reference.child("users").child("farmer").child(userID).child("my_manager").child("manager_id").setValue(ob.getItem(i).getId());
                            reference.child("users").child("farmer").child(userID).child("my_manager").child("manager_name").setValue(ob.getItem(i).getName());
                            reference.child("users").child("farmer").child(userID).child("my_manager").child("manager_img_url").setValue(ob.getItem(i).getImgurl());


                            reference.child("users").child("manager").child(ob.getItem(i).getId()).child("my_farmers").child(userID).setValue(userID);

                            String timeStamp= String.valueOf(Calendar.getInstance().getTime().getTime() / 1000);
                            String not_id= "noti_"+auth.getUid()+timeStamp;
                            reference.child("users").child("manager").child(ob.getItem(i).getId()).child("notifications").child(not_id ).setValue(new NotificationModel(not_id, "admin", auth.getUid(), " added new farmer under you on date : " + new SimpleDateFormat("dd MMM YYYY hh:mm a").format(Calendar.getInstance().getTime().getTime()) + " click to check ! ", timeStamp, "add-farmer", "false"));
                            reference.child("users").child("farmer").child(userID).child("notifications").child(not_id).setValue(new NotificationModel(not_id, "admin", auth.getUid(), "allotted new manager " + ob.getItem(i).getName() + "to you on the date : " + new SimpleDateFormat("dd MMM YYYY hh:mm a").format(Calendar.getInstance().getTime().getTime()) + " click to check ! ", timeStamp, "manager-accept", "false"));


                            dialog.cancel();

                            ///////////////////////
                            finish();
                        }
                    });
                    buttonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });


                    dialog.show();


                }
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(usertype).child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            reference.child("rej-farmer").child(userID).setValue(snapshot.getValue());
                            reference.child(usertype).child(userID).removeValue();
                            reff_alluser.child(userID).child("usertype").setValue("rej-farmer");
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
