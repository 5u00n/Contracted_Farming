package com.ashish.contractedfarming.Farmer.Dashboard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.ashish.contractedfarming.Farmer.Dashboard.Weather.HttpRequest;
import com.ashish.contractedfarming.Farmer.News.FarmerNewsActivity;
import com.ashish.contractedfarming.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FarmerDashboardActivity extends AppCompatActivity {
    RecyclerView farmerstoryRV, explorePlantsRv, myPlantsRv, myFarmRv;
    Context context;
    ImageButton newsbtn;

    ArrayList<FarmerStoryModel> farmerstoryList;
    ArrayList<AdminPlantsModel> exploreplantList;
    ArrayList<AdminPlantsModel> myplantList;
    ArrayList<FarmerMyfarmModel> myfarmList;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        context = this.getBaseContext();

        farmerstoryRV = findViewById(R.id.farmerstoryRV);
        explorePlantsRv = findViewById(R.id.farmer_exploreplantsrv);
        myPlantsRv = findViewById(R.id.farmer_myplantsrv);
        myFarmRv = findViewById(R.id.farmer_myfarmRv);
        newsbtn = findViewById(R.id.farmer_news_tab);


        newsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FarmerDashboardActivity.this, FarmerNewsActivity.class);
                startActivity(intent);
            }
        });


        farmerstoryList = new ArrayList<>();
        farmerstoryList.add(new FarmerStoryModel("Ashish", "", "https://www.google.com/imgres?imgurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Fthumb%2Fmsid-97672613%2Cwidth-1200%2Cheight-900%2Cresizemode-4%2F97672613.jpg&imgrefurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Findia%2Fbreaking-news-live-updates-7-2-2023%2Fliveblog%2F97672613.cms&tbnid=KEbAPKnwf0U_zM&vet=12ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ..i&docid=KTKj333gdYGDjM&w=1200&h=900&q=news&ved=2ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ"));
        farmerstoryList.add(new FarmerStoryModel("Ashish", "", ""));
        farmerstoryList.add(new FarmerStoryModel("Ashish", "", ""));
        farmerstoryList.add(new FarmerStoryModel("Ashish", "", ""));
        farmerstoryList.add(new FarmerStoryModel("Ashish", "", ""));


        FarmerStoryAdapter adapter1 = new FarmerStoryAdapter(farmerstoryList, context);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        farmerstoryRV.setLayoutManager(layoutManager1);
        farmerstoryRV.setNestedScrollingEnabled(false);
        farmerstoryRV.setAdapter(adapter1);


        ArrayList<AdminPlantsModel> exploreplantList = new ArrayList<>();


        exploreplantList.add(new AdminPlantsModel("1", "Potato", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));
        exploreplantList.add(new AdminPlantsModel("2", "Tomato", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));

        FarmerExploreplantsAdapter adapter2 = new FarmerExploreplantsAdapter(exploreplantList, this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        explorePlantsRv.setLayoutManager(layoutManager2);
        explorePlantsRv.setNestedScrollingEnabled(false);
        explorePlantsRv.setAdapter(adapter2);


        ArrayList<AdminPlantsModel> myplantList = new ArrayList<>();

        myplantList.add(new AdminPlantsModel("1", "", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));
        myplantList.add(new AdminPlantsModel("1", "ppp", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));
        myplantList.add(new AdminPlantsModel("1", "ppp", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));


        FarmerMyplantsAdapter adapter3 = new FarmerMyplantsAdapter(myplantList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        myPlantsRv.setLayoutManager(layoutManager);
        myPlantsRv.setNestedScrollingEnabled(false);
        myPlantsRv.setAdapter(adapter3);


        myfarmList = new ArrayList<>();

        myfarmList.add(new FarmerMyfarmModel("1", "11", "1", "kharadi", "pune", "pune", "maha", "55", "10", "tree"));
        myfarmList.add(new FarmerMyfarmModel("1", "11", "1", "pune", "pune", "pune", "maha", "55", "10", "tree"));

        FarmerMyfarmAdapter adapter4 = new FarmerMyfarmAdapter(myfarmList, context);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        myFarmRv.setLayoutManager(layoutManager4);
        myFarmRv.setAdapter(adapter4);


    }


    public void run(View view) {
        //CITY1 = CITY.getText().toString();
        new weatherTask().execute();
    }

    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String args[]) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?lat=" + "&lon=" + "&appid=73cbebdd0322acd49bda6ede059b2b18");
            return response;
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");


                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");
                // addressT.setText(address);
                // updated_atT.setText(updatedAtText);
                // statusT.setText(weatherDescription.toUpperCase());
                //tempT.setText(temp);
                //temp_minTxt.setText(tempMin);
                // temp_maxT.setText(tempMax);
                // sunriseT.setText (new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                //  sunsetT.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                //  windT.setText(windSpeed);
                //  pressureT.setText(pressure);
                // //   humidityT.setText(humidity);
                //   findViewById(R.id.loader).setVisibility(View.GONE);
                //  findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);
                //  findViewById(R.id.presd).setVisibility(View.VISIBLE);
                //  findViewById(R.id.sund).setVisibility(View.VISIBLE);
                // findViewById(R.id.sunsd).setVisibility(View.VISIBLE);
                //  findViewById(R.id.wnd).setVisibility(View.VISIBLE);
                //   findViewById(R.id.humd).setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                //  findViewById(R.id.loader).setVisibility(View.GONE);
                //  findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }

        }
    }
}