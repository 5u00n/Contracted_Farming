package com.ashish.contractedfarming.Admin.Notification;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdminNotificationAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

 
    public AdminNotificationAdapter(@NonNull FragmentManager fm,int totalTabstabs) {
        super(fm);
        Context Context = context;
        this.totalTabs = totalTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

            switch(position){
                case 0:
                    return new AdminFarmerNotificationFragment();
                case 1:
                    return new AdminAgentNotificationFragment();

            }
        return null;
    }

    @Override
    public int getCount() {

        return totalTabs;
    }

}
