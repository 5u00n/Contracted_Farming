package com.ashish.contractedfarming.Admin.ManagerProfile.Profile;

import com.google.firebase.database.DataSnapshot;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ManagerProfileAdapter extends FragmentPagerAdapter {
 DataSnapshot snapshot;
    int totaltabs;
    public ManagerProfileAdapter(@NonNull FragmentManager fm, int totaltabs, DataSnapshot snapshot) {
        super(fm);
        this.totaltabs=totaltabs;
        this.snapshot = snapshot;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(totaltabs==3){
            switch (position){
                case 1:
                    return new FragmentMangerProfileInfo(snapshot);
                case 2:
                    return new FragmentManagerProfileIdentity(snapshot);
                case 0:
                    return new FragmentManagerProfileFarmer(snapshot);
        }}else{
        switch (position){
            case 0:
                return new FragmentMangerProfileInfo(snapshot);
            case 1:
                return new FragmentManagerProfileIdentity(snapshot);

        }}

        return null;
    }

    @Override
    public int getCount() {
       return totaltabs;
    }
}
