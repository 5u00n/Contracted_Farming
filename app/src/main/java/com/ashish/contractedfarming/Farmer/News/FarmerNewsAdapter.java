package com.ashish.contractedfarming.Farmer.News;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.contractedfarming.Admin.Dashboard.News.AdminNewsModel;
import com.ashish.contractedfarming.R;

import java.util.ArrayList;

public class FarmerNewsAdapter extends RecyclerView.Adapter<FarmerNewsAdapter.viewHolder> {
    Context context;
    ArrayList<AdminNewsModel> list;

    public FarmerNewsAdapter(Context context, ArrayList<AdminNewsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_farmer_news,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        AdminNewsModel model = list.get(position);
        //holder.newspic.setImageResource(R.drawable.news);
        holder.id.setText(model.getId());
        holder.topic.setText(model.getTopic());
        holder.time.setText(model.getDate());


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
TextView time,id,topic;
ImageView newspic;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.view_farmer_news_time);
            id = itemView.findViewById(R.id.view_farmer_news_id);
            topic= itemView.findViewById(R.id.view_farmer_news_title);
            newspic = itemView.findViewById(R.id.view_farmer_news_img);
        }
    }
}
