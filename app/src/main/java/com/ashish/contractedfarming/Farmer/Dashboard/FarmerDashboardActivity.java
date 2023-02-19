package com.ashish.contractedfarming.Farmer.Dashboard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.AdminPlantsModel;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.MyFarm.FarmerMyfarmAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.MyFarm.FarmerMyfarmModel;
import com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.FarmerExploreplantsAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.MyPlants.FarmerMyplantsAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.FarmerStoryAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.FarmerStoryModel;
import com.ashish.contractedfarming.Farmer.News.FarmerNewsActivity;
import com.ashish.contractedfarming.R;

import java.util.ArrayList;

public class FarmerDashboardActivity extends AppCompatActivity {
    RecyclerView farmerstoryRV,explorePlantsRv,myPlantsRv,myFarmRv;
    Context context;
    ImageButton newsbtn;

    ArrayList<FarmerStoryModel> farmerstoryList;
    ArrayList<AdminPlantsModel> exploreplantList;
    ArrayList<AdminPlantsModel> myplantList;
    ArrayList<FarmerMyfarmModel> myfarmList;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        context=this.getBaseContext();

        farmerstoryRV = findViewById(R.id.farmerstoryRV);
        explorePlantsRv= findViewById(R.id.farmer_exploreplantsrv);
        myPlantsRv= findViewById(R.id.farmer_myplantsrv);
        myFarmRv= findViewById(R.id.farmer_myfarmRv);
        newsbtn= findViewById(R.id.farmer_news_tab);


        newsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FarmerDashboardActivity.this, FarmerNewsActivity.class);
                startActivity(intent);
            }
        });



        farmerstoryList = new ArrayList<>();
        farmerstoryList.add(new FarmerStoryModel("Ashish","","https://www.google.com/imgres?imgurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Fthumb%2Fmsid-97672613%2Cwidth-1200%2Cheight-900%2Cresizemode-4%2F97672613.jpg&imgrefurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Findia%2Fbreaking-news-live-updates-7-2-2023%2Fliveblog%2F97672613.cms&tbnid=KEbAPKnwf0U_zM&vet=12ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ..i&docid=KTKj333gdYGDjM&w=1200&h=900&q=news&ved=2ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ"));
        farmerstoryList.add(new FarmerStoryModel("Ashish","",""));
        farmerstoryList.add(new FarmerStoryModel("Ashish","",""));
        farmerstoryList.add(new FarmerStoryModel("Ashish","",""));
        farmerstoryList.add(new FarmerStoryModel("Ashish","",""));


        FarmerStoryAdapter adapter1 = new FarmerStoryAdapter(farmerstoryList,context);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true);
        farmerstoryRV.setLayoutManager(layoutManager1);
        farmerstoryRV.setNestedScrollingEnabled(false);
        farmerstoryRV.setAdapter(adapter1);




        ArrayList<AdminPlantsModel> exploreplantList = new ArrayList<>();


        exploreplantList.add(new AdminPlantsModel("1","Potato","https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));
        exploreplantList.add(new AdminPlantsModel("2","Tomato","https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));

        FarmerExploreplantsAdapter adapter2 = new FarmerExploreplantsAdapter(exploreplantList,this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);

        explorePlantsRv.setLayoutManager(layoutManager2);
        explorePlantsRv.setNestedScrollingEnabled(false);
        explorePlantsRv.setAdapter(adapter2);



        ArrayList<AdminPlantsModel> myplantList = new ArrayList<>();

        myplantList.add(new AdminPlantsModel("1","","https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));
        myplantList.add(new AdminPlantsModel("1","ppp","https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));
        myplantList.add(new AdminPlantsModel("1","ppp","https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));


        FarmerMyplantsAdapter adapter3 = new FarmerMyplantsAdapter(myplantList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);

        myPlantsRv.setLayoutManager(layoutManager);
        myPlantsRv.setNestedScrollingEnabled(false);
        myPlantsRv.setAdapter(adapter3);


        myfarmList = new ArrayList<>();

        myfarmList.add(new FarmerMyfarmModel("1","11","1","kharadi","pune","pune","maha","55","10","tree"));
        myfarmList.add(new FarmerMyfarmModel("1","11","1","pune","pune","pune","maha","55","10","tree"));

        FarmerMyfarmAdapter adapter4 = new FarmerMyfarmAdapter(myfarmList,context);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        myFarmRv.setLayoutManager(layoutManager4);
        myFarmRv.setAdapter(adapter4);






       /* ImageView farmerNews;

        ImageView farmerCandW;

        ImageView farmerNtificatio;
        ImageView farmerAccount;
        ImageView helpandDignosis;
        ImageView myfarm;
        ImageView myplants;
        ImageView cropProtection; */

        /*      //  farmerNotificatio= (ImageView)findViewById(R.id.farmernotificationicon);
        farmerNews = (ImageView)findViewById(R.id.newspic);
        farmerCandW = (ImageView)findViewById(R.id.candw);
        farmerAccount = (ImageView)findViewById(R.id.farmeraccount);
        helpandDignosis = (ImageView)findViewById(R.id.helpanddignosis);
        myfarm = (ImageView)findViewById(R.id.myfarm);
        myplants = (ImageView)findViewById(R.id.myplants);
      //  cropProtection = (ImageView)findViewById(R.id.cropprotection);*/



       /* farmerNotificatio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FarmerDashboardActivity.this,FarmerNotificationActivity.class);
                startActivity(i);
            }
        });*/
/*
        farmerNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FarmerDashboardActivity.this,FarmerNewsActivity.class);
                startActivity(i);
            }
        });


        farmerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FarmerDashboardActivity.this,FarmerAccountActivity.class);
                startActivity(i);
            }
        });

        myfarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FarmerDashboardActivity.this, FarmerMyplantsActivity.class);
                startActivity(i);
            }
        });



        farmerCandW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FarmerDashboardActivity.this,FarmerCandWActivity.class);
                startActivity(i);
            }
        });


        myplants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FarmerDashboardActivity.this,FarmerMyplantsActivity.class);
                startActivity(i);
            }
        });*/

    }
}