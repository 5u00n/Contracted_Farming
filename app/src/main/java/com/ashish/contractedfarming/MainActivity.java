package com.ashish.contractedfarming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.ashish.contractedfarming.Admin.Dashboard.AdminDashboardActivity;
import com.ashish.contractedfarming.AppInfo.AppInfoActivity;
import com.ashish.contractedfarming.Login.LoginActivity;
import com.ashish.contractedfarming.Login.SetProfileActivity;
import com.ashish.contractedfarming.Login.UserDetailsActivity;
import com.ashish.contractedfarming.Manager.NewManager.NewManagerUploadActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database ;
    DatabaseReference databaseReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();


        goToNextActivity();
        // ...
    }


    void goToNextActivity() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            final Intent[] intent = new Intent[1];
            String uid = FirebaseAuth.getInstance().getUid().toString();

            Log.d("Logig Page uid", uid);
            databaseReference.child("all-users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists() && (snapshot.child("usertype").exists() && snapshot.child("approved_num").exists())) {
                        switch (snapshot.child("usertype").getValue().toString()) {
                            case "farmer":
                               // intent[0] = new Intent(MainActivity.this, FarmerDashboardActivity.class);
                                break;
                            case "rej-farmer":
                                //intent[0] = new Intent(MainActivity.this, FarmerApprovalWaitActivity.class);
                              //  intent[0].putExtra("status", "rejected");
                                break;
                            case "new-farmer":
                                switch (snapshot.child("approved_num").getValue().toString()) {
                                    case "0":
                                       // intent[0] = new Intent(MainActivity.this, UserDetailsActivity.class);
                                        break;
                                    case "1":
                                       // intent[0] = new Intent(MainActivity.this, FarmerUploadActivity.class);
                                        break;
                                    case "2":
                                        //intent[0] = new Intent(MainActivity.this, FarmerApprovalWaitActivity.class);
                                       // intent[0].putExtra("status", "Waiting");
                                        break;
                                }
                                break;
                            case "new-agent":
                                switch (snapshot.child("approved_num").getValue().toString()) {
                                    case "0":
                                        intent[0] = new Intent(MainActivity.this, UserDetailsActivity.class);
                                        break;
                                    case "1":
                                        intent[0] = new Intent(MainActivity.this, NewManagerUploadActivity.class);
                                        break;
                                    case "2":
                                        intent[0] = new Intent(MainActivity.this, NewManagerUploadActivity.class);
                                        intent[0].putExtra("status", "Waiting");
                                        break;
                                }

                                break;
                            case "agent":
                                //intent[0] = new Intent(MainActivity.this, AgentDashboardActivity.class);
                                break;
                            case "rej-agent":
                               // intent[0] = new Intent(MainActivity.this, AgentApprovalWaitActivity.class);
                                //intent[0].putExtra("status", "rejected");
                                break;
                            case "admin":
                                intent[0] = new Intent(MainActivity.this, AdminDashboardActivity.class);
                                break;

                        }

                    } else {
                        intent[0] = new Intent(MainActivity.this, SetProfileActivity.class);
                    }

                    intent[0].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent[0]);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
            boolean isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true);

            if (isFirstLaunch) {
                isFirstLaunch = false;
                sharedPreferences.edit().putBoolean("isFirstLaunch", isFirstLaunch).apply();

                startActivity(new Intent(this, AppInfoActivity.class));
                finish();
            }else{
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        }
    }
}
