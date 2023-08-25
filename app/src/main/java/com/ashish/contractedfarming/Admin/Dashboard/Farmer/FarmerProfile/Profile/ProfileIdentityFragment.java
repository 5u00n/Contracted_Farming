package com.ashish.contractedfarming.Admin.Dashboard.Farmer.FarmerProfile.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

public class ProfileIdentityFragment extends Fragment {
    DataSnapshot snapshot;


    ImageView aadhaar_img,pan_img,bank_img;
    TextView aadhaar_no, pan_no;
    public ProfileIdentityFragment(DataSnapshot ds) {
        // Required empty public constructor
        this.snapshot = ds;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_farmer_profile_document, container, false);
        aadhaar_img= v.findViewById(R.id.agent_verification_identity_aadhaar_img);
        aadhaar_no= v.findViewById(R.id.agent_verification_identity_aadhaar_no);
        pan_img= v.findViewById(R.id.agent_verification_identity_pan_img);
        pan_no= v.findViewById(R.id.agent_verification_identity_pan_no);
        bank_img= v.findViewById(R.id.agent_verification_identity_bank_img);

        if(snapshot.child("verification").child("aadhaar_url").exists()){
            Picasso.get().load(snapshot.child("verification").child("aadhaar_url").getValue().toString()).into(aadhaar_img);
        }
        if(snapshot.child("verification").child("pan_url").exists()){
            Picasso.get().load(snapshot.child("verification").child("pan_url").getValue().toString()).into(pan_img);
        }

        if(snapshot.child("verification").child("bank_url").exists()){
            Picasso.get().load(snapshot.child("verification").child("bank_url").getValue().toString()).into(bank_img);
        }

        aadhaar_no.setText("Aadhaar No. :"+snapshot.child("verification").child("aadhaar_no").getValue().toString());
        pan_no.setText("PAN no. :"+snapshot.child("verification").child("pan_no").getValue().toString());


        return v;

    }
}