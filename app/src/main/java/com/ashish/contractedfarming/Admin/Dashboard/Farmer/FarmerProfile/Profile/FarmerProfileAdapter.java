package com.ashish.contractedfarming.Admin.Dashboard.Farmer.FarmerProfile.Profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.firebase.database.DataSnapshot;

public class FarmerProfileAdapter extends FragmentPagerAdapter {


    int totaltabs = 0;
    DataSnapshot ds;

    public FarmerProfileAdapter(@NonNull FragmentManager fm, int totaltabs, DataSnapshot ds) {
        super(fm);
        this.totaltabs = totaltabs;
        this.ds = ds;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {


            switch (position) {

                case 0:
                    return new FarmerProfileFragment(ds);
                case 1:
                    return new PlotProfileFragment(ds);
                case 2:
                    return new ProfileIdentityFragment(ds);
               

        }


        return null;
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
