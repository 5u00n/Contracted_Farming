package com.ashish.contractedfarming.Farmer.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.contractedfarming.Farmer.Chat.ChatHelper.ChatUserAdapter;
import com.ashish.contractedfarming.Farmer.Chat.ChatHelper.FarmerSearchUserActivity;
import com.ashish.contractedfarming.Farmer.Chat.ChatHelper.FarmerSpecificChatActivity;
import com.ashish.contractedfarming.Farmer.ConferenceAndWorkShop.FarmerCandWActivity;
import com.ashish.contractedfarming.Farmer.Dashboard.FarmerDashboardActivity;
import com.ashish.contractedfarming.Farmer.News.FarmerNewsActivity;
import com.ashish.contractedfarming.Farmer.Notification.FarmerNotificationActivity;
import com.ashish.contractedfarming.MainActivity;
import com.ashish.contractedfarming.Models.ChatUsersModel;
import com.ashish.contractedfarming.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FarmerChatActivity extends AppCompatActivity {
    Context context;

    Toolbar toolbar;

    ImageButton home, newsTabButton, confTabButton, chatTabButton, notificationTabButton, currentTab;


    FirebaseAuth auth;

    String f_name, f_img_src,f_location;
    ImageView profile_img;
    TextView profile_name,p_location;

    RecyclerView chatRecyclerView;
    FloatingActionButton floatingActionButton;

    FirebaseDatabase database;
    DatabaseReference reference;

    ArrayList<ChatUsersModel> chatUsersModelArrayList;

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

        if(!f_img_src.isEmpty()) {
            Picasso.get().load(f_img_src).into(profile_img);
        }
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

        database= FirebaseDatabase.getInstance();
        reference=database.getReference();

        reference.child("all-users").child(auth.getUid()).child("online_status").setValue("online");

        initBottomNav();
        initAddChats();
        initChats();
    }


    public void initAddChats(){
        floatingActionButton=findViewById(R.id.farmer_chat_floating_add_message);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(context, FarmerSearchUserActivity.class),2006);
            }
        });
    }


    public void initChats(){
        chatRecyclerView= findViewById(R.id.farmer_chat_recycler);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DataSnapshot chatSnapshot= snapshot.child("chats");
                if(chatSnapshot.exists()){
                    chatUsersModelArrayList= new ArrayList<>();
                    for (DataSnapshot ads:chatSnapshot.getChildren()){
                        DataSnapshot ds;
                        if(ads.getKey().contains(auth.getUid()+"_")) {
                            ds=snapshot.child("all-users").child(ads.getKey().replace(auth.getUid()+"_",""));
                            chatUsersModelArrayList.add(new ChatUsersModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("img_url").getValue().toString(), ds.child("online_status").getValue().toString()));
                        }
                    }
                    ChatUserAdapter chatsAdapter = new ChatUserAdapter(context,chatUsersModelArrayList);

                    chatsAdapter.setOnItemClickListener(new ChatUserAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClicked(String itemId) {
                            startActivity(new Intent(FarmerChatActivity.this, FarmerSpecificChatActivity.class).putExtra("receiver_id",itemId));
                        }
                    });
                    chatRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

                    chatRecyclerView.setAdapter(chatsAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2006 && resultCode==2006){
            String receiver_id=data.getStringExtra("receiver_id");
            startActivity(new Intent(FarmerChatActivity.this, FarmerSpecificChatActivity.class).putExtra("receiver_id",receiver_id));
        }
    }

    public void initBottomNav(){
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
        getMenuInflater().inflate(R.menu.menu_chat, menu);
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

            case R.id.menu_search:
                startActivityForResult(new Intent(context, FarmerSearchUserActivity.class),2006);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseDatabase.getInstance().getReference("all-users").child(FirebaseAuth.getInstance().getUid()).child("online_status").setValue("offline");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference("all-users").child(FirebaseAuth.getInstance().getUid()).child("online_status").setValue("online");
    }
}