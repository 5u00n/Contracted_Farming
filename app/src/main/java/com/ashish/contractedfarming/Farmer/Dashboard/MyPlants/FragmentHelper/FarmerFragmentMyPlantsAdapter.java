package com.ashish.contractedfarming.Farmer.Dashboard.MyPlants.FragmentHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.contractedfarming.Farmer.Dashboard.MyPlants.MyPlantModel;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.FarmerStoryModel;
import com.ashish.contractedfarming.Models.FarmerPlantModel;
import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FarmerFragmentMyPlantsAdapter extends RecyclerView.Adapter<FarmerFragmentMyPlantsAdapter.viewHolder>{
    ArrayList<FarmerPlantModel> list;
    Context context;


    public FarmerFragmentMyPlantsAdapter(ArrayList<FarmerPlantModel> list, Context context) {
        // super();
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FarmerFragmentMyPlantsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_farmer_my_plant,parent,false);
        return new FarmerFragmentMyPlantsAdapter .viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        FarmerPlantModel model = list.get(position);

        if(model.getPlant_progress()!=0) {
            holder.status.setText(model.getPlant_progress());
        }else{
            holder.status.setText("Not set");
        }
        holder.plantName.setText(model.getPlant_name());
        holder.farmName.setText(model.getFarm_name());
        holder.id.setText(model.getId());

        if(!model.getPlant_img_url().isEmpty()) {
            Picasso.get().load(model.getPlant_img_url()).into(holder.plantImg);
        }
        //holder.plantProgress.setProgress(model.getPlant_progress());
        if(!model.getFarm_img_url().isEmpty()) {
            Picasso.get().load(model.getFarm_img_url()).into(holder.plotImg);
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView  status,plantName,farmName,id;
        ImageView plotImg,plantImg;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            status= itemView.findViewById(R.id.view_farmer_my_plant_progress);
            plantName= itemView.findViewById(R.id.view_farmer_my_plant_plant_name);
            farmName= itemView.findViewById(R.id.view_farmer_my_plant_plot_name);
            id= itemView.findViewById(R.id.view_farmer_my_plant_id);


            plotImg= itemView.findViewById(R.id.view_farmer_my_plant_farm_img);
            plantImg= itemView.findViewById(R.id.view_farmer_my_plant_plant_img);



        }
    }
}