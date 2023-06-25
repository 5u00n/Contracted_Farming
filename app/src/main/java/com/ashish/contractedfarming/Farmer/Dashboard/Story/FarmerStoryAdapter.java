package com.ashish.contractedfarming.Farmer.Dashboard.Story;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FarmerStoryAdapter extends RecyclerView.Adapter<FarmerStoryAdapter.viewHolder>  {

    ArrayList<FarmerStoryModel> list;
    Context context;


    public FarmerStoryAdapter(ArrayList<FarmerStoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(contex).inflate(R.layout.view_farmer_story, parent, false);
        View view = LayoutInflater.from(context).inflate(R.layout.view_farmer_story,parent,false);
        return new viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        FarmerStoryModel model = list.get(position);
       // holder.userimg.setImageResource(R.drawable.farmerimg);
       // holder.storyimg.setImageResource(R.drawable.news2);
        holder.name.setText(model.getUsername());
        holder.userUID.setText(model.getUserUID());
        Picasso.get().load(model.img_url).into(holder.storyimg);
        Picasso.get().load(model.user_img_url).into(holder.userimg);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView name,userUID;
        ImageView userimg,storyimg;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.farmer_story_farmer_name);
            userimg = itemView.findViewById(R.id.farmer_story_profil_img);
            storyimg = itemView.findViewById(R.id.addStoryImg);
            userUID = itemView.findViewById(R.id.farmer_storyuser_uid);


        }
    }
}
