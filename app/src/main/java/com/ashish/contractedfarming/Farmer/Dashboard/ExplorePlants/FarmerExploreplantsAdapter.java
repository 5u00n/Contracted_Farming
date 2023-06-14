package com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.AdminPlantsModel;
import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FarmerExploreplantsAdapter extends RecyclerView.Adapter<FarmerExploreplantsAdapter.viewHolder> {
    ArrayList<AdminPlantsModel> list;
    Context context;

    public FarmerExploreplantsAdapter(ArrayList<AdminPlantsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_exploreplantscroller,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        
        AdminPlantsModel model = list.get(position);
       holder.plantname.setText(model.getPlant());
       Picasso.get().load(model.getImg()).into(holder.imag);
      // holder.imag.setImageResource(R.drawable.awalapic);

       holder.id.setText(model.getId());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imag;
        TextView plantname,id;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imag = itemView.findViewById(R.id.farmer_exploreplant_img);
            plantname = itemView.findViewById(R.id.farmer_exploreplant_plantname);
            id = itemView.findViewById(R.id.farmer_exploreplant_plantid);
        }
    }


}
    
