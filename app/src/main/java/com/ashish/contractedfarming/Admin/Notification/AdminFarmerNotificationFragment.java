package com.ashish.contractedfarming.Admin.Notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ashish.contractedfarming.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class AdminFarmerNotificationFragment extends Fragment {

    public   AdminFarmerNotificationFragment(){


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= null;
       view = inflater.inflate(R.layout.fragment_admin_farmer_notification,container,false);
        RecyclerView rv = view.findViewById(R.id.fragment_admin_admin_notificationlist);


        return view;
    }
}
