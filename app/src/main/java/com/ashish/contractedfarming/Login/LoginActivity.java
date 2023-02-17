package com.ashish.contractedfarming.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ashish.contractedfarming.Anonymous.AnonymousActivity;
import com.ashish.contractedfarming.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {


    RelativeLayout rlogin;
    LinearLayout loption;
    EditText mgetphonenumber;
    Button viewApp, login,msendotp;
    ImageButton hidelogin;
    CountryCodePicker mcountrycodepicker;
    String countrycode;
    String phonenumber;

    FirebaseAuth firebaseAuth;
    ProgressBar mprogressbarofmain;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String codesent;
    FirebaseDatabase database ;
    DatabaseReference databaseReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        firebaseAuth= FirebaseAuth.getInstance();


        viewApp= findViewById(R.id.login_gotojustview);
        login= findViewById(R.id.login_gotologin);
        msendotp= findViewById(R.id.login_sendotpbutton);
        hidelogin= findViewById(R.id.login_hidelogin);

        mcountrycodepicker = findViewById(R.id.login_countrycodepicker);
        mgetphonenumber = findViewById(R.id.login_phone_no);
        mprogressbarofmain = findViewById(R.id.login_progressbar);

        rlogin=findViewById(R.id.login_loginlayout);
        loption=findViewById(R.id.login_optionlayout);


        viewApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, AnonymousActivity.class));

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loption.setVisibility(View.GONE);
                rlogin.setVisibility(View.VISIBLE);
            }
        });

        hidelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlogin.setVisibility(View.GONE);
                loption.setVisibility(View.VISIBLE);
            }
        });

        countrycode = mcountrycodepicker.getSelectedCountryCodeWithPlus();
        mcountrycodepicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countrycode = mcountrycodepicker.getSelectedCountryCodeWithPlus();
            }
        });

        msendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number;
                number = mgetphonenumber.getText().toString();
                if (number.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter YOur number", Toast.LENGTH_SHORT).show();
                } else if (number.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Please Enter correct number", Toast.LENGTH_SHORT).show();
                } else {

                    mprogressbarofmain.setVisibility(View.VISIBLE);
                    phonenumber = countrycode + number;

                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(phonenumber)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(LoginActivity.this)
                            .setCallbacks(mCallbacks)
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }


            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                //how to automatically fetch code here
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(getApplicationContext(), "OTP is Sent", Toast.LENGTH_SHORT).show();
                mprogressbarofmain.setVisibility(View.INVISIBLE);
                codesent = s;
                Intent intent = new Intent(LoginActivity.this, OTPAuthenticationActivity.class);
                intent.putExtra("otp", codesent);
                startActivity(intent);
            }
        };

    }
}
