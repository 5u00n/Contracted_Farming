package com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.PlantAddition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ashish.contractedfarming.Models.PlotModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FarmerSelectFarmActivity extends AppCompatActivity {



    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    Button add_button;
    ImageButton back_button;
    ListView listView;

    String plant_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_select_farm);

        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference= database.getReference();

        plant_id= getIntent().getExtras().getString("plant_id");
        add_button= findViewById(R.id.farmer_select_farm_popup_select_button_next);
        back_button= findViewById(R.id.farmer_select_farm_popup_back_button);

        listView= findViewById(R.id.farmer_select_farm_popup_listView);

        ArrayList<PlotModel> plotModels = new ArrayList<>();
        reference.child("users").child("farmer").child(auth.getUid()).child("plot").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snap) {
                if(snap.exists()){
                    for(DataSnapshot snapshot:snap.getChildren() )
                    if(snapshot.child("approval").exists() ) {
                        plotModels.add(new PlotModel(snapshot.child("plot_img_url").getValue().toString(), snapshot.child("plotID").getValue().toString(), snapshot.child("plotName").getValue().toString(), snapshot.child("area").getValue().toString(), snapshot.child("village").getValue().toString(), snapshot.child("approval").getValue().toString()));
                    }else{
                        plotModels.add(new PlotModel(snapshot.child("plot_img_url").getValue().toString(), snapshot.child("plotID").getValue().toString(), snapshot.child("plotName").getValue().toString(), snapshot.child("area").getValue().toString(), snapshot.child("village").getValue().toString(), "null"));

                    }

                    listView.setAdapter(new FarmerSelectFarmListViewAdapter(FarmerSelectFarmActivity.this,plotModels));
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(FarmerSelectFarmActivity.this, i, Toast.LENGTH_SHORT).show();
                FarmerSelectFarmListViewAdapter adapter= (FarmerSelectFarmListViewAdapter) adapterView.getAdapter();
                CheckBox ck=view.findViewById(R.id.view_select_farm_listview_checkbox);
                if(ck.isChecked()){
                    ck.setChecked(false);
                }else {
                    ck.setChecked(true);
                }
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FarmerSelectFarmActivity.this,FarmerExplorePlantsActivity.class).putExtra("plant_id",plant_id));
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FarmerSelectFarmActivity.this,FarmerExplorePlantsActivity.class).putExtra("plant_id",plant_id));
        finish();
    }
}