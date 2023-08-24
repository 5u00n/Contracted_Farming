package com.ashish.contractedfarming.Manager.Dashboard;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ManagerDashboardAdapter extends FragmentStatePagerAdapter {
    int totaltabs=0;
    public ManagerDashboardAdapter(@NonNull FragmentManager fm , int totaltabs) {
        super(fm);
        this.totaltabs=totaltabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentManagerDashboard();
            case 1:
                return new FragmentManagerTasks();
            case 2:
                return new FragmentManagerRequests();
            case 3:
                return new FragmentManagerConnections();
            case 4 :
                return new FragmentManagerRequirments();
            case 5:
                return new FragmentManagerMyArea();
            default:
                return new FragmentManagerDashboard();
        }
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
