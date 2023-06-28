package com.ashish.contractedfarming.Farmer.Dashboard.Story.FragmentHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.contractedfarming.Farmer.Dashboard.Story.FarmerStoryModel;
import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FarmerFragmentStoryAdapter extends RecyclerView.Adapter<FarmerFragmentStoryAdapter.viewHolder>{
    ArrayList<FarmerStoryModel> list;
    Context context;


    public FarmerFragmentStoryAdapter(ArrayList<FarmerStoryModel> list, Context context) {
       // super();
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FarmerFragmentStoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_story,parent,false);
        return new FarmerFragmentStoryAdapter.viewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull FarmerFragmentStoryAdapter.viewHolder holder, int position) {
        FarmerStoryModel model = list.get(position);
        holder.topic.setText(model.getStory_text());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM YYY, hh:mm aa");

        holder.date.setText(dateFormat.format(new Date(Long.parseLong(model.getStory_time())*1000)));
        Picasso.get().load(model.getImg_url()).into(holder.storyimg);
        //Picasso.get().load(model.user_img_url).into(holder.userimg);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView  topic,date;
        ImageView storyimg;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            topic= itemView.findViewById(R.id.farmer_fragment_recycler_story_details);
            storyimg = itemView.findViewById(R.id.farmer_fragment_recycler_story_img);
            date = itemView.findViewById(R.id.farmer_fragment_recycler_story_date);


        }
    }
}