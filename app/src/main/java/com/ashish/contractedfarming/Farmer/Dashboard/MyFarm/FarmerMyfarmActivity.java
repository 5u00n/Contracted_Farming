package com.ashish.contractedfarming.Farmer.Dashboard.MyFarm;

import android.content.Context;
import android.os.Bundle;


import com.ashish.contractedfarming.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FarmerMyfarmActivity extends AppCompatActivity {

    RecyclerView farmeraddfarmyRV;
    Context context;

    ArrayList<FarmerMyfarmModel> list;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_myfarm);


        context=this.getBaseContext();

        farmeraddfarmyRV = findViewById(R.id.farmer_myfarm_rv);
       list = new ArrayList<>();

        list.add(new FarmerMyfarmModel("1","11","1","kharadi","pune","pune","maha","55","10","tree"));
        list.add(new FarmerMyfarmModel("1","11","1","kharadi","pune","pune","maha","55","10","tree"));


        /*   farmerstoryRV = findViewById(R.id.farmerstoryRV);
        farmerstoryList = new ArrayList<>();
        farmerstoryList.add(new FarmerStoryModel("Ashish","","https://www.google.com/imgres?imgurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Fthumb%2Fmsid-97672613%2Cwidth-1200%2Cheight-900%2Cresizemode-4%2F97672613.jpg&imgrefurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Findia%2Fbreaking-news-live-updates-7-2-2023%2Fliveblog%2F97672613.cms&tbnid=KEbAPKnwf0U_zM&vet=12ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ..i&docid=KTKj333gdYGDjM&w=1200&h=900&q=news&ved=2ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ"));
        farmerstoryList.add(new FarmerStoryModel("Ashish","",""));


        FarmerStoryAdapter adapter = new FarmerStoryAdapter(farmerstoryList,context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true);
        farmerstoryRV.setLayoutManager(layoutManager);
        farmerstoryRV.setNestedScrollingEnabled(false);
        farmerstoryRV.setAdapter(adapter);
         */
      FarmerMyfarmAdapter adapter = new FarmerMyfarmAdapter(list,context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        farmeraddfarmyRV.setLayoutManager(layoutManager);
        farmeraddfarmyRV.setAdapter(adapter);




    }
}