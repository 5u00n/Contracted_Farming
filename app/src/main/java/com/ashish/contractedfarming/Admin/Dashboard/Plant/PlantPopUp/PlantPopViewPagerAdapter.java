package com.ashish.contractedfarming.Admin.Dashboard.Plant.PlantPopUp;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

public class PlantPopViewPagerAdapter extends PagerAdapter {

    Context mContext;
    int resId = 0;
    DataSnapshot snapshot;

    public PlantPopViewPagerAdapter(Context context, DataSnapshot snapshot) {
        mContext = context;
        this.snapshot = snapshot;
    }


    public Object instantiateItem(ViewGroup collection, int position) {


        ViewGroup layout;
        TextView id, name, disc, life, humidity, fertilizer, water, light, temp, imgurl;
        ImageView imageView;


        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (position) {
            case 0:
                resId = R.layout.fragment_plant_popup_first;
                layout = (ViewGroup) inflater.inflate(resId, collection, false);
                life = layout.findViewById(R.id.fragment_plant_popup_first_life);
                humidity = layout.findViewById(R.id.fragment_plant_popup_first_humidity);
                fertilizer = layout.findViewById(R.id.fragment_plant_popup_first_fertilizer);
                water = layout.findViewById(R.id.fragment_plant_popup_first_water);
                light = layout.findViewById(R.id.fragment_plant_popup_first_light);
                temp = layout.findViewById(R.id.fragment_plant_popup_first_temp);
                name = layout.findViewById(R.id.fragment_plant_popup_first_name);
                imageView = layout.findViewById(R.id.fragment_plant_popup_first_img);


                int name_size=snapshot.child("name").getValue().toString().length();
                if(name_size>8) {

                    name.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) (40- ((name_size-8))));
                }
                name.setText(snapshot.child("name").getValue().toString());
                life.setText(snapshot.child("life_span").getValue().toString());
                humidity.setText(snapshot.child("humidity").getValue().toString());
                fertilizer.setText(snapshot.child("fertilizer").getValue().toString());
                water.setText(snapshot.child("water_time").getValue().toString());
                temp.setText(snapshot.child("temprature").getValue().toString());
                light.setText(snapshot.child("light").getValue().toString());

                Picasso.get().load(snapshot.child("imgurl").getValue().toString()).into(imageView);

                break;
            case 1:
                resId = R.layout.fragment_plant_popup_second;
                layout = (ViewGroup) inflater.inflate(resId, collection, false);
                disc = layout.findViewById(R.id.fragment_plant_popup_second_disc);
                disc.setText(snapshot.child("disc").getValue().toString());
                imageView = layout.findViewById(R.id.fragment_plant_popup_second_img);
                Picasso.get().load(snapshot.child("imgurl").getValue().toString()).into(imageView);

                break;
            default:
                layout = (ViewGroup) inflater.inflate(resId, collection, false);
                break;
        }

        collection.addView(layout);
        return layout;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}