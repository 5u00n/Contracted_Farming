package com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.PlantAddition;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.AdminPlantsModel;
import com.ashish.contractedfarming.Models.PlantModel;
import com.ashish.contractedfarming.Models.PlotModel;
import com.ashish.contractedfarming.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FarmerSelectFarmListViewAdapter extends ArrayAdapter<PlotModel> {

    private List<Boolean> checkboxStates;
    ArrayList<PlotModel> plotmodels;
    TextView id,plot_name,approval,location;
    ImageView plot_img;


    MaterialCardView layout;
    Context context;



    public FarmerSelectFarmListViewAdapter(@NonNull Context context,ArrayList<PlotModel> plotModels) {
        super(context,0,plotModels);

        this.context=getContext();
        this.plotmodels= plotModels;
        checkboxStates = new ArrayList<>(Collections.nCopies(plotModels.size(), false));
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
        layout=row.findViewById(R.id.view_select_farm_listview_card);


        MaterialCheckBox checked1= row.findViewById(R.id.view_select_farm_listview_checkbox1);

        checked1.setChecked(checkboxStates.get(position));


        id.setText(model.getPlotID());
        if(model. getPlot_img_url()!="--" || model.getPlot_img_url()!=null) {
            Picasso.get().load(model.getPlot_img_url()).into(plot_img);
        }
        plot_name.setText(model.getPlotName());
        location.setText(model.getArea()+","+model.getVillage());
        approval.setText(model.getApproval());


        ColorStateList colorList=layout.getCardBackgroundColor();
        row.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(!checked1.isChecked()) {
                    checked1.toggle();
                    //layout.setCardBackgroundColor(Color.GREEN);
                }
                else {
                    checked1.toggle();
                    //layout.setCardBackgroundColor(colorList);
                }

               // if(layout.getCardBackgroundColor())

            }
        });


        checked1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               // Log.d("------checkbox---", String.valueOf(b));
                checkboxStates.set(position, b);
            }
        });
        return row;

    }


    public List<Boolean> getCheckboxStates() {
        return checkboxStates;
    }




}
