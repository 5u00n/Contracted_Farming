package com.ashish.contractedfarming.Admin.ManagerProfile.Profile;

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

public class FragmentManagerProfileIdentity extends Fragment {
    DataSnapshot snapshot;


    ImageView aadhaar_img,pan_img;
    TextView aadhaar_no, pan_no;

    public FragmentManagerProfileIdentity(DataSnapshot snapshot) {
        // Required empty public constructorthi
        this.snapshot = snapshot;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_agent_verification_identity, container, false);

        aadhaar_img= v.findViewById(R.id.agent_verification_identity_aadhaar_img);
        aadhaar_no= v.findViewById(R.id.agent_verification_identity_aadhaar_no);
        pan_img= v.findViewById(R.id.agent_verification_identity_pan_img);
        pan_no= v.findViewById(R.id.agent_verification_identity_pan_no);

        if(snapshot.child("verification").child("aadhaar_url").exists()){
            Picasso.get().load(snapshot.child("verification").child("aadhaar_url").getValue().toString()).into(aadhaar_img);
        }
        if(snapshot.child("verification").child("pan_url").exists()){
            Picasso.get().load(snapshot.child("verification").child("pan_url").getValue().toString()).into(pan_img);
        }

        aadhaar_no.setText("Aadhaar No. :"+snapshot.child("verification").child("aadhaar_no").getValue().toString());
        pan_no.setText("PAN no. :"+snapshot.child("verification").child("pan_no").getValue().toString());

        return v;

    }
}