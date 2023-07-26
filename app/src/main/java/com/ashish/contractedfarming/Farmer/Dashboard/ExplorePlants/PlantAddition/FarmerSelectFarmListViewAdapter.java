package com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.PlantAddition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.AdminPlantsModel;
import com.ashish.contractedfarming.Models.PlantModel;
import com.ashish.contractedfarming.Models.PlotModel;
import com.ashish.contractedfarming.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FarmerSelectFarmListViewAdapter extends ArrayAdapter<PlotModel> {

    ArrayList<PlotModel> plotmodels;
    TextView id,plot_name,approval,location;
    ImageView plot_img;
    CheckBox checked;

    public FarmerSelectFarmListViewAdapter(@NonNull Context context,ArrayList<PlotModel> plotModels) {
        super(context,0,plotModels);

        this.plotmodels= plotModels;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PlotModel model=  getItem(position);
        View row =LayoutInflater.from(getContext()).inflate(R.layout.view_listview_farmer_farm,parent,false);

        id=row.findViewById(R.id.view_select_farm_listview_text_id);
        plot_name= row.findViewById(R.id.view_select_farm_listview_text_farm_name);
        approval= row.findViewById(R.id.view_select_farm_listview_text_status);
        location= row.findViewById(R.id.view_select_farm_listview_text_location);
        plot_img= row.findViewById(R.id.view_select_farm_listview_img_farm);

        checked= row.findViewById(R.id.view_select_farm_listview_checkbox);


        id.setText(model.getPlotID());
        if(model. getPlot_img_url()!="--" || model.getPlot_img_url()!=null) {
            Picasso.get().load(model.getPlot_img_url()).into(plot_img);
        }
        plot_name.setText(model.getPlotName());
        location.setText(model.getArea()+","+model.getVillage());
        approval.setText(model.getApproval());
        return row;

    }
}
