package com.ashish.contractedfarming.Admin.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.contractedfarming.Farmer.Chat.ChatHelper.ChatUserAdapter;
import com.ashish.contractedfarming.Farmer.Chat.ChatHelper.FarmerSpecificChatActivity;
import com.ashish.contractedfarming.Farmer.Chat.FarmerChatActivity;
import com.ashish.contractedfarming.Models.ChatUsersModel;
import com.ashish.contractedfarming.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminChatListFragment extends Fragment {



    RecyclerView recyclerView;
    AppBarLayout appBarLayout;
    Toolbar toolbar;

    FloatingActionButton floatingActionButton;

    Context context;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    ArrayList<ChatUsersModel> chatUsersModelArrayList;
    public AdminChatListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_admin_chat_list, container, false);

        recyclerView= view.findViewById(R.id.admin_chat_recyclerview);
        context =getContext();

        database= FirebaseDatabase.getInstance();
        reference=database.getReference();
        auth=FirebaseAuth.getInstance();


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
                            startActivity(new Intent(context, FarmerSpecificChatActivity.class).putExtra("receiver_id",itemId));
                        }
                    });
                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

                    recyclerView.setAdapter(chatsAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2006 && resultCode==2006){
            String receiver_id=data.getStringExtra("receiver_id");
            startActivity(new Intent(context, FarmerSpecificChatActivity.class).putExtra("receiver_id",receiver_id));
        }
    }
}