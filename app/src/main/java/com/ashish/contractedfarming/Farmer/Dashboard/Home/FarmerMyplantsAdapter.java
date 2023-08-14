package com.ashish.contractedfarming.Farmer.Dashboard.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.contractedfarming.Models.FarmerPlantModel;
import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FarmerMyplantsAdapter extends RecyclerView.Adapter<FarmerMyplantsAdapter.viewHolder> {

    ArrayList<FarmerPlantModel> list;
    Context context;

    public FarmerMyplantsAdapter(ArrayList<FarmerPlantModel> list, Context context) {
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
        FarmerPlantModel model = list.get(position);
       // holder.image.setImageResource(R.drawable.tomatoimg);
        holder.id.setText(model.getId());
        holder.plotName.setText(model.getFarm_name());
        holder.plantName.setText(model.getPlant_name());

        if(!model.getFarm_img_url().isEmpty()){
            Picasso.get().load(model.getFarm_img_url()).into(holder.plotImage);
        }
        if(!model.getPlant_img_url().isEmpty()){
            Picasso.get().load(model.getPlant_img_url()).into(holder.plantImage);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView plotImage;
        TextView id,plantName,plotName;
        ImageView plantImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            plotImage = itemView.findViewById(R.id.farmer_myplant_plotimg);
            plantName= itemView.findViewById(R.id.farmer_myplant_plantName);
            plotName=itemView.findViewById(R.id.farmer_myplant_plotName);
            id= itemView.findViewById(R.id.farmer_myplant_id);
            plantImage=itemView.findViewById(R.id.farmer_myplant_plant_img_bg);
        }
    }
}
