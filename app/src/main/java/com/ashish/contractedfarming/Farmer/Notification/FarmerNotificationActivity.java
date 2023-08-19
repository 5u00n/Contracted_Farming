package com.ashish.contractedfarming.Farmer.Notification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.contractedfarming.Admin.Dashboard.News.AdminNewsModel;
import com.ashish.contractedfarming.Farmer.Chat.FarmerChatActivity;
import com.ashish.contractedfarming.Farmer.ConferenceAndWorkShop.FarmerCandWActivity;
import com.ashish.contractedfarming.Farmer.Dashboard.FarmerDashboardActivity;
import com.ashish.contractedfarming.Farmer.News.FarmerNewsActivity;
import com.ashish.contractedfarming.Farmer.News.FarmerNewsAdapter;
import com.ashish.contractedfarming.MainActivity;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FarmerNotificationActivity extends AppCompatActivity {

    Context context;

    FirebaseAuth auth;

    ImageButton home, newsTabButton, confTabButton, chatTabButton, notificationTabButton, currentTab;
    ListView lv;
    Toolbar toolbar;
    String name[] = {"Tomato", "parsley", "bokchoy"};
    String text[] = {"Important...", "Task Panding...", "Water time"};
    // int img[] = {R.drawable.profilicon, R.drawable.profilicon, R.drawable.profilicon};

    String f_name, f_img_src,f_location;

    RecyclerView rv;
    ImageView profile_img;
    TextView profile_name,p_location;

    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<FarmerNotificationModel> list ;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_notification);

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

        //  lv = findViewById(R.id.farmer_notigication_list);
        //= new ArrayList<>();


        context = getBaseContext();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        rv = findViewById(R.id.farmer_notification_rv);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    DataSnapshot notification_snaps=snapshot.child("users").child("farmer").child(auth.getUid()).child("notifications");
                    //DataSnapshot sender_snaps= snapshot.child("all-users").child()
                    list= new ArrayList<>();
                    for (DataSnapshot ds : notification_snaps.getChildren()) {
                        list.add(new FarmerNotificationModel(ds.child("not_id").getValue().toString(),snapshot.child("all-users").child(ds.child("creator").getValue().toString()).child("usertype").getValue().toString(),snapshot.child("all-users").child(ds.child("creator").getValue().toString()).child("username").getValue().toString(), ds.child("message").getValue().toString(), ds.child("date_created").getValue().toString(), ds.child("type").getValue().toString(),ds.child("seen").getValue().toString()));
                    }

                    Collections.sort(list, new Comparator<FarmerNotificationModel>() {
                        @Override
                        public int compare(FarmerNotificationModel o1, FarmerNotificationModel o2) {
                            return o1.getDate_created().compareTo(o2.getDate_created());
                        }
                    });


                    FarmerNotificationAdapter adapter = new FarmerNotificationAdapter(FarmerNotificationActivity.this, list);



                    adapter.setOnItemClickListener(new FarmerNotificationAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClicked(String not_id,String notification_type) {
                            reference.child("users").child("farmer").child(auth.getUid()).child("notifications").child(not_id).child("seen").setValue("true");
                            switch (notification_type) {
                                case "farms":
                                    gotoHome("farms");
                                    break;
                                case "farmer_plants":
                                    gotoHome("farmer_plants");
                                    break;
                                case "plants":
                                    gotoHome("plants");
                                    break;
                                case "posts":
                                    gotoHome();
                                    break;
                                case "conference":
                                    gotoConf();
                                    break;
                                case "news":
                                    gotoNews();
                                    break;
                            }
                        }
                    });
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);


                    rv.setLayoutManager(layoutManager);
                    rv.setAdapter(adapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        home = findViewById(R.id.farmer_home_tab);
        newsTabButton = findViewById(R.id.farmer_news_tab);
        confTabButton = findViewById(R.id.farmer_conference_tab);
        chatTabButton = findViewById(R.id.farmer_message_tab);
        notificationTabButton = findViewById(R.id.farmer_notification_icon);

        currentTab = notificationTabButton;

        currentTab.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));

        home.setOnClickListener(view -> {
            gotoHome();
        });

        newsTabButton.setOnClickListener(view -> {
            gotoNews();
        });

        confTabButton.setOnClickListener(view -> {
            gotoConf();
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
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        notificationTabButton.setOnClickListener(view -> {
            //currentTab.setBackgroundColor(Color.TRANSPARENT);
            //notificationTabButton.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
            //Intent intent = new Intent(context, FarmerNotificationActivity.class);
            //startActivity(intent);
            //finish();
            //overridePendingTransition( R.anim.slide_in_right,R.anim.slide_out_left);

        });


    }

    public void gotoHome(){
        currentTab.setBackgroundColor(Color.TRANSPARENT);
        home.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
        Intent intent = new Intent(context, FarmerDashboardActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void gotoHome(String intText){
        currentTab.setBackgroundColor(Color.TRANSPARENT);
        home.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
        Intent intent = new Intent(context, FarmerDashboardActivity.class);
        intent.putExtra("tab_open",intText);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void gotoConf(){
        currentTab.setBackgroundColor(Color.TRANSPARENT);
        confTabButton.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
        Intent intent = new Intent(context, FarmerCandWActivity.class);
        intent.putExtra("f_name",f_name);
        intent.putExtra("f_img_src",f_img_src);
        intent.putExtra("f_location",f_location);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void gotoNews(){
        currentTab.setBackgroundColor(Color.TRANSPARENT);
        newsTabButton.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.shape_rectangle));
        Intent intent = new Intent(context, FarmerNewsActivity.class);
        intent.putExtra("f_name",f_name);
        intent.putExtra("f_img_src",f_img_src);
        intent.putExtra("f_location",f_location);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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


