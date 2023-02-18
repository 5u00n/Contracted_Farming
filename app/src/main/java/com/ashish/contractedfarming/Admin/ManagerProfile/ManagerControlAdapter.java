package com.ashish.contractedfarming.Admin.ManagerProfile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ManagerControlAdapter extends FragmentPagerAdapter {

    int totaltabs =0;
    public ManagerControlAdapter(@NonNull FragmentManager fm, int totaltabs) {
        super(fm);
        this.totaltabs= totaltabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentAdminNewManager();
            case 1:
                return new FragmentAdminControlManager();
            case 2:
                return new FragmentAdminControlRejectedManager();
            case 3:
                return new FragmentAdminManagerConnect();
        }

        return null;
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
