package com.ashish.contractedfarming.Admin.Dashboard.Farmer.FarmerProfile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FarmerControlAdapter extends FragmentStatePagerAdapter
{

    int totaltabs=0;
    public FarmerControlAdapter(@NonNull FragmentManager fm, int totaltabs) {
        super(fm);
        this.totaltabs=totaltabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AdminNewfarmerControlFragment();
            case 1:
                return new AdminFarmerListControlFragment();
            case 2:
                return new AdminRejectedFarmerControlFragment();
            case 3:
                return new AdminConnectionsFarmerFragment();
            default:
                return new AdminNewfarmerControlFragment();
        }
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
