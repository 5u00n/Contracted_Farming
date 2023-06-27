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
import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FarmerFragmentMyPlantsAdapter extends RecyclerView.Adapter<FarmerFragmentMyPlantsAdapter.viewHolder>{
    ArrayList<MyPlantModel> list;
    Context context;


    public FarmerFragmentMyPlantsAdapter(ArrayList<MyPlantModel> list, Context context) {
        // super();
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FarmerFragmentMyPlantsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_story,parent,false);
        return new FarmerFragmentMyPlantsAdapter .viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        MyPlantModel model = list.get(position);

        holder.status.setText(model.getPlant_status());
        holder.plantName.setText(model.getPlant_name());
        holder.farmName.setText(model.getFarm_name());

        //Picasso.get().load(model.getBackground_img_url()).into(holder.backgroundImg);
        holder.plantProgress.setProgress(model.getPlant_progress());
        Picasso.get().load(model.getPlant_img_url()).into(holder.plantImg);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView  status,plantName,farmName;
        ImageView backgroundImg,plantImg;
        ProgressBar plantProgress;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            status= itemView.findViewById(R.id.farmer_my_plant_status_text);
            plantName= itemView.findViewById(R.id.farmer_my_plant_name_text);
            farmName= itemView.findViewById(R.id.farmer_my_plant_farm_text);
            backgroundImg= itemView.findViewById(R.id.farmer_my_plant_background_img);
            plantImg= itemView.findViewById(R.id.farmer_my_plant_img);
            plantProgress= itemView.findViewById(R.id.farmer_my_plant_progress);



        }
    }
}