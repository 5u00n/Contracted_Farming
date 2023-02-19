package com.ashish.contractedfarming.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.contractedfarming.Farmer.NewFarmer.NewFarmerUploadActivity;
import com.ashish.contractedfarming.Manager.NewManager.NewManagerUploadActivity;
import com.ashish.contractedfarming.Models.AddressModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserDetailsActivity extends AppCompatActivity {

    String i_name="",imgurl="",usertype="",userUID;

    ImageView imageView;
    TextView name,utype;
    EditText mob,adhar,pan,dist,taluka,vill,cir,pin;
    DatabaseReference allrefer;

    Button next;

    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseAuth firebaseAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        database= FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        userUID=firebaseAuth.getUid();

        allrefer= database.getReference("all-users").child(userUID);

        Intent intent=getIntent();

        i_name=intent.getStringExtra("name");
        imgurl=intent.getStringExtra("img_url");
        usertype=intent.getStringExtra("usertype");




        //Get data from database
        imageView = findViewById(R.id.user_pofile_title_img);
        name= findViewById(R.id.user_details_name);


        //upload data to database
        utype= findViewById(R.id.user_details_utype);
        mob= findViewById(R.id.user_details_mob);
        adhar= findViewById(R.id.user_details_adhar);
        pan= findViewById(R.id.user_details_pan);
        dist= findViewById(R.id.user_details_dist);
        taluka= findViewById(R.id.user_details_taluka);
        vill= findViewById(R.id.user_details_vill);
        cir= findViewById(R.id.user_details_cir);
        pin= findViewById(R.id.user_details_pin);

        utype.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(utype.getText().toString()!=null)
                    reference=database.getReference("users").child(utype.getText().toString()).child(FirebaseAuth.getInstance().getUid());
            }
        });

        next= findViewById(R.id.user_details_next);


        //get data from intent
        if(imgurl!=null) {
            Picasso.get().load(imgurl).into(imageView);
        }
        name.setText("Welcome "+i_name);;
        utype.setText(usertype);




        //put data to database
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((mob.getText().toString().isEmpty() || adhar.getText().toString().isEmpty()) || (pan.getText().toString().isEmpty()||dist.getText().toString().isEmpty())||(taluka.getText().toString().isEmpty()|| vill.getText().toString().isEmpty())|| (cir.getText().toString().isEmpty()||pin.getText().toString().isEmpty())){
                    Toast.makeText(UserDetailsActivity.this, "ALL Fields are Compulsory", Toast.LENGTH_SHORT).show();
                }else {
                    reference.child("mob_no").setValue(mob.getText().toString());
                    reference.child("verification").child("aadhaar_no").setValue(adhar.getText().toString());
                    reference.child("verification").child("pan_no").setValue(pan.getText().toString());

                    reference.child("address").setValue(new AddressModel(vill.getText().toString(),cir.getText().toString(),taluka.getText().toString(),dist.getText().toString(),"Maharastra","India",pin.getText().toString()));

                    allrefer.child("approved_num").setValue("1");


                    if(utype.getText().toString().equals("new-manager")){
                        startActivity(new Intent(UserDetailsActivity.this, NewManagerUploadActivity.class));
                        finish();
                    }
                    if(utype.getText().toString().equals("new-farmer")) {
                        startActivity(new Intent(UserDetailsActivity.this, NewFarmerUploadActivity.class));
                        finish();
                    }
                }
            }
        });


        allrefer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    i_name = snapshot.child("username").getValue().toString();
                    imgurl = snapshot.child("img_url").getValue().toString();
                    usertype = snapshot.child("usertype").getValue().toString();

                    if (imgurl != null) {
                        Picasso.get().load(imgurl).into(imageView);
                    }
                    name.setText(getString(R.string.welcome)+" "+i_name);
                    utype.setText(usertype);

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}