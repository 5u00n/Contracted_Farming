package com.ashish.contractedfarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ashish.contractedfarming.Admin.Dashboard.AdminDashboardActivity;
import com.ashish.contractedfarming.AppInfo.AppInfoActivity;
import com.ashish.contractedfarming.Farmer.Dashboard.FarmerDashboardActivity;
import com.ashish.contractedfarming.NewFarmer.AddPlotActivity;
import com.ashish.contractedfarming.NewFarmer.NewFarmerApprovalWaitActivity;
import com.ashish.contractedfarming.NewFarmer.NewFarmerUploadActivity;
import com.ashish.contractedfarming.Helper.Helpers;
import com.ashish.contractedfarming.Login.LoginActivity;
import com.ashish.contractedfarming.Login.SetProfileActivity;
import com.ashish.contractedfarming.Login.UserDetailsActivity;
import com.ashish.contractedfarming.Manager.Dashboard.ManagerDashboardActivity;
import com.ashish.contractedfarming.NewManager.ManagerApprovalWaitActivity;
import com.ashish.contractedfarming.NewManager.NewManagerUploadActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getBaseContext());
        firebaseAuth = FirebaseAuth.getInstance();
         setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        LinearLayout ll= findViewById(R.id.main_linear);
        ll.setVisibility(View.GONE);

        Runnable r = new Runnable() {
            @Override
            public void run(){
               // goToNextActivity(); //<-- put your code in here.
                if(!Helpers.isInternetAvailable())
                    ll.setVisibility(View.VISIBLE);
                else {
                    ll.setVisibility(View.GONE);
                }
            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 2000);



   /*     if(Helpers.isInternetAvailable()) {
            goToNextActivity();
            ll.setVisibility(View.GONE);
        }
        else {
            Toast.makeText(this, "No Internet ", Toast.LENGTH_SHORT).show();

            }
        }

    */


        // ...
    }


    void goToNextActivity() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            final Intent[] intent = new Intent[1];
            String uid = FirebaseAuth.getInstance().getUid().toString();

            databaseReference.child("all-users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()) {
                        if (snapshot.child("usertype").exists() && snapshot.child("approved_num").exists()) {

                            switch (snapshot.child("usertype").getValue().toString()) {
                                case "farmer":
                                    intent[0] = new Intent(MainActivity.this, FarmerDashboardActivity.class);
                                    break;
                                case "rej-farmer":
                                    intent[0] = new Intent(MainActivity.this, NewFarmerApprovalWaitActivity.class);
                                    intent[0].putExtra("status", "rejected");
                                    break;
                                case "new-farmer":
                                    switch (snapshot.child("approved_num").getValue().toString()) {
                                        case "0":
                                            intent[0] = new Intent(MainActivity.this, UserDetailsActivity.class);
                                            break;
                                        case "1":
                                            intent[0] = new Intent(MainActivity.this, NewFarmerUploadActivity.class);
                                            break;
                                        case "2":
                                            intent[0] = new Intent(MainActivity.this, AddPlotActivity.class);
                                            break;
                                        case "3":
                                            intent[0] = new Intent(MainActivity.this, NewFarmerApprovalWaitActivity.class);
                                            intent[0].putExtra("status", "Waiting");
                                            break;
                                    }
                                    break;
                                case "new-manager":
                                    switch (snapshot.child("approved_num").getValue().toString()) {
                                        case "0":
                                            intent[0] = new Intent(MainActivity.this, UserDetailsActivity.class);
                                            break;
                                        case "1":
                                            intent[0] = new Intent(MainActivity.this, NewManagerUploadActivity.class);
                                            break;
                                        case "2":
                                            intent[0] = new Intent(MainActivity.this, ManagerApprovalWaitActivity.class);
                                            intent[0].putExtra("status", "Waiting");
                                            break;
                                    }

                                    break;
                                case "manager":
                                    intent[0] = new Intent(MainActivity.this, ManagerDashboardActivity.class);
                                    break;
                                case "rej-manager":
                                    intent[0] = new Intent(MainActivity.this, ManagerApprovalWaitActivity.class);
                                    intent[0].putExtra("status", "rejected");
                                    break;
                                case "admin":
                                    intent[0] = new Intent(MainActivity.this, AdminDashboardActivity.class);
                                    break;

                            }

                        } else {
                            intent[0] = new Intent(MainActivity.this, SetProfileActivity.class);
                        }
                        if (intent[0] != null) {
                            intent[0].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent[0]);
                            finish();
                        }
                    }else{
                        Log.d("NO Data","NOO Data................................");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
            Log.d("NO USERID","NOO USERID................................");
            SharedPreferences sharedPreferences = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
            boolean isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true);

            if (isFirstLaunch) {
                isFirstLaunch = false;
                sharedPreferences.edit().putBoolean("isFirstLaunch", isFirstLaunch).apply();

                startActivity(new Intent(this, AppInfoActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        goToNextActivity();
    }
}
