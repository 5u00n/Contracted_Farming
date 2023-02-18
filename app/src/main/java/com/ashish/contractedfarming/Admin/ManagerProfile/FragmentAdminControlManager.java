package com.ashish.contractedfarming.Admin.ManagerProfile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ashish.contractedfarming.Admin.Dashboard.Manager.AdminManagerAdapter;
import com.ashish.contractedfarming.Admin.Dashboard.Manager.AdminManagerModel;
import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

public class FragmentAdminControlManager extends Fragment {


    public FragmentAdminControlManager() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_admin_control_agents, container, false);

        Context context = getContext();


        SearchView searchView = v.findViewById(R.id.admin_agent_control_agentlist_search);
        ListView listView = v.findViewById(R.id.fragment_admin_agent_list_lv);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("users").child("agent");


        List<AdminManagerModel> arrayList = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (!searchView.hasFocus()) {
                        arrayList.removeAll(arrayList);
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            arrayList.add(new AdminManagerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("address").child("vill").getValue().toString(), ds.child("imgurl").getValue().toString()));
                        }
                        AdminManagerAdapter adapter = new AdminManagerAdapter(getContext(), arrayList);
                        if (adapter != null) {
                            listView.setAdapter(adapter);
                        }
                    }
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {

                            arrayList.removeAll(arrayList);
                            for (DataSnapshot ds : snapshot.getChildren()) {

                                if (newText.length() <= ds.child("username").toString().length()) {
                                    if (ds.child("username").toString().toLowerCase().contains(newText.toString().toLowerCase())) {
                                        arrayList.add(new AdminManagerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("address").child("vill").getValue().toString(), ds.child("imgurl").getValue().toString()));
                                    }
                                }
                            }
                            AdminManagerAdapter adapter = new AdminManagerAdapter(getContext(), arrayList);
                            if (adapter != null) {
                                listView.setAdapter(adapter);
                            }

                            return false;
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                AdminManagerAdapter ob = (AdminManagerAdapter) adapterView.getAdapter();
                //Log.d("Agent Clicks ", "ID : " + ob.getItem(i).getId()+ "    long: " + l);


                reference.child(ob.getItem(i).getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LayoutInflater li = LayoutInflater.from(getContext());
                        View promptsView = li.inflate(R.layout.popup_agent_profile, null);


                        TextView name, no, vill, circ, talu, dist, pin;
                        ImageView imageView;
                        name = promptsView.findViewById(R.id.popup_agent_name);
                        no = promptsView.findViewById(R.id.popup_agent_phone);
                        vill = promptsView.findViewById(R.id.popup_agent_village);
                        circ = promptsView.findViewById(R.id.popup_agent_circle);
                        talu = promptsView.findViewById(R.id.popup_agent_taluka);
                        dist = promptsView.findViewById(R.id.popup_agent_district);
                        pin = promptsView.findViewById(R.id.popup_agent_pin);
                        imageView = promptsView.findViewById(R.id.popup_agent_image);

                        name.setText(snapshot.child("name").getValue().toString());
                        if (snapshot.child("imgurl").getValue() != null) {
                            Picasso.get().load(snapshot.child("imgurl").getValue().toString()).into(imageView);
                        }
                        no.setText(snapshot.child("mob").getValue().toString());
                        vill.setText("Village : " + snapshot.child("village").getValue().toString());
                        circ.setText("Circle    : " + snapshot.child("circle").getValue().toString());
                        talu.setText("Taluka : " + snapshot.child("taluka").getValue().toString());
                        dist.setText("District : " + snapshot.child("dis").getValue().toString());
                        pin.setText("PIN      : " + snapshot.child("pin").getValue().toString());


                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        // set dialog message
                        alertDialogBuilder.setCancelable(false).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        return v;
    }
}