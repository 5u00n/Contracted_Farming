package com.ashish.contractedfarming.Admin.Dashboard.Farmer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdminFarmerAdapter extends ArrayAdapter<AdminFarmerModel> {
    public AdminFarmerAdapter(@NonNull Context context, List<AdminFarmerModel> adminFarmerModels)
    {

        super(context, 0,adminFarmerModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AdminFarmerModel a = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_admin_farmer,parent,false);
        TextView nm, id,pl;
        nm = convertView.findViewById(R.id.admin_farmer_name);
        pl = convertView.findViewById(R.id.admin_farmer_place);
        id=convertView.findViewById(R.id.admin_farmer_id);
        ImageView img1 = convertView.findViewById(R.id.admin_farmer_icon);
        ImageView img2 = convertView.findViewById(R.id.admin_farmer_play);
        img2.setImageResource(R.drawable.baseline_play_arrow_24);
        nm.setText(a.name);
        pl.setText(a.place);
        id.setText(a.id);
        if(a.getImg()!=null) {
            Picasso.get().load(a.getImg()).into(img1);
        }
        return convertView;


    }
}

