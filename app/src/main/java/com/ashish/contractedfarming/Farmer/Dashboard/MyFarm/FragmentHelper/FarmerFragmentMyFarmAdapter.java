package com.ashish.contractedfarming.Farmer.Dashboard.MyFarm.FragmentHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashish.contractedfarming.Farmer.Dashboard.MyFarm.FarmerMyfarmModel;
import com.ashish.contractedfarming.R;

import java.util.ArrayList;

public class FarmerFragmentMyFarmAdapter  extends RecyclerView.Adapter<FarmerFragmentMyFarmAdapter.viewHolder>{
    ArrayList<FarmerMyfarmModel> list ;
    Context context;

    public FarmerFragmentMyFarmAdapter(ArrayList<FarmerMyfarmModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FarmerFragmentMyFarmAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_farmer_myfarm,parent,false);

        return new FarmerFragmentMyFarmAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerFragmentMyFarmAdapter.viewHolder holder, int position) {

        FarmerMyfarmModel model = list.get(position);
        holder.plot_name.setText("Farm Name : "+model.getPlotName());
        holder.area.setText(model.getArea());
        holder.village.setText(model.getVill());
        holder.taluka.setText(model.getTaluka());
        holder.district.setText(model.getDist());
        holder.state.setText(model.getState());
        holder.gatno.setText(model.getGatno());
        holder.survayno.setText(model.getSurvayno());
        holder.plant_name.setText(model.getPlant());
        holder.userUID.setText(model.getUserUID());
        holder.plotID.setText(model.getPlotID());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView plot_name, area, village, taluka, district, state, gatno, survayno, plant_name, userUID, plotID;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            plot_name = itemView.findViewById(R.id.farmer_addfarm_plot_name);
            area = itemView.findViewById(R.id.farmer_addfarm_area);
            village = itemView.findViewById(R.id.farmer_addfarm_village);
            taluka = itemView.findViewById(R.id.farmer_addfarm_taluka);
            district = itemView.findViewById(R.id.farmer_addfarm_district);
            state = itemView.findViewById(R.id.farmer_addfarm_state);
            gatno = itemView.findViewById(R.id.farmer_addfarm_gatno);
            survayno = itemView.findViewById(R.id.farmer_addfarm_survayno);
            plant_name = itemView.findViewById(R.id.farmer_addfarm_plant);
            userUID = itemView.findViewById(R.id.farmer_addfarm_userUID);
            plotID = itemView.findViewById(R.id.farmer_addfarm_plotID);
        }
    }



}
