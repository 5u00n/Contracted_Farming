package com.ashish.contractedfarming.Farmer.Dashboard.MyPlants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.AdminPlantsModel;
import com.ashish.contractedfarming.R;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FarmerMyplantsAdapter extends RecyclerView.Adapter<FarmerMyplantsAdapter.viewHolder> {

    ArrayList<AdminPlantsModel> list;
    Context context;

    public FarmerMyplantsAdapter(ArrayList<AdminPlantsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_myplants,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        AdminPlantsModel model = list.get(position);
       // holder.image.setImageResource(R.drawable.tomatoimg);
        holder.id.setText(model.getId());
        holder.name.setText(model.getPlant());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

 /*   @NonNull
    @Override
    public FarmerExploreplantsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_myplants,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerExploreplantsAdapter.viewHolder holder, int position) {
        AdminPlantsModel model = list.get(position);
        holder.imag.setImageResource(R.drawable.tomato2pic);
        holder.id.setText(model.getId());
        holder.plantname.setText(model.getPlant());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }








    */

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView id,name;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.farmer_myplant_plantimg);
            name= itemView.findViewById(R.id.farmer_myplant_plantName);
            id= itemView.findViewById(R.id.farmer_myplant_id);
        }
    }
}
