package com.ashish.contractedfarming.Farmer.News;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.ashish.contractedfarming.Admin.Dashboard.News.AdminNewsModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        list.add(new AdminNewsModel("1","asfg","20/01/2023"," cvcvcvc"," "));
        list.add(new AdminNewsModel("2","asfg","20/01/2023"," cvcvcvc"," "));

        FarmerNewsAdapter adapter = new FarmerNewsAdapter(this,list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);





    }
}
