package com.ashish.contractedfarming.Farmer.Dashboard.Home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.AdminPlantsModel;
import com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.PlantAddition.FarmerExplorePlantsActivity;
import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, holder.name.getText(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, FarmerExplorePlantsActivity.class);
                i.putExtra("plant_id",model.getId());
                i.putExtra("plant_url",model.getImg());
                i.putExtra("plant_name",model.getPlant());
                Log.d("From fragment",model.getId()+" : "+model.getPlant()+" : "+model.getImg());
                context.startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imag;
        TextView plantname,id;

        CardView cardView;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            cardView= itemView.findViewById(R.id.farmer_dash_explore_plant_layout);
            imag = itemView.findViewById(R.id.farmer_exploreplant_img);
            plantname = itemView.findViewById(R.id.farmer_exploreplant_plantname);
            id = itemView.findViewById(R.id.farmer_exploreplant_plantid);
        }
    }


}
    
