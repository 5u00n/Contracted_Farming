package com.ashish.contractedfarming.Farmer.ConferenceAndWorkShop;

import android.os.Bundle;
import android.widget.ListView;


import com.ashish.contractedfarming.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class FarmerCandWActivity extends AppCompatActivity {
    ListView lv;

   // int img[] = {R.drawable.conferance3, R.drawable.conferance1, R.drawable.conferance2, R.drawable.conferance3, R.drawable.conferance4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_confandwork);
        lv = findViewById(R.id.farmer_conferance_list);
        ArrayList<FarmerCandWModel> list = new ArrayList<>();


        if(list!=null) {
            FarmerCandWAdapter adapter = new FarmerCandWAdapter(getBaseContext(), list);
            lv.setAdapter(adapter);
        }
    }
}