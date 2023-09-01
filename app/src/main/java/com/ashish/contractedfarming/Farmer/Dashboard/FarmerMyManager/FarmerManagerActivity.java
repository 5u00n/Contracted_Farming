package com.ashish.contractedfarming.Farmer.Dashboard.FarmerMyManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.contractedfarming.Models.NotificationModel;
import com.ashish.contractedfarming.Models.RequestModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FarmerManagerActivity extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;


    Context context;

    ImageButton button_back;
    Button button_request_manager;

    ImageView imageView_managerProfile;
    TextView textView_managerName, textView_managerMob, textView_managerVillage, textView_managerCircle,textView_managerTaluka,textView_managerDist,textView_managerPin;

    View view_include;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_manager);

        context=getBaseContext();

        String time_stamp=String.valueOf((System.currentTimeMillis() / 1000));

        auth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        reference= database.getReference();

        button_back= findViewById(R.id.farmer_manager_back_button);
        button_request_manager= findViewById(R.id.farmer_manager_manager_request_button);

        view_include= findViewById(R.id.farmer_manager_view_include);


        imageView_managerProfile= findViewById(R.id.popup_agent_image);
        textView_managerName= findViewById(R.id.popup_agent_name);
        textView_managerMob= findViewById(R.id.popup_agent_phone);
        textView_managerVillage= findViewById(R.id.popup_agent_village);
        textView_managerCircle= findViewById(R.id.popup_agent_circle);
        textView_managerTaluka= findViewById(R.id.popup_agent_taluka);
        textView_managerDist= findViewById(R.id.popup_agent_district);
        textView_managerPin= findViewById(R.id.popup_agent_pin);


        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button_request_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add request to admin for mew manager -----------------------------------------

                AlertDialog.Builder builder = new AlertDialog.Builder(FarmerManagerActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to request for new manager?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        RequestModel requestModel=new RequestModel("requests_"+auth.getUid()  + "_"+time_stamp,auth.getUid(),"admin","mewmanager_request_"+"Manager","new-manager",time_stamp,"00","00","Please assign me a manager from my area !");
                        reference.child("users").child("farmer").child(auth.getUid()).child("requests").child("requests_"+auth.getUid()  + "_"+time_stamp).setValue(requestModel);
                        reference.child("requests").child("requests_"+auth.getUid()  +"_"+ time_stamp).setValue(requestModel);


                        reference.child("users").child("admin").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot snapshotUsers : snapshot.getChildren()) {
                                        reference.child("users").child(snapshot.getKey()).child(snapshotUsers.child("userUID").getValue().toString()).child("notifications").child("noti_" + "manager-request_" + auth.getUid()  + "_" + time_stamp).setValue(new NotificationModel("noti_" + "manger-request_" + auth.getUid() +  "_" + time_stamp, "Farmer", auth.getUid(), "Requested for new Manager on date : " + new SimpleDateFormat("dd MMM YYYY HH:mm a").format(Long.parseLong(time_stamp) * 1000) + ". Click to check ! ", String.valueOf(Calendar.getInstance().getTime().getTime() / 1000), "manager_request", "false"));
                                        //Log.d("USER EFFECTED Add Plant from farmer Notification :",new Gson().toJson(snapshotUsers.getValue()));

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        reference.child("users").child("farmer").child(auth.getUid()).child("assigned_manager_id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    //Log.d("My Manager ID",snapshot.getValue().toString());

                    reference.child("users").child("manager").child(snapshot.getKey().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                Picasso.get().load(snapshot.child("img_url").getValue().toString()).into(imageView_managerProfile);

                                textView_managerName.setText(snapshot.child("username").getValue().toString());
                                textView_managerMob.setText(snapshot.child("mob_no").getValue().toString());
                                textView_managerVillage.setText("Village : "+snapshot.child("address").child("village").getValue().toString());
                                textView_managerCircle.setText("Circle : "+snapshot.child("address").child("circle").getValue().toString());
                                textView_managerName.setText("Taluka : "+snapshot.child("address").child("taluka").getValue().toString());
                                textView_managerName.setText("District : "+snapshot.child("address").child("dist").getValue().toString());
                                textView_managerName.setText("Pin :"+snapshot.child("address").child("pin").getValue().toString());

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    view_include.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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