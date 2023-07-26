package com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.FragmentHelper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.PlantAddition.FarmerExplorePlantsActivity;
import com.ashish.contractedfarming.Models.PlantModel;
import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExplorePlantsAdapter extends RecyclerView.Adapter<ExplorePlantsAdapter.viewHolder>{
    ArrayList<PlantModel> list;
    Context context;


    View view;

    public ExplorePlantsAdapter(ArrayList<PlantModel> list, Context context,View view) {
        // super();
        this.list = list;
        this.context = context;
        this.view=view;
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, holder.name.getText(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, FarmerExplorePlantsActivity.class);
                i.putExtra("plant_id",model.getId());
                context.startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView id,name,lifecycle;
        ImageView plant_image,back_img;

        CardView cardView;





        public viewHolder(@NonNull View itemView) {
            super(itemView);




            cardView= itemView.findViewById(R.id.view_plant_main_layout);
            id= itemView.findViewById(R.id.farmer_plant_id);
            name= itemView.findViewById(R.id.farmer_plant_name_text);
            lifecycle = itemView.findViewById(R.id.farmer_plant_lifespan_text);
            plant_image=itemView.findViewById(R.id.farmer_plant_img);
            back_img= itemView.findViewById(R.id.farmer_plant_background_img);


        }
    }
}