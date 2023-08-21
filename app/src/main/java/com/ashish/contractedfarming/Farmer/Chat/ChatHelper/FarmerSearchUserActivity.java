package com.ashish.contractedfarming.Farmer.Chat.ChatHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashish.contractedfarming.Farmer.News.FarmerNewsActivity;
import com.ashish.contractedfarming.Farmer.News.FarmerNewsAdapter;
import com.ashish.contractedfarming.Models.ChatUsersModel;
import com.ashish.contractedfarming.Models.PlantModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FarmerSearchUserActivity extends AppCompatActivity {

    ImageButton back_button;
    TextView no_of_contacts;
    LinearLayout textLayout;
    SearchView searchView;

    RecyclerView farmerRecycler, managerRecycler, adminRecycler;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    Context context;


    ArrayList<ChatUsersModel> adminArrayList, farmerArrayList, managerArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_search_user);


        context = getBaseContext();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("all-users");

        database.getReference().child("all-users").child(auth.getUid()).child("online_status").setValue("online");
        searchView = findViewById(R.id.farmer_search_user_search_button);

        initNavbar();
        initRecycle();

    }

    public void initNavbar() {


        back_button = findViewById(R.id.farmer_search_user_back_image_button);
        no_of_contacts = findViewById(R.id.farmer_search_user_no_of_users_text);

        textLayout = findViewById(R.id.farmer_search_user_text_layout);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_button.setVisibility(View.GONE);
                textLayout.setVisibility(View.GONE);
                searchView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                back_button.setVisibility(View.VISIBLE);
                textLayout.setVisibility(View.VISIBLE);
                searchView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                return false;
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(0);
                finish();
            }
        });
    }

    public void initRecycle() {
        farmerRecycler = findViewById(R.id.farmer_search_user_farmers_recycler);
        managerRecycler = findViewById(R.id.farmer_search_user_managers_recycler);
        adminRecycler = findViewById(R.id.farmer_search_user_admins_recycler);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    farmerArrayList = new ArrayList<>();
                    managerArrayList = new ArrayList<>();
                    adminArrayList = new ArrayList<>();

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if(!ds.child("userUID").getValue().toString().equals(auth.getUid())) {

                            if (ds.child("usertype").exists()) {
                                if (ds.child("usertype").getValue().toString().contains("farmer")) {
                                    farmerArrayList.add(new ChatUsersModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("img_url").getValue().toString(), ds.child("online_status").getValue().toString()));
                                }
                                if (ds.child("usertype").getValue().toString().equals("manager")) {
                                    managerArrayList.add(new ChatUsersModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("img_url").getValue().toString(), ds.child("online_status").getValue().toString()));
                                }
                                if (ds.child("usertype").getValue().toString().contains("admin")) {
                                    adminArrayList.add(new ChatUsersModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("img_url").getValue().toString(), ds.child("online_status").getValue().toString()));
                                }
                            }
                        }
                    }

                    ChatUserAdapter farmerAdapter = new ChatUserAdapter(context, farmerArrayList);


                    farmerAdapter.setOnItemClickListener(new ChatUserAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClicked(String itemId) {
                            setResult(2006, new Intent().putExtra("receiver_id", itemId));
                            //Log.d("FROM SELECT USER ACTIVITY farmer ",itemId);
                            finish();
                        }
                    });
                    ChatUserAdapter managerAdapter = new ChatUserAdapter(context, managerArrayList);

                    managerAdapter.setOnItemClickListener(new ChatUserAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClicked(String itemId) {
                            setResult(2006, new Intent().putExtra("receiver_id", itemId));
                            //Log.d("FROM SELECT USER ACTIVITY manager ",itemId);
                            finish();
                        }
                    });
                    ChatUserAdapter adminAdapter = new ChatUserAdapter(context, adminArrayList);

                    adminAdapter.setOnItemClickListener(new ChatUserAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClicked(String itemId) {
                            setResult(2006, new Intent().putExtra("receiver_id", itemId));
                            finish();
                        }
                    });

                    farmerRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    managerRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    adminRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }



                        @Override
                        public boolean onQueryTextChange(String newText) {

                            ArrayList<ChatUsersModel> filteredlistFramer = new ArrayList<>();
                            ArrayList<ChatUsersModel> filteredlistManager = new ArrayList<>();
                            ArrayList<ChatUsersModel> filteredlistAdmin = new ArrayList<>();

                            // running a for loop to compare elements.
                            for (ChatUsersModel item :farmerArrayList ) {
                                // checking if the entered string matched with any item of our recycler view.
                                if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                                    // if the item is matched we are
                                    // adding it to our filtered list.
                                    filteredlistFramer.add(item);
                                }
                            }
                                for (ChatUsersModel item :managerArrayList ) {
                                    // checking if the entered string matched with any item of our recycler view.
                                    if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                                        // if the item is matched we are
                                        // adding it to our filtered list.
                                        filteredlistManager.add(item);
                                    }
                                }
                                    for (ChatUsersModel item :adminArrayList ) {
                                        // checking if the entered string matched with any item of our recycler view.
                                        if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                                            // if the item is matched we are
                                            // adding it to our filtered list.
                                            filteredlistAdmin.add(item);
                                        }
                            }
                            if (!filteredlistFramer.isEmpty()) {
                                farmerAdapter.filterList(filteredlistFramer);
                            }
                            if (!filteredlistManager.isEmpty()) {
                                managerAdapter.filterList(filteredlistManager);
                            }
                            if (!filteredlistAdmin.isEmpty()) {
                                adminAdapter.filterList(filteredlistAdmin);
                            }


                            return false;
                        }
                    });

                    farmerRecycler.setAdapter(farmerAdapter);
                    managerRecycler.setAdapter(managerAdapter);
                    adminRecycler.setAdapter(adminAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(0);
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