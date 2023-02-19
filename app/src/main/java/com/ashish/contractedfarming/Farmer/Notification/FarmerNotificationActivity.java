package com.ashish.contractedfarming.Farmer.Notification;

import android.os.Bundle;
import android.widget.ListView;

import com.ashish.contractedfarming.R;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FarmerNotificationActivity extends AppCompatActivity {
    ListView lv;

    String name[] = {"Tomato", "parsley", "bokchoy"};
    String text[] = {"Important...","Task Panding...", "Water time"};
   // int img[] = {R.drawable.profilicon, R.drawable.profilicon, R.drawable.profilicon};

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


            setContentView(R.layout.activity_farmer_notification);
            lv = findViewById(R.id.farmer_notigication_list);
            ArrayList<FarmerNotificationModel> list = new ArrayList<>();


            if(list!=null) {

                FarmerNotificationAdapter adapter = new FarmerNotificationAdapter(getBaseContext(), list);
                lv.setAdapter(adapter);
            }
    }
}


