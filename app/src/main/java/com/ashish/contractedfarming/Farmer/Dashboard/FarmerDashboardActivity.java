package com.ashish.contractedfarming.Farmer.Dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.contractedfarming.Admin.Dashboard.DashboardAdapter;
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

import com.ashish.contractedfarming.MainActivity;
import com.ashish.contractedfarming.Models.PlotModel;
import com.ashish.contractedfarming.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FarmerDashboardActivity extends AppCompatActivity{
    RecyclerView farmerstoryRV, explorePlantsRv, myPlantsRv, myFarmRv;
    Context context;
    ImageButton newsbtn;


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

    AppBarLayout appBarLayout;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    ImageView profile_img;
    TextView profile_name;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);


        toolbar =findViewById(R.id.farmer_dash_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        context = FarmerDashboardActivity.this;

        auth= FirebaseAuth.getInstance();
        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        database= FirebaseDatabase.getInstance();
        databaseReference= database.getReference();

        tabLayout= findViewById(R.id.farmer_tabs_layout);
        viewPager= findViewById(R.id.farmer_view_pager);


        tabLayout.addTab(tabLayout.newTab().setText("Dashboard"));
        tabLayout.addTab(tabLayout.newTab().setText("Posts"));
        tabLayout.addTab(tabLayout.newTab().setText("All Plants"));
        tabLayout.addTab(tabLayout.newTab().setText("My Farm"));
        tabLayout.addTab(tabLayout.newTab().setText("My Plants"));
        tabLayout.addTab(tabLayout.newTab().setText("My Requests"));

        profile_img=findViewById(R.id.dash_farmer_profile);
        profile_name= findViewById(R.id.dash_farmer_name);

       for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup)tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 50, 0);

            tab.requestLayout();
        }




        newsbtn = findViewById(R.id.farmer_news_tab);





        newsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FarmerDashboardActivity.this, FarmerNewsActivity.class);
                startActivity(intent);
            }
        });


        //sets up weather
       // run();

        //getGPS();
        //sets up story

       // initStory();
        //sets up Explore plants

       // initExplorePlants();
        //initMyPlants();
      //  initMyFarm();

        FarmerDashboardAdapter adapter = new FarmerDashboardAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.getCurrentItem();


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        databaseReference.child("users").child("farmer").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profile_name.setText(snapshot.child("username").getValue().toString());
                Picasso.get().load(snapshot.child("img_url").getValue().toString()).into(profile_img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_profile :
            // do something
            Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show();
            break;

            case R.id.menu_logout:
                auth.signOut();
                startActivity(new Intent(FarmerDashboardActivity.this,MainActivity.class));
                finish();
        }

        return super.onOptionsItemSelected(item);
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