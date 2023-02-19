package com.ashish.contractedfarming.Farmer.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.ashish.contractedfarming.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FarmerNotificationAdapter extends ArrayAdapter<FarmerNotificationModel> {
    public FarmerNotificationAdapter(@NonNull Context context, List<FarmerNotificationModel> arr) {
        super(context, 0,arr);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FarmerNotificationModel a = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_farmer_notification,parent,false);
        TextView nm, txt;
        nm = convertView.findViewById(R.id.farmernotificationname);
        txt = convertView.findViewById(R.id.farmernotificationtxt);
        ImageView img1 = convertView.findViewById(R.id.farmernotificationpic);
        ImageView img2 = convertView.findViewById(R.id.farmernotificationrplay);
        img2.setImageResource(R.drawable.baseline_play_arrow_24);
        nm.setText(a.name);
        txt.setText(a.text);
        img1.setImageResource(a.img);





        return convertView;
    }
}
