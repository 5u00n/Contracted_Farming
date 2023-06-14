package com.ashish.contractedfarming.Farmer.Dashboard;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ashish.contractedfarming.Admin.Dashboard.Farmer.FarmerFragment;
import com.ashish.contractedfarming.Admin.Dashboard.Home.HomeFragment;
import com.ashish.contractedfarming.Admin.Dashboard.Manager.ManagerFragment;
import com.ashish.contractedfarming.Admin.Dashboard.News.NewsFragment;
import com.ashish.contractedfarming.Admin.Dashboard.Plant.PlantFragment;
import com.ashish.contractedfarming.Farmer.Dashboard.Home.FarmerHomeFragment;
import com.ashish.contractedfarming.Farmer.Dashboard.MyFarm.MyFarmFragment;
import com.ashish.contractedfarming.Farmer.Dashboard.MyPlants.MyPlantsFragment;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.StoryFragment;

public class FarmerDashboardAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public FarmerDashboardAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new FarmerHomeFragment();
            case 1:
                return new StoryFragment();
            case 2:
                return new PlantFragment();
            case 3:
                return new MyFarmFragment();
            case 4:
                return new MyPlantsFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
