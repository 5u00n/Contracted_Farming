package com.ashish.contractedfarming.Farmer.Dashboard.Story.MyFarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ashish.contractedfarming.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FarmerMyfarmAdapter extends RecyclerView.Adapter<FarmerMyfarmAdapter.viewHolder>{
    ArrayList<FarmerMyfarmModel>list ;
    Context context;


    public FarmerMyfarmAdapter(ArrayList<FarmerMyfarmModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_farmer_myfarm,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

       FarmerMyfarmModel model = list.get(position);
       holder.area.setText(model.getArea());
       holder.village.setText(model.getVill());
       holder.taluka.setText(model.getTaluka());
       holder.district.setText(model.getDist());
       holder.state.setText(model.getState());
       holder.gatno.setText(model.getGatno());
       holder.survayno.setText(model.getSurvayno());
       holder.plant.setText(model.getPlant());
       holder.userUID.setText(model.getUserUID());
       holder.plotID.setText(model.getPlotID());


        /*   FarmerStoryModel model = list.get(position);
        holder.userimg.setImageResource(R.drawable.farmerimg);
        holder.storyimg.setImageResource(R.drawable.news);
        holder.name.setText(model.getUsername());
        holder.userUID.setText(model.getUserUID()); */

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
     TextView area,village,taluka,district,state,gatno,survayno,plant,userUID,plotID;

     public viewHolder(@NonNull View itemView) {
         super(itemView);
         area = itemView.findViewById(R.id.farmer_addfarm_area);
         village = itemView.findViewById(R.id.farmer_addfarm_village);
         taluka = itemView.findViewById(R.id.farmer_addfarm_taluka);
        district=itemView.findViewById(R.id.farmer_addfarm_district);
        state = itemView.findViewById(R.id.farmer_addfarm_state);
        gatno = itemView.findViewById(R.id.farmer_addfarm_gatno);
        survayno= itemView.findViewById(R.id.farmer_addfarm_survayno);
        plant= itemView.findViewById(R.id.farmer_addfarm_plant);
        userUID= itemView.findViewById(R.id.farmer_addfarm_userUID);
        plotID = itemView.findViewById(R.id.farmer_addfarm_plotID);
     }
 }



}
