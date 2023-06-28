package com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.FragmentHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.AdminPlantsModel;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.FarmerStoryModel;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.FragmentHelper.FarmerFragmentStoryAdapter;
import com.ashish.contractedfarming.Models.PlantModel;
import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExplorePlantsAdapter extends RecyclerView.Adapter<ExplorePlantsAdapter.viewHolder>{
    ArrayList<PlantModel> list;
    Context context;


    public ExplorePlantsAdapter(ArrayList<PlantModel> list, Context context) {
        // super();
        this.list = list;
        this.context = context;
    }

    public void filterList(ArrayList<PlantModel> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        list = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ExplorePlantsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_plants,parent,false);
        return new ExplorePlantsAdapter.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ExplorePlantsAdapter.viewHolder holder, int position) {
        PlantModel model = list.get(position);


       holder.id.setText(model.getId());
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM YYY, hh:mm aa");

        holder.name.setText(model.getName());
        holder.lifecycle.setText(model.getLife_span());
        Picasso.get().load(model.getImgurl()).into(holder.plant_image);
        //Picasso.get().load(model.user_img_url).into(holder.userimg);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView id,name,lifecycle;
        ImageView plant_image,back_img;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            id= itemView.findViewById(R.id.farmer_plant_id);
            name= itemView.findViewById(R.id.farmer_plant_name_text);
            lifecycle = itemView.findViewById(R.id.farmer_plant_lifespan_text);
            plant_image=itemView.findViewById(R.id.farmer_plant_img);
            back_img= itemView.findViewById(R.id.farmer_plant_background_img);


        }
    }
}