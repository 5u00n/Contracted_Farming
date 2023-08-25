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

public class FarmerProfileFragment extends Fragment {
    DataSnapshot snapshot;

    ImageView profile;
    TextView name, mob_no, vill, cir, taluka, dist, stat, pin, id;


    public FarmerProfileFragment(DataSnapshot ds) {
        // Required empty public constructor
        this.snapshot=ds;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle saveIstanceState){
       View v = inflater.inflate(R.layout.fragment_farmer_verificationone, container, false);


        profile = v.findViewById(R.id.agent_verification_identity_image);

        name = v.findViewById(R.id.agent_verification_identity_name);
        id = v.findViewById(R.id.agent_verification_identity_id);
        mob_no = v.findViewById(R.id.agent_verification_identity_phone);
        vill = v.findViewById(R.id.agent_verification_identity_village);
        cir = v.findViewById(R.id.agent_verification_identity_circle);
        taluka = v.findViewById(R.id.agent_verification_identity_taluka);
        dist = v.findViewById(R.id.agent_verification_identity_district);
        stat = v.findViewById(R.id.agent_verification_identity_state);
        pin = v.findViewById(R.id.agent_verification_identity_pin);

        if (snapshot.child("img_url").exists()) {
            Picasso.get().load(snapshot.child("img_url").getValue().toString()).into(profile);
        }

        name.setText(snapshot.child("username").getValue().toString());
        id.setText(snapshot.child("userUID").getValue().toString());
        mob_no.setText(snapshot.child("mob_no").getValue().toString());
        if(snapshot.child("address").exists()) {
            vill.setText("Village :   " + snapshot.child("address").child("village").getValue().toString());
            cir.setText("Circle :   " + snapshot.child("address").child("circle").getValue().toString());
            taluka.setText("Taluka :   " + snapshot.child("address").child("taluka").getValue().toString());
            dist.setText("District :   " + snapshot.child("address").child("dist").getValue().toString());
            stat.setText("State :   " + snapshot.child("address").child("state").getValue().toString());
            pin.setText("PIN :   " + snapshot.child("address").child("pin").getValue().toString());
        }


        return v;
    }
}
