package com.ashish.contractedfarming.Farmer.Chat.ChatHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.contractedfarming.Admin.Chat.AdminSpecificChat.AdminSpecificChatActivity;
import com.ashish.contractedfarming.Admin.Chat.AdminSpecificChat.Messages;
import com.ashish.contractedfarming.Admin.Chat.AdminSpecificChat.MessagesAdapter;
import com.ashish.contractedfarming.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class FarmerSpecificChatActivity extends AppCompatActivity {

    EditText get_message;
    ImageButton send_message_button;

    CardView send_message_cardview;
    androidx.appcompat.widget.Toolbar toolbar_of_specific_chat;
    ImageView specific_user_image;
    TextView specific_username;

    private String entered_message;
    Intent intent;
    String receiver_uid, sender_uid;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String sender_room, receiver_room;

    ImageButton back_button_specific_chat;

    RecyclerView messager_recyclerview;

    String current_time;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    MessagesAdapter messagesAdapter;
    ArrayList<Messages> messagesArrayList;
    ArrayList<Messages> message;


    //Switch encdec;

    Context context;


    void setMessage(ArrayList<Messages> msg) {
        this.message = msg;
    }

    public ArrayList<Messages> getMessage() {
        return message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_specific_chat);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        context = getBaseContext();


        get_message = findViewById(R.id.getmessage);
       // encdec = findViewById(R.id.switch1);
        send_message_cardview = findViewById(R.id.carviewofsendmessage);
        send_message_button = findViewById(R.id.imageviewsendmessage);
        toolbar_of_specific_chat = findViewById(R.id.toolbarofspecificchat);
        specific_username = findViewById(R.id.Nameofspecificuser);
        specific_user_image = findViewById(R.id.specificuserimageinimageview);
        back_button_specific_chat = findViewById(R.id.backbuttonofspecificchat);

        messagesArrayList = new ArrayList<>();
        messager_recyclerview = findViewById(R.id.recyclerviewofspecific);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messager_recyclerview.setLayoutManager(linearLayoutManager);


        intent = getIntent();

        setSupportActionBar(toolbar_of_specific_chat);

        toolbar_of_specific_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Toolbar is Clicked", Toast.LENGTH_SHORT).show();


            }
        });




        sender_uid = firebaseAuth.getUid();
        receiver_uid = getIntent().getStringExtra("receiver_id");

        //Log.d("FROM CHAT ACTIVITY",receiver_uid);


        sender_room = sender_uid +"_"+ receiver_uid;
        receiver_room = receiver_uid +"_"+ sender_uid;


        DatabaseReference databaseReference = firebaseDatabase.getReference( );
        DatabaseReference chatReference= databaseReference.child("chats").child(sender_room).child("messages");

        messagesAdapter = new MessagesAdapter(context, messagesArrayList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();

                DataSnapshot usersSnapshot= snapshot.child("all-users").child(receiver_uid);
                specific_username.setText(usersSnapshot.child("username").getValue().toString());
                if(!usersSnapshot.child("img_url").exists() || usersSnapshot.child("img_url").getValue().toString().equals("") || usersSnapshot.child("img_url").getValue().toString().equals(" ")) {
                    specific_user_image.setImageResource(R.drawable.logo);
                }else {
                    Picasso.get().load(usersSnapshot.child("img_url").getValue().toString()).into(specific_user_image);              //------------------------------------Add inside ReferenceListener
                }

                DataSnapshot chatsSnapshots=snapshot.child("chats").child(sender_room).child("messages");
                if(chatsSnapshots.hasChildren()) {
                    for (DataSnapshot snapshot1 : chatsSnapshots.getChildren()) {
                        Messages messages = snapshot1.getValue(Messages.class);
                        messagesArrayList.add(messages);
                    }
                    setMessage(messagesArrayList);

                    messagesAdapter = new MessagesAdapter(context, messagesArrayList);
                    messager_recyclerview.setAdapter(messagesAdapter);
                    messagesAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        back_button_specific_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });








        send_message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                entered_message = get_message.getText().toString();
                if (entered_message.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter message first", Toast.LENGTH_SHORT).show();
                } else {
                    Date date = new Date();
                    current_time = simpleDateFormat.format(calendar.getTime());
                    Messages messages = new Messages(entered_message, firebaseAuth.getUid(), date.getTime(), current_time);
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child("chats").child(sender_room).child("messages").push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    firebaseDatabase.getReference().child("chats").child(receiver_room).child("messages").push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    // --------------------------------------------------------------------------------- ADD CODE THAT WILL UPDATE THE UI SAYING THE MESSAGE HAS BEEN SENT
                                                }
                                            });
                                }
                            });

                    get_message.setText(null);


                }


            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        messagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (messagesAdapter != null) {
            messagesAdapter.notifyDataSetChanged();
        }
    }




}