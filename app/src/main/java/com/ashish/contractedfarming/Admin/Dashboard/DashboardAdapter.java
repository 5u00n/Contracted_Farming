package com.ashish.contractedfarming.Admin.Dashboard;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ashish.contractedfarming.Admin.Dashboard.Conference.AdminConferenceFragment;
import com.ashish.contractedfarming.Admin.Dashboard.Farmer.FarmerFragment;
import com.ashish.contractedfarming.Admin.Dashboard.Home.AdminHomeFragment;
import com.ashish.contractedfarming.Admin.Dashboard.Manager.ManagerFragment;
import com.ashish.contractedfarming.Admin.Dashboard.News.NewsFragment;
import com.ashish.contractedfarming.Admin.Dashboard.Plant.PlantFragment;
import com.ashish.contractedfarming.Admin.Dashboard.Requests.AdminRequestFragment;

public class DashboardAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public DashboardAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new AdminHomeFragment();
            case 1:
                return new NewsFragment();
            case 2:
                return new AdminConferenceFragment();
            case 3:
                return new AdminRequestFragment();
            case 4:
                return new PlantFragment();
            case 5:
                return new FarmerFragment();
            case 6:
                return new ManagerFragment();

        }
        return new AdminHomeFragment();
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
