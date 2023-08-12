package com.ashish.contractedfarming.Farmer.Dashboard;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ashish.contractedfarming.Farmer.Chat.FarmerChatActivity;
import com.ashish.contractedfarming.Farmer.ConferenceAndWorkShop.FarmerCandWActivity;
import com.ashish.contractedfarming.Farmer.Dashboard.Weather.HttpRequest;
import com.ashish.contractedfarming.Farmer.News.FarmerNewsActivity;
import com.ashish.contractedfarming.Farmer.Notification.FarmerNotificationActivity;
import com.ashish.contractedfarming.MainActivity;
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

public class FarmerDashboardActivity extends AppCompatActivity {


    private static final int ADD_STORY_IMG = 2;
    FarmerDashboardAdapter adapter;
    Context context;
    ImageButton home, newsTabButton, confTabButton, chatTabButton, notificationTabButton, currentTab;


    //Firebase Variable
    FirebaseAuth auth;
    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database;
    DatabaseReference databaseReference;





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


        toolbar = findViewById(R.id.farmer_dash_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        context = FarmerDashboardActivity.this;

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        tabLayout = findViewById(R.id.farmer_tabs_layout);
        viewPager = findViewById(R.id.farmer_view_pager);


        tabLayout.addTab(tabLayout.newTab().setText("Dashboard"));
        tabLayout.addTab(tabLayout.newTab().setText("Posts"));
        tabLayout.addTab(tabLayout.newTab().setText("All Plants"));
        tabLayout.addTab(tabLayout.newTab().setText("My Farm"));
        tabLayout.addTab(tabLayout.newTab().setText("My Plants"));
        //tabLayout.addTab(tabLayout.newTab().setText("My Requests"));

        profile_img = findViewById(R.id.dash_farmer_profile);
        profile_name = findViewById(R.id.dash_farmer_name);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 50, 0);

            tab.requestLayout();
        }


        context = getBaseContext();


        home = findViewById(R.id.farmer_home_tab);
        newsTabButton = findViewById(R.id.farmer_news_tab);
        confTabButton = findViewById(R.id.farmer_conference_tab);
        chatTabButton = findViewById(R.id.farmer_message_tab);
        notificationTabButton = findViewById(R.id.farmer_notification_icon);

        currentTab = home;

        currentTab.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));

        home.setOnClickListener(view -> {
            //currentTab.setBackgroundColor(Color.TRANSPARENT);
            //home.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            // Intent intent = new Intent(context,FarmerDashboardActivity.class);
            //startActivity(intent);
            //finish();
            //overridePendingTransition( R.anim.slide_out_left,R.anim.slide_in_right);
        });

        newsTabButton.setOnClickListener(view -> {
            currentTab.setBackgroundColor(Color.TRANSPARENT);
            newsTabButton.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            Intent intent = new Intent(context, FarmerNewsActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        confTabButton.setOnClickListener(view -> {
            currentTab.setBackgroundColor(Color.TRANSPARENT);
            confTabButton.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            Intent intent = new Intent(context, FarmerCandWActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        chatTabButton.setOnClickListener(view -> {
            currentTab.setBackgroundColor(Color.TRANSPARENT);
            chatTabButton.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            Intent intent = new Intent(context, FarmerChatActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        notificationTabButton.setOnClickListener(view -> {
            currentTab.setBackgroundColor(Color.TRANSPARENT);
            notificationTabButton.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            Intent intent = new Intent(context, FarmerNotificationActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

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

        adapter = new FarmerDashboardAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
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



    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // In fragment class callback

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_STORY_IMG  && resultCode == RESULT_OK) {
           // sendImagetoStorage("_7_12",data.getData());
            story_img.setImageURI(data.getData());
        }
    }
    */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = adapter.getItem(viewPager.getCurrentItem());
        fragment.onActivityResult(requestCode, resultCode, data);
        //Log.d("Image data  ", String.valueOf(data));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_profile:
                // do something
                Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_logout:
                auth.signOut();
                startActivity(new Intent(FarmerDashboardActivity.this, MainActivity.class));
                finish();
        }

        return super.onOptionsItemSelected(item);
    }


}