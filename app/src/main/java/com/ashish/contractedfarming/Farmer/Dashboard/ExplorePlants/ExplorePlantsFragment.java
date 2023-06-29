package com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.FragmentHelper.ExplorePlantsAdapter;
import com.ashish.contractedfarming.Models.PlantModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ExplorePlantsFragment extends Fragment {

    Context context;

    RecyclerView recyclerView;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    SearchView searchView;


    ArrayList<PlantModel> arrayList ;
    
    
    public ExplorePlantsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_explore_plants, container, false);
        
        context = getContext();

        auth=FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        reference=database.getReference("plants");

        recyclerView=v.findViewById(R.id.farmer_explore_plant_recycler);
        searchView=v.findViewById(R.id.farmer_explore_plant_search);


        
        
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList= new ArrayList<>();
                final ExplorePlantsAdapter[] adapter = new ExplorePlantsAdapter[1];
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                if (snapshot.hasChildren()) {
                     if (!searchView.hasFocus() || searchView.getQuery()!=null) {
                         //adapter[0].notifyDataSetChanged();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            arrayList.add(new PlantModel(ds.child("id").getValue().toString(), ds.child("name").getValue().toString(), ds.child("life_span").getValue().toString(), ds.child("imgurl").getValue().toString()));

                            Log.d("all data ", String.valueOf(arrayList));
                        }

                        adapter[0] = new ExplorePlantsAdapter(arrayList, context);
                        recyclerView.setAdapter(adapter[0]);


                    }


                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }



                        @Override
                        public boolean onQueryTextChange(String newText) {

                            ArrayList<PlantModel> filteredlist = new ArrayList<PlantModel>();

                            // running a for loop to compare elements.
                            for (PlantModel item : arrayList) {
                                // checking if the entered string matched with any item of our recycler view.
                                if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                                    // if the item is matched we are
                                    // adding it to our filtered list.
                                    filteredlist.add(item);
                                }
                            }
                            if (filteredlist.isEmpty()) {
                                // if no item is added in filtered list we are
                                // displaying a toast message as no data found.
                                //Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
                            } else {
                                // at last we are passing that filtered
                                // list to our adapter class.
                                adapter[0].filterList(filteredlist);
                            }


                            return false;
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;
    }
}