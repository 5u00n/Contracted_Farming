package com.ashish.contractedfarming.Admin.Dashboard.Farmer;

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

import com.ashish.contractedfarming.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

public class FarmerFragment extends Fragment {
    public FarmerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_dashboard_farmer, container, false);


        Context context = getContext();


        SearchView searchView = v.findViewById(R.id.admin_dash_farmer_search);
        ListView listView = v.findViewById(R.id.admin_dash_farmer_list);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("users").child("farmer");


        List<AdminFarmerModel> arrayList = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!searchView.hasFocus()) {
                    arrayList.removeAll(arrayList);
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        arrayList.add(new AdminFarmerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("address").child("village").getValue().toString(), ds.child("img_url").getValue().toString()));
                    }
                    AdminFarmerAdapter adapter = new AdminFarmerAdapter(getContext(), arrayList);
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
                                    arrayList.add(new AdminFarmerModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("address").child("village").getValue().toString(), ds.child("img_url").getValue().toString()));
                                }
                            }
                        }
                        AdminFarmerAdapter adapter = new AdminFarmerAdapter(getContext(), arrayList);
                        if (adapter != null) {
                            listView.setAdapter(adapter);
                        }

                        return false;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                AdminFarmerAdapter ob = (AdminFarmerAdapter) adapterView.getAdapter();
                //Log.d("Agent Clicks ", "ID : " + ob.getItem(i).getId()+ "    long: " + l);


                reference.child(ob.getItem(i).getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LayoutInflater li = LayoutInflater.from(getContext());
                        View promptsView = li.inflate(R.layout.popup_farmer_profile, null);


                        TextView name, no, vill, circ, talu, dist, pin, plants;
                        ImageView imageView;
                        plants = promptsView.findViewById(R.id.popup_farmer_plant);
                        name = promptsView.findViewById(R.id.popup_farmer_name);
                        no = promptsView.findViewById(R.id.popup_farmer_phone);
                        vill = promptsView.findViewById(R.id.popup_farmer_village);
                        circ = promptsView.findViewById(R.id.popup_farmer_circle);
                        talu = promptsView.findViewById(R.id.popup_farmer_taluka);
                        dist = promptsView.findViewById(R.id.popup_farmer_district);
                        pin = promptsView.findViewById(R.id.popup_farmer_pin);
                        imageView = promptsView.findViewById(R.id.popup_farmer_image);


                        plants.setText("Plant 1 : " + snapshot.child("plants").child("0").child("name").getValue().toString() + "\nPlant 2 : " + snapshot.child("plants").child("1").child("name").getValue().toString());
                        name.setText(snapshot.child("username").getValue().toString());
                        if (snapshot.child("img_url").getValue() != null) {
                            Picasso.get().load(snapshot.child("img_url").getValue().toString()).into(imageView);
                        }
                        no.setText(snapshot.child("mob_no").getValue().toString());
                        //vill.setText("Village : " + snapshot.child("adress").child("village").getValue().toString());
                        vill.setText("Village : " + snapshot.child("address").child("village").getValue().toString());
                        circ.setText("Circle : " + snapshot.child("address").child("circle").getValue().toString());
                        talu.setText("Taluka : " + snapshot.child("address").child("taluka").getValue().toString());
                        dist.setText("District : " + snapshot.child("address").child("dist").getValue().toString());
                        pin.setText("PIN      : " + snapshot.child("address").child("pin").getValue().toString());


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



