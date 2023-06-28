package com.ashish.contractedfarming.Admin.Dashboard.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ashish.contractedfarming.Admin.FarmerControl.FarmerControlActivity;
import com.ashish.contractedfarming.Admin.ManagerProfile.AdminManagerControlActivity;
import com.ashish.contractedfarming.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminHomeFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<ListModel> listModels;

    ViewPager viewPager;


    CardView farmerCard, agentCard;

    TextView mg_stat,mg_reg,mg_rej;

    public AdminHomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        View v = inflater.inflate(R.layout.fragment_admin_dashboard_home, container, false);
        listModels = new ArrayList<>();

        ListView lv = v.findViewById(R.id.admin_dash_list);

        TextView cardS = v.findViewById(R.id.admin_dash_card_stat);
        TextView reg = v.findViewById(R.id.admin_dash_reg_farm);
        TextView rej = v.findViewById(R.id.admin_dash_rej_farm);
        mg_stat=v.findViewById(R.id.admin_dash_card_stat_agent);
        mg_reg=v.findViewById(R.id.admin_dash_reg_agent);
        mg_rej=v.findViewById(R.id.admin_dash_rej_agent);






        viewPager = getActivity().findViewById(R.id.view_pager);



        farmerCard=v.findViewById(R.id.admin_dash_card_farmer);
        agentCard=v.findViewById(R.id.admin_dash_card_agent);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listModels.removeAll(listModels);
                cardS.setText(String.valueOf(snapshot.child("users").child("farmer").getChildrenCount()));
                reg.setText(String.valueOf(snapshot.child("users").child("new-farmer").getChildrenCount()));
                rej.setText(String.valueOf(snapshot.child("users").child("rej-farmer").getChildrenCount()));

                mg_stat.setText(String.valueOf(snapshot.child("users").child("manager").getChildrenCount()));
                mg_reg.setText(String.valueOf(snapshot.child("users").child("new-manager").getChildrenCount()));
                mg_rej.setText(String.valueOf(snapshot.child("users").child("rej-manager").getChildrenCount()));

                listModels.add(new ListModel("News", "" + snapshot.child("news").getChildrenCount(), R.drawable.baseline_newspaper_white_24));
                listModels.add(new ListModel("Plants", "" + snapshot.child("plants").getChildrenCount(), R.drawable.ic_baseline_grass_24));
                listModels.add(new ListModel("Farmer", "" + snapshot.child("users").child("farmer").getChildrenCount(), R.drawable.ic_baseline_hiking_24));
                listModels.add(new ListModel("Manager", "" + snapshot.child("users").child("manager").getChildrenCount(), R.drawable.ic_baseline_support_agent_24));
                listModels.add(new ListModel("Notification", String.valueOf(snapshot.child("conf-workshop").getChildrenCount() + snapshot.child("users").child("admin").child("notification").getChildrenCount()), R.drawable.baseline_notifications_none_white_24));
                if (getContext() != null) {
                    CListAdapter adapter = new CListAdapter(getContext(), listModels);

                    if (adapter != null) {
                        lv.setAdapter(adapter);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 4) {
                    viewPager.setCurrentItem(i + 1);
                } else {
                }

            }
        });


        farmerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), FarmerControlActivity.class).putExtra("from_user","admin"));
            }
        });


        agentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AdminManagerControlActivity.class).putExtra("from_user","admin"));

            }
        });
        /// CListAdapter adapter= new CListAdapter(getContext(),);
        return v;
    }


}
