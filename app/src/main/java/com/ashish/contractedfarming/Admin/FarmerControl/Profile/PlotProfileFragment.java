package com.ashish.contractedfarming.Admin.FarmerControl.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ashish.contractedfarming.Farmer.NewFarmer.AddPlotActivity;
import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class PlotProfileFragment extends Fragment {


    DataSnapshot snapshot;
    public PlotProfileFragment(DataSnapshot snapshot) {
        // Required empty public constructor
        this.snapshot= snapshot;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_plot_profile, container, false);

        ListView lv= v.findViewById(R.id.fragment_farmer_plot_list);

                    ArrayList<String> list= new ArrayList<>();
                    int count=1;
                    for(DataSnapshot ds: snapshot.child("plot").getChildren()){
                        list.add("Plot : "+count+"  |  Gat No : "+ds.child("gat_no").getValue().toString()+"  |  Sarvay No : "+ds.child("sarvay_no").getValue().toString());
                        count++;
                    }
                    lv.setAdapter(new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list));




        return v;
    }
}