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

public class AdminAgentNotificationFragment extends Fragment {

    public AdminAgentNotificationFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View  view = null;
       view = inflater.inflate(R.layout.fragment_admin_agent_notification,container,false);
       RecyclerView rv = view.findViewById(R.id.fragment_admin_admin_notificationlist);


        return view;
    }
}
