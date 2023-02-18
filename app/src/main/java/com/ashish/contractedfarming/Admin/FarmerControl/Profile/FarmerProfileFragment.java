package com.ashish.contractedfarming.Admin.FarmerControl.Profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import androidx.fragment.app.Fragment;

public class FarmerProfileFragment extends Fragment {
    DataSnapshot ds;

    TextView username,mob,vill,taluka,cir,dist,state,pin;
    ImageView profile;

    public FarmerProfileFragment(DataSnapshot ds) {
        // Required empty public constructor
        this.ds=ds;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle saveIstanceState){
       View v = inflater.inflate(R.layout.fragment_farmer_verificationone, container, false);

       username= v.findViewById(R.id.verify_farmer_name);

       profile= v.findViewById(R.id.verify_farmer_image);
       username.setText(ds.child("username").getValue().toString());

        Picasso.get().load(ds.child("imgurl").getValue().toString()).into(profile);
        Context context = getContext();
        //  AdminFarmerAdapter ob = (AdminFarmerAdapter) adapterView.getAdapter();
        return v;
    }
}
