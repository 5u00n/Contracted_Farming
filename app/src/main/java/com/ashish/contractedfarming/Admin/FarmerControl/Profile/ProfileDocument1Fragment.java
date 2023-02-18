package com.ashish.contractedfarming.Admin.FarmerControl.Profile;

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

public class ProfileDocument1Fragment extends Fragment {
    DataSnapshot ds;
    public ProfileDocument1Fragment(DataSnapshot ds) {
        // Required empty public constructor
        this.ds=ds;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_farmer_profile_document, container, false);

        ImageView aadhar;
        TextView aadhar_no;
        aadhar= v.findViewById(R.id.fragment_farmer_document_aadhar_img);
        aadhar_no= v.findViewById(R.id.fragment_farmer_document_aadhar_no);

        Picasso.get().load(ds.child("verification").child("aadhaar_url").getValue().toString()).into(aadhar);
        aadhar_no.setText(ds.child("verification").child("adhar_no").getValue().toString());



        ///
        return v;

    }
}