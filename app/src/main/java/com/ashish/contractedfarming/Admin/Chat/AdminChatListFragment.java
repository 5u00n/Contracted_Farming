package com.ashish.contractedfarming.Admin.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.ashish.contractedfarming.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class AdminChatListFragment extends Fragment {



    RecyclerView recyclerView;
    AppBarLayout appBarLayout;
    Toolbar toolbar;

    FloatingActionButton floatingActionButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    public AdminChatListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_admin_chat_list, container, false);

        recyclerView= view.findViewById(R.id.admin_chat_recyclerview);



        floatingActionButton= view.findViewById(R.id.admin_chat_floating);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int LAUNCH_SECOND_ACTIVITY = 1;
               // Intent i = new Intent(this, SecondActivity.class);
              //  startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
            }
        });
        return  view;
    }
}