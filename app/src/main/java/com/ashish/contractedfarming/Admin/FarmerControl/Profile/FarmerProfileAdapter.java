package com.ashish.contractedfarming.Admin.FarmerControl.Profile;

import com.google.firebase.database.DataSnapshot;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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


        if (totaltabs == 4) {
            switch (position) {
                case 0:
                    return new PlotProfileFragment(ds);
                case 1:
                    return new FarmerProfileFragment(ds);
                case 2:
                    return new ProfileDocument1Fragment(ds);
                case 3:
                    return new ProfileDocument2Fragment(ds);
            }
        } else {
            switch (position) {
                case 0:
                    return new FarmerProfileFragment(ds);
                case 1:
                    return new ProfileDocument1Fragment(ds);
                case 2:
                    return new ProfileDocument2Fragment(ds);
            }


        }


        return null;
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
