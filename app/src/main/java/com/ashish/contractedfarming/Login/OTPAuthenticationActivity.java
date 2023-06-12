package com.ashish.contractedfarming.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.contractedfarming.Admin.Dashboard.AdminDashboardActivity;
import com.ashish.contractedfarming.Farmer.Dashboard.FarmerDashboardActivity;
import com.ashish.contractedfarming.Farmer.NewFarmer.AddPlotActivity;
import com.ashish.contractedfarming.Farmer.NewFarmer.NewFarmerApprovalWaitActivity;
import com.ashish.contractedfarming.Farmer.NewFarmer.NewFarmerUploadActivity;
import com.ashish.contractedfarming.MainActivity;
import com.ashish.contractedfarming.Manager.NewManager.ManagerApprovalWaitActivity;
import com.ashish.contractedfarming.Manager.NewManager.NewManagerUploadActivity;
import com.ashish.contractedfarming.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OTPAuthenticationActivity extends AppCompatActivity {
    TextView mchangenumber;
    EditText mgetotp;
    android.widget.Button mverifyotp;
    String enteredotp;

    FirebaseAuth firebaseAuth;
    ProgressBar mprogressbarofotpauth;
    Context context;

    FirebaseDatabase database ;
    DatabaseReference databaseReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpauthentication);


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        context= getBaseContext();

        mchangenumber = findViewById(R.id.changenumber);
        mverifyotp = findViewById(R.id.verifyotp);
        mgetotp = findViewById(R.id.getotp);
        mprogressbarofotpauth = findViewById(R.id.progressbarofotpauth);

        firebaseAuth = FirebaseAuth.getInstance();

        mchangenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OTPAuthenticationActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        });

        mverifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredotp = mgetotp.getText().toString();
                if (enteredotp.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your OTP First ", Toast.LENGTH_SHORT).show();
                } else {
                    mprogressbarofotpauth.setVisibility(View.VISIBLE);
                    String coderecieved = getIntent().getStringExtra("otp");
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(coderecieved, enteredotp);
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });


    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mprogressbarofotpauth.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                    goToNextActivity();

                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        mprogressbarofotpauth.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

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
                                  intent[0] = new Intent(context, FarmerDashboardActivity.class);
                                break;
                            case "rej-farmer":
                                 // intent[0] = new Intent(context, FarmerApprovalWaitActivity.class);
                                 // intent[0].putExtra("status", "rejected");
                                break;
                            case "new-farmer":
                                switch (snapshot.child("approved_num").getValue().toString()) {
                                    case "0":
                                        intent[0] = new Intent(context, UserDetailsActivity.class);
                                        break;
                                    case "1":
                                        intent[0] = new Intent(context, NewFarmerUploadActivity.class);
                                        break;
                                    case "2":
                                        intent[0] = new Intent(context, AddPlotActivity.class);
                                        break;
                                    case "3":
                                        intent[0] = new Intent(context, NewFarmerApprovalWaitActivity.class);
                                        intent[0].putExtra("status", "Waiting");
                                        break;
                                }
                                break;
                            case "new-manager":
                                switch (snapshot.child("approved_num").getValue().toString()) {
                                    case "0":
                                        intent[0] = new Intent(context, UserDetailsActivity.class);
                                        break;
                                    case "1":
                                        intent[0] = new Intent(context, NewManagerUploadActivity.class);
                                        break;
                                    case "2":
                                        intent[0] = new Intent(context, ManagerApprovalWaitActivity.class);
                                        intent[0].putExtra("status", "Waiting");
                                        break;
                         }
                        break;
                        case "manager":
                            //intent[0] = new Intent(context, AgentDashboardActivity.class);
                            break;
                            case "rej-manager":
                                // intent[0] = new Intent(context, AgentApprovalWaitActivity.class);
                                //intent[0].putExtra("status", "rejected");
                                break;
                            case "admin":
                                intent[0] = new Intent(context, AdminDashboardActivity.class);
                                break;

                        }

                    } else {
                        intent[0] = new Intent(context, SetProfileActivity.class);
                    }

                    if (intent[0] != null) {
                        intent[0].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent[0]);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
        else{
            Intent intent = new Intent(OTPAuthenticationActivity.this, SetProfileActivity.class);
            startActivity(intent);
            finish();
        }


    }
}