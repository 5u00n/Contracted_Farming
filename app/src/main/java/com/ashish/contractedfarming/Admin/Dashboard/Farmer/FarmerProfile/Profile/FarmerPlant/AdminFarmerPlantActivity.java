package com.ashish.contractedfarming.Admin.Dashboard.Farmer.FarmerProfile.Profile.FarmerPlant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class AdminFarmerPlantActivity extends AppCompatActivity {


    Context context;

    String farmer_plant_id;


    ImageButton backButton;

    ImageView imageViewFarmer, imageViewPlant,imageViewFarm,imageViewManager,imageViewVerification;

    TextView textViewFarmer,textViewPlant, textViewFarm,textViewManger,textViewAdminApproval,textViewManagerApproval,textViewDateAdded,textViewFinalApproval,textViewFarmerCoordinate, textViewManagerCoordinate;

    TextView farmerPlantId,farmerId,managerId,plantId, farmId;
    Button buttonFarmer, buttonManager, buttonPlant, buttonFarm, buttonAdminApproval,buttonManagerApproval;

    CardView cardViewVerification;


    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_farmer_plant);

        context=getBaseContext();

        farmer_plant_id=getIntent().getExtras().getString("farmer_plant_id");

        Log.d("farmer plant verification ",farmer_plant_id);


        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

        initElements();
        clickFunctions();
        getDataFromFirebase();
    }

    void initElements(){
        backButton= findViewById(R.id.admin_farmer_plant_back_button);

        farmerPlantId= findViewById(R.id.admin_farmer_plant_id);


        imageViewFarmer= findViewById(R.id.admin_farmer_plant_farmer_image);
        textViewFarmer= findViewById(R.id.admin_farmer_plant_farmer_name);
        farmerId= findViewById(R.id.admin_farmer_plant_farmer_id);
        buttonFarmer= findViewById(R.id.admin_farmer_plant_farmer_button);


        imageViewPlant= findViewById(R.id.admin_farmer_plant_plant_image);
        textViewPlant= findViewById(R.id.admin_farmer_plant_plant_name);
        plantId= findViewById(R.id.admin_farmer_plant_plant_id);
        buttonPlant= findViewById(R.id.admin_farmer_plant_plant_button);


        imageViewFarm= findViewById(R.id.admin_farmer_plant_farm_image);
        textViewFarm= findViewById(R.id.admin_farmer_plant_farm_text);
        farmId= findViewById(R.id.admin_farmer_plant_farm_id);
        buttonFarm= findViewById(R.id.admin_farmer_plant_farm_button);


        imageViewManager= findViewById(R.id.admin_farmer_plant_manager_image);
        textViewManger= findViewById(R.id.admin_farmer_plant_manager_name);
        managerId= findViewById(R.id.admin_farmer_plant_manager_id);
        buttonManager= findViewById(R.id.admin_farmer_plant_manager_button);


        textViewAdminApproval= findViewById(R.id.admin_farmer_plant_admin_approval_status);
        buttonAdminApproval= findViewById(R.id.admin_farmer_plant_admin_approval_button);
        textViewManagerApproval= findViewById(R.id.admin_farmer_plant_manager_approval_status);
        buttonManagerApproval= findViewById(R.id.admin_farmer_plant_manager_request_button);


        textViewDateAdded= findViewById(R.id.admin_farmer_plant_added_date);
        textViewFinalApproval= findViewById(R.id.admin_farmer_plant_final_approval);


        cardViewVerification= findViewById(R.id.admin_farmer_plant_verification_card);

        imageViewVerification= findViewById(R.id.admin_farmer_plant_verification_image);
        textViewFarmerCoordinate= findViewById(R.id.admin_farmer_plant_verification_loc_farmer);
        textViewManagerCoordinate= findViewById(R.id.admin_farmer_plant_verification_loc_manager);



    }


    void clickFunctions(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        /*------------------ Farmer actions --**/

        imageViewFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        textViewFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    void getDataFromFirebase(){
        reference.child("farmer_plants").child(farmer_plant_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    //Log.d("Farmer Acceptance : ", new Gson().toJson(snapshot.getValue()));

                    //Farmer Details
                    Picasso.get().load(snapshot.child("farmer_img_url").getValue().toString()).into(imageViewFarmer);
                    textViewFarmer.setText(snapshot.child("farmer_name").getValue().toString());
                    farmerId.setText(snapshot.child("farmer_id").getValue().toString());

                    Picasso.get().load(snapshot.child("plant_img_url").getValue().toString()).into(imageViewPlant);
                    textViewPlant.setText(snapshot.child("plant_name").getValue().toString());
                    plantId.setText(snapshot.child("plant_id").getValue().toString());

                    Picasso.get().load(snapshot.child("farm_img_url").getValue().toString()).into(imageViewFarm);
                    textViewFarm.setText(snapshot.child("farm_name").getValue().toString());
                    farmId.setText(snapshot.child("plot_id").getValue().toString());


                    Picasso.get().load(snapshot.child("manager_img_url").getValue().toString()).into(imageViewManager);
                    textViewManger.setText(snapshot.child("manager_name").getValue().toString());
                    managerId.setText(snapshot.child("manager_id").getValue().toString());


                    textViewAdminApproval.setText(snapshot.child("approval_admin").getValue().toString());
                    textViewManagerApproval.setText(snapshot.child("approval_manager").getValue().toString());

                    textViewDateAdded.setText(snapshot.child("date_added").getValue().toString());
                    textViewFinalApproval.setText("By __manager on dd mm yyy");//snapshot.child("final_approval").getValue().toString());

                    if(snapshot.child("manager_loc").exists()) {
                        cardViewVerification.setVisibility(View.VISIBLE);
                        Picasso.get().load(snapshot.child("verification_img").getValue().toString()).into(imageViewVerification);
                        textViewManagerCoordinate.setText(snapshot.child("manager_loc").getValue().toString());
                        textViewFarmerCoordinate.setText(snapshot.child("farmer_loc").getValue().toString());
                    }else {
                        cardViewVerification.setVisibility(View.GONE);
                    }


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