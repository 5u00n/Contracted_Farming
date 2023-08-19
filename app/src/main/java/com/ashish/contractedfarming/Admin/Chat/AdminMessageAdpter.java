package com.ashish.contractedfarming.Admin.Chat;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdminMessageAdpter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;


    public AdminMessageAdpter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        Context Context = context;
        this.totalTabs = totalTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                return new AdminChatListFragment();
            case 1:
                return new AdminChatStatusFragment();

        }
        return null;
    }

    @Override
    public int getCount() {

        return totalTabs;
    }

}
