package com.ashish.contractedfarming.Admin.Dashboard.Plant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminPlantsAdapter extends ArrayAdapter<AdminPlantsModel> {
    public AdminPlantsAdapter(@NonNull Context context, List<AdminPlantsModel> arr) {
        super(context, 0, arr);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AdminPlantsModel a = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_admin_plants, parent, false);
        TextView pl, id;
        pl = convertView.findViewById(R.id.adminplantsname);
        ImageView img1 = convertView.findViewById(R.id.adminplantimgs);
        id = convertView.findViewById(R.id.view_admin_plants_id);
        pl.setText(a.plant);
        id.setText(a.id);
        if (a.getImg() != null) {
            Picasso.get().load(a.getImg()).into(img1);
        }
        return convertView;
    }
}
