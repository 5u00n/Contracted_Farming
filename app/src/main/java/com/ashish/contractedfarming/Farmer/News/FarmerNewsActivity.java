package com.ashish.contractedfarming.Farmer.News;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.contractedfarming.Admin.Dashboard.News.AdminNewsModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FarmerNewsActivity extends AppCompatActivity {


    private static int PICK_IMAGE = 123;
    Uri imageUri = null;

    RecyclerView rv;
    Context context;

    ArrayList<AdminNewsModel> list;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_news);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("news");

        rv = findViewById(R.id.farmer_news_rv);
        context = getBaseContext();
        list = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.removeAll(list);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    list.add(new AdminNewsModel(ds.child("id").getValue().toString(), ds.child("topic").getValue().toString(), ds.child("date").getValue().toString(), ds.child("data").getValue().toString(), ds.child("imgurl").getValue().toString()));
                }
                if(getBaseContext()!=null) {
                    FarmerNewsAdapter adapter = new FarmerNewsAdapter(FarmerNewsActivity.this,list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);

                    if (adapter != null) {
                        rv.setLayoutManager(layoutManager);
                        rv.setAdapter(adapter);
                    }
                }
                ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







    }
}
