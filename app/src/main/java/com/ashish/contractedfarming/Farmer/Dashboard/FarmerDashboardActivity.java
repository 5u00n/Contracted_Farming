package com.ashish.contractedfarming.Farmer.Dashboard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.AdminPlantsModel;
import com.ashish.contractedfarming.Farmer.Dashboard.MyFarm.FarmerMyfarmAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.MyFarm.FarmerMyfarmModel;
import com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.FarmerExploreplantsAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.MyPlants.FarmerMyplantsAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.FarmerStoryAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.FarmerStoryModel;
import com.ashish.contractedfarming.Farmer.Dashboard.Weather.HttpRequest;
import com.ashish.contractedfarming.Farmer.NewFarmer.AddPlotActivity;
import com.ashish.contractedfarming.Farmer.News.FarmerNewsActivity;

import com.ashish.contractedfarming.Models.PlotModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FarmerDashboardActivity extends AppCompatActivity {
    RecyclerView farmerstoryRV, explorePlantsRv, myPlantsRv, myFarmRv;
    Context context;
    ImageButton newsbtn;

    ArrayList<FarmerStoryModel> farmerstoryList;
    ArrayList<AdminPlantsModel> exploreplantList;
    ArrayList<AdminPlantsModel> myplantList;
    ArrayList<FarmerMyfarmModel> myfarmList;

    LocationManager locationManager;
    String latitude, longitude;

    private static final int REQUEST_LOCATION = 1;
    private static final int ADD_STORY_IMG=2;



    //Firebase Variable
    FirebaseAuth auth;
    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database;
    DatabaseReference databaseReference;


    //Weather
    TextView  temp,humidity,windSpeed,rainfall;

    //Story Section
    ConstraintLayout constraintLayout;

    ImageView story_img;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        context = this.getBaseContext();

        auth= FirebaseAuth.getInstance();
        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        database= FirebaseDatabase.getInstance();
        databaseReference= database.getReference();

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


        //sets up weather
        run();

        getGPS();
        //sets up story

        initStory();
        //sets up Explore plants

        initExplorePlants();
        initMyPlants();
        initMyFarm();

    }


    public void initStory(){


        constraintLayout= findViewById(R.id.addStory_layout);

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(FarmerDashboardActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.prompt_add_story);

                EditText story_disc;
                Button addStory,cancel;
                story_disc= dialog.findViewById(R.id.prompt_add_story_disc);
                story_img= dialog.findViewById(R.id.prompt_add_story_img);





                story_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, ADD_STORY_IMG);
                    }
                });


                addStory= dialog.findViewById(R.id.prompt_add_story_button_post);
                cancel= dialog.findViewById(R.id.prompt_add_story_button_cancel);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                addStory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String time_stamp=String.valueOf((System.currentTimeMillis() / 1000));
                        if(story_disc.getText().toString().isEmpty() ) {

                        }else{

                        }

                            dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });

        farmerstoryList = new ArrayList<>();
        farmerstoryList.add(new FarmerStoryModel("Ashish", "BB", "https://www.google.com/imgres?imgurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Fthumb%2Fmsid-97672613%2Cwidth-1200%2Cheight-900%2Cresizemode-4%2F97672613.jpg&imgrefurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Findia%2Fbreaking-news-live-updates-7-2-2023%2Fliveblog%2F97672613.cms&tbnid=KEbAPKnwf0U_zM&vet=12ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ..i&docid=KTKj333gdYGDjM&w=1200&h=900&q=news&ved=2ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ","hELLO",""));
        farmerstoryList.add(new FarmerStoryModel("Ashish", "BB", "https://www.google.com/imgres?imgurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Fthumb%2Fmsid-97672613%2Cwidth-1200%2Cheight-900%2Cresizemode-4%2F97672613.jpg&imgrefurl=https%3A%2F%2Ftimesofindia.indiatimes.com%2Findia%2Fbreaking-news-live-updates-7-2-2023%2Fliveblog%2F97672613.cms&tbnid=KEbAPKnwf0U_zM&vet=12ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ..i&docid=KTKj333gdYGDjM&w=1200&h=900&q=news&ved=2ahUKEwjjhsuC_5v9AhWHIrcAHRgeAskQMygGegUIARCzAQ","Found Business",""));


        FarmerStoryAdapter adapter1 = new FarmerStoryAdapter(farmerstoryList, context);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        farmerstoryRV.setLayoutManager(layoutManager1);
        farmerstoryRV.setNestedScrollingEnabled(false);
        farmerstoryRV.setAdapter(adapter1);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // In fragment class callback

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_STORY_IMG  && resultCode == RESULT_OK) {
           // sendImagetoStorage("_7_12",data.getData());
            story_img.setImageURI(data.getData());
        }
    }

    public void initExplorePlants(){



        ArrayList<AdminPlantsModel> exploreplantList = new ArrayList<>();


        exploreplantList.add(new AdminPlantsModel("1", "Potato", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));
        exploreplantList.add(new AdminPlantsModel("2", "Tomato", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));

        FarmerExploreplantsAdapter adapter2 = new FarmerExploreplantsAdapter(exploreplantList, this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        explorePlantsRv.setLayoutManager(layoutManager2);
        explorePlantsRv.setNestedScrollingEnabled(false);
        explorePlantsRv.setAdapter(adapter2);

    }


    public void initMyPlants(){
        ArrayList<AdminPlantsModel> myplantList = new ArrayList<>();

        myplantList.add(new AdminPlantsModel("1", "", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));
        myplantList.add(new AdminPlantsModel("1", "ppp", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));
        myplantList.add(new AdminPlantsModel("1", "ppp", "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));


        FarmerMyplantsAdapter adapter3 = new FarmerMyplantsAdapter(myplantList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        myPlantsRv.setLayoutManager(layoutManager);
        myPlantsRv.setNestedScrollingEnabled(false);
        myPlantsRv.setAdapter(adapter3);
    }

    public void initMyFarm(){
        myfarmList = new ArrayList<>();

        myfarmList.add(new FarmerMyfarmModel("1", "11", "1", "kharadi", "pune", "pune", "maha", "55", "10", "tree"));
        myfarmList.add(new FarmerMyfarmModel("1", "11", "1", "pune", "pune", "pune", "maha", "55", "10", "tree"));

        FarmerMyfarmAdapter adapter4 = new FarmerMyfarmAdapter(myfarmList, context);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        myFarmRv.setLayoutManager(layoutManager4);
        myFarmRv.setAdapter(adapter4);
    }



    public void getGPS(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                getLocation();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                run();
                Log.d("Your Location: ", "Latitude: " + latitude + " " + "Longitude: " + longitude);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void run() {
        //CITY1 = CITY.getText().toString();
        new weatherTask().execute();
    }

    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String args[]) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?lat="+latitude + "&lon=" +longitude+ "&appid=73cbebdd0322acd49bda6ede059b2b18");
            return response;
        }

        @Override
        protected void onPostExecute(String result) {

            temp= findViewById(R.id.weather_data_temprature);
            humidity=findViewById(R.id.weather_data_hunidity);
            windSpeed= findViewById(R.id.weather_data_wind_speed);
            rainfall= findViewById(R.id.weather_data_rainfall);

            try {
                JSONObject jsonObj = new JSONObject(result);

                Log.d("weather data",jsonObj.toString());
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                temp.setText(String.format("%.02f",(Float.parseFloat(main.getString("temp"))- 273.15) )+ "°C");
                humidity.setText(main.getString("humidity")+"%");
                windSpeed.setText(wind.getString("speed")+" m/s");
                rainfall.setText("NAN");


            } catch (JSONException e) {
                Log.d("Weather Error",e.toString());
            }

        }
    }
}