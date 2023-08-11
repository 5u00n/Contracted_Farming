package com.ashish.contractedfarming.Farmer.Dashboard.MyPlants.FragmentHelper;

import android.os.Bundle;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.AdminPlantsModel;
import com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.FarmerExploreplantsAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.MyPlants.FarmerMyplantsAdapter;
import com.ashish.contractedfarming.R;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FarmerMyplantsActivity extends AppCompatActivity {
    public RecyclerView rv2;
    public RecyclerView rv1;

   // int img[] = {R.drawable.cherrytomatopic, R.drawable.parsleypic,R.drawable.asparaguss,R.drawable.cherrytomatopic};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_plant);
        rv2 = findViewById(R.id.exploreplantsrv);
        rv1 = findViewById(R.id.myplantsrv);


       // for (int i = 0; i < 4; i++) {
       //     list.add(new FarmerMyplantsModel(img[i]));

       // }


      /*  list.add(new AdminPlantsModel(R.drawable.zucchinipic));
        list.add(new AdminPlantsModel(R.drawable.cherrytomatopic));
        list.add(new AdminPlantsModel(R.drawable.cherrytomatopic));
        list.add(new AdminPlantsModel(R.drawable.cherrytomatopic));*/



        ArrayList<AdminPlantsModel> list1 = new ArrayList<>();


        list1.add(new AdminPlantsModel("1","Potato","https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));
        list1.add(new AdminPlantsModel("2","Tomato","https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));



        ArrayList<AdminPlantsModel> list = new ArrayList<>();

        list.add(new AdminPlantsModel("1","","https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));
        list.add(new AdminPlantsModel("1","ppp","https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));
        list.add(new AdminPlantsModel("1","ppp","https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fexternal%2Fslideshow%2Ffull%2F141151_full.jpg&imgrefurl=https%3A%2F%2Fwww.macmillandictionary.com%2Fdictionary%2Fbritish%2Fpotato&tbnid=1CeaBPMDK9eX9M&vet=12ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ..i&docid=D83Ugcq3LvI-CM&w=1280&h=680&q=potato&ved=2ahUKEwjw_7b0v539AhUV3nMBHf_2C9kQMygCegUIARDsAQ"));



        FarmerExploreplantsAdapter adapter1 = new FarmerExploreplantsAdapter(list1, this);





        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);

        rv2.setLayoutManager(layoutManager1);
        rv2.setNestedScrollingEnabled(false);
        rv2.setAdapter(adapter1);




        FarmerMyplantsAdapter adapter = new FarmerMyplantsAdapter(list,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);

        rv1.setLayoutManager(layoutManager);
        rv1.setNestedScrollingEnabled(false);
        rv1.setAdapter(adapter);
       // rv1.setHasFixedSize(true);




        // rv1.setHasFixedSize(true);


      //  FarmerMyplantsAdapter adapter1 = new FarmerMyplantsAdapter(list1,this);
     //   rv2.setAdapter(adapter1);

       /* FarmerStoryAdapter adapter = new FarmerStoryAdapter(farmerstoryList,context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true);
        farmerstoryRV.setLayoutManager(layoutManager);
        farmerstoryRV.setNestedScrollingEnabled(false);
        farmerstoryRV.setAdapter(adapter);*/

    }
}
