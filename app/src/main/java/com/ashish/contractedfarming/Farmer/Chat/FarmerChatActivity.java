package com.ashish.contractedfarming.Farmer.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.contractedfarming.Farmer.ConferenceAndWorkShop.FarmerCandWActivity;
import com.ashish.contractedfarming.Farmer.Dashboard.FarmerDashboardActivity;
import com.ashish.contractedfarming.Farmer.News.FarmerNewsActivity;
import com.ashish.contractedfarming.Farmer.Notification.FarmerNotificationActivity;
import com.ashish.contractedfarming.MainActivity;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class FarmerChatActivity extends AppCompatActivity {
    Context context;

    Toolbar toolbar;

    ImageButton home, newsTabButton, confTabButton, chatTabButton, notificationTabButton, currentTab;


    FirebaseAuth auth;

    String f_name, f_img_src,f_location;
    ImageView profile_img;
    TextView profile_name,p_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_chat);



        f_name=getIntent().getExtras().getString("f_name");
        f_img_src=getIntent().getExtras().getString("f_img_src");
        f_location=getIntent().getExtras().getString("f_location");

        profile_img = findViewById(R.id.dash_farmer_profile);
        profile_name = findViewById(R.id.dash_farmer_name);
        p_location=findViewById(R.id.dash_farmer_location);

        Picasso.get().load(f_img_src).into(profile_img);
        profile_name.setText(f_name);
        p_location.setText(f_location);

        auth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.farmer_dash_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");


        context = getBaseContext();


        home = findViewById(R.id.farmer_home_tab);
        newsTabButton = findViewById(R.id.farmer_news_tab);
        confTabButton = findViewById(R.id.farmer_conference_tab);
        chatTabButton = findViewById(R.id.farmer_message_tab);
        notificationTabButton = findViewById(R.id.farmer_notification_icon);

        currentTab = chatTabButton;

        currentTab.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));

        home.setOnClickListener(view -> {
            currentTab.setBackgroundColor(Color.TRANSPARENT);
            home.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            Intent intent = new Intent(context, FarmerDashboardActivity.class);
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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        confTabButton.setOnClickListener(view -> {
            currentTab.setBackgroundColor(Color.TRANSPARENT);
            confTabButton.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            Intent intent = new Intent(context, FarmerCandWActivity.class);
            intent.putExtra("f_name",f_name);
            intent.putExtra("f_img_src",f_img_src);
            intent.putExtra("f_location",f_location);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        chatTabButton.setOnClickListener(view -> {
            //currentTab.setBackgroundColor(Color.TRANSPARENT);
            //chatTabButton.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            //Intent intent = new Intent(context, FarmerChatActivity.class);
            //startActivity(intent);
            //finish();
            //overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);
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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

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
        switch (id) {
            case R.id.menu_profile:
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