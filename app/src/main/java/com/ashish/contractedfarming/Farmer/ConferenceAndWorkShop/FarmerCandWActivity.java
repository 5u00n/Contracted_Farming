package com.ashish.contractedfarming.Farmer.ConferenceAndWorkShop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.ashish.contractedfarming.Admin.Dashboard.News.AdminNewsModel;
import com.ashish.contractedfarming.Farmer.Chat.FarmerChatActivity;
import com.ashish.contractedfarming.Farmer.Dashboard.FarmerDashboardActivity;
import com.ashish.contractedfarming.Farmer.News.FarmerNewsActivity;
import com.ashish.contractedfarming.Farmer.News.FarmerNewsAdapter;
import com.ashish.contractedfarming.Farmer.Notification.FarmerNotificationActivity;
import com.ashish.contractedfarming.MainActivity;
import com.ashish.contractedfarming.Models.ConferenceModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FarmerCandWActivity extends AppCompatActivity {
    ListView lv;
    Context context;



    ImageButton home,newsTabButton,confTabButton,chatTabButton,notificationTabButton,currentTab;

    Toolbar toolbar;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;


    String f_name, f_img_src,f_location;
    ImageView profile_img;
    TextView profile_name,p_location;


    RecyclerView recyclerViewToday,recyclerViewUpcoming,recyclerViewPast;
    ArrayList<ConferenceModel> listToday,listUpcoming,listPast ;

    Calendar dateToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_confandwork);



        f_name=getIntent().getExtras().getString("f_name");
        f_img_src=getIntent().getExtras().getString("f_img_src");
        f_location=getIntent().getExtras().getString("f_location");

        profile_img = findViewById(R.id.dash_farmer_profile);
        profile_name = findViewById(R.id.dash_farmer_name);
        p_location=findViewById(R.id.dash_farmer_location);

        Picasso.get().load(f_img_src).into(profile_img);
        profile_name.setText(f_name);
        p_location.setText(f_location);

        auth= FirebaseAuth.getInstance();

        toolbar =findViewById(R.id.farmer_dash_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("conf-workshop");


        //lv = findViewById(R.id.farmer_conferance_list);



        context = getBaseContext();

        dateToday=Calendar.getInstance();

        recyclerViewToday = findViewById(R.id.farmer_conf_rv_current);
        recyclerViewUpcoming = findViewById(R.id.farmer_conf_rv_future);
        recyclerViewPast = findViewById(R.id.farmer_conf_rv_over);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listToday = new ArrayList<>();
                listUpcoming = new ArrayList<>();
                listPast = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //Log.d("Date comp",(new SimpleDateFormat("dd MMM yyyy").format(Long.parseLong(ds.child("conf_date").getValue().toString()) * 1000L))+ "    " +(new SimpleDateFormat("dd MMM yyyy").format(dateToday.getTime())));
                    if ((new SimpleDateFormat("dd MMM yyyy").format(Long.parseLong(ds.child("conf_date").getValue().toString()) * 1000L)).equals(new SimpleDateFormat("dd MMM yyyy").format(dateToday.getTime()))) {

                        listToday.add(new ConferenceModel(ds.child("conf_id").getValue().toString(), ds.child("conf_topic").getValue().toString(), ds.child("by_name").getValue().toString(), ds.child("conf_date").getValue().toString(), ds.child("conf_venue").getValue().toString(), ds.child("conf_img_url").getValue().toString(), ds.child("created_date").getValue().toString()));
                    } else if ((new Date(Long.parseLong(ds.child("conf_date").getValue().toString()) * 1000L)).before(dateToday.getTime())) {
                        listPast.add(new ConferenceModel(ds.child("conf_id").getValue().toString(), ds.child("conf_topic").getValue().toString(), ds.child("by_name").getValue().toString(), ds.child("conf_date").getValue().toString(), ds.child("conf_venue").getValue().toString(), ds.child("conf_img_url").getValue().toString(), ds.child("created_date").getValue().toString()));
                    } else {
                        listUpcoming.add(new ConferenceModel(ds.child("conf_id").getValue().toString(), ds.child("conf_topic").getValue().toString(), ds.child("by_name").getValue().toString(), ds.child("conf_date").getValue().toString(), ds.child("conf_venue").getValue().toString(), ds.child("conf_img_url").getValue().toString(), ds.child("created_date").getValue().toString()));
                    }
                }


                Collections.sort(listToday, new Comparator<ConferenceModel>() {
                    @Override
                    public int compare(ConferenceModel o1, ConferenceModel o2) {
                        return o1.getConf_date().compareTo(o2.getConf_date());
                    }
                });

                Collections.sort(listUpcoming, new Comparator<ConferenceModel>() {
                    @Override
                    public int compare(ConferenceModel o1, ConferenceModel o2) {
                        return o1.getConf_date().compareTo(o2.getConf_date());
                    }
                });

                Collections.sort(listPast, new Comparator<ConferenceModel>() {
                    @Override
                    public int compare(ConferenceModel o1, ConferenceModel o2) {
                        return o1.getConf_date().compareTo(o2.getConf_date());
                    }
                });



                FarmerCandWAdapter adapterToday = new FarmerCandWAdapter(FarmerCandWActivity.this, listToday);
                FarmerCandWAdapter adapterUpcoming = new FarmerCandWAdapter(FarmerCandWActivity.this, listUpcoming);
                FarmerCandWAdapter adapterPast = new FarmerCandWAdapter(FarmerCandWActivity.this, listPast);

                LinearLayoutManager layoutManagerToday = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                LinearLayoutManager layoutManagerUpcoming = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                LinearLayoutManager layoutManagerPast = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);


                recyclerViewToday.setLayoutManager(layoutManagerToday);
                recyclerViewUpcoming.setLayoutManager(layoutManagerUpcoming);
                recyclerViewPast.setLayoutManager(layoutManagerPast);

                recyclerViewToday.setAdapter(adapterToday);
                recyclerViewUpcoming.setAdapter(adapterUpcoming);
                recyclerViewPast.setAdapter(adapterPast);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        home=findViewById(R.id.farmer_home_tab);
        newsTabButton=findViewById(R.id.farmer_news_tab);
        confTabButton=findViewById(R.id.farmer_conference_tab);
        chatTabButton=findViewById(R.id.farmer_message_tab);
        notificationTabButton=findViewById(R.id.farmer_notification_icon);

        currentTab=confTabButton;

        currentTab.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));

        home.setOnClickListener(view -> {
            currentTab.setBackgroundColor(Color.TRANSPARENT);
            home.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            Intent intent = new Intent(context, FarmerDashboardActivity.class);
            intent.putExtra("f_name",f_name);
            intent.putExtra("f_img_src",f_img_src);
            intent.putExtra("f_location",f_location);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        newsTabButton.setOnClickListener(view -> {
            currentTab.setBackgroundColor(Color.TRANSPARENT);
            newsTabButton.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            Intent intent = new Intent(context, FarmerNewsActivity.class);
            intent.putExtra("f_name",f_name);
            intent.putExtra("f_img_src",f_img_src);
            intent.putExtra("f_location",f_location);
            startActivity(intent);
            finish();
            overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);
        });

        confTabButton.setOnClickListener(view -> {
            //currentTab.setBackgroundColor(Color.TRANSPARENT);
            //confTabButton.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            //Intent intent = new Intent(context, FarmerCandWActivity.class);
            //startActivity(intent);
            //finish();
            //overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);
        });

        chatTabButton.setOnClickListener(view -> {
            currentTab.setBackgroundColor(Color.TRANSPARENT);
            chatTabButton.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            Intent intent = new Intent(context, FarmerChatActivity.class);
            intent.putExtra("f_name",f_name);
            intent.putExtra("f_img_src",f_img_src);
            intent.putExtra("f_location",f_location);
            startActivity(intent);
            finish();
            overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);
        });

        notificationTabButton.setOnClickListener(view -> {
            currentTab.setBackgroundColor(Color.TRANSPARENT);
            notificationTabButton.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            Intent intent = new Intent(context, FarmerNotificationActivity.class);
            intent.putExtra("f_name",f_name);
            intent.putExtra("f_img_src",f_img_src);
            intent.putExtra("f_location",f_location);
            startActivity(intent);
            finish();
            overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);

        });






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
                startActivity(new Intent(context, MainActivity.class));
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}