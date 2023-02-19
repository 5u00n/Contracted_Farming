package com.ashish.contractedfarming.Farmer.ConferenceAndWorkShop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import com.ashish.contractedfarming.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FarmerCandWAdapter extends ArrayAdapter<FarmerCandWModel> {
    public FarmerCandWAdapter(@NonNull Context context, List<FarmerCandWModel>arr) {
        super(context, 0,arr);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FarmerCandWModel a = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_farmer_candw,parent,false);

        ImageView img1 = convertView.findViewById(R.id.farmerconferanceimgs);



        img1.setImageResource(a.img);


        return convertView;
    }
}
