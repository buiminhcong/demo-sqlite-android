package com.bmcong2k.sqlite.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bmcong2k.sqlite.fragment.FragmentHistory;
import com.bmcong2k.sqlite.fragment.FragmentHome;
import com.bmcong2k.sqlite.fragment.FragmentSearch;

public class ViewPagerAdaper extends FragmentStatePagerAdapter {


    public ViewPagerAdaper(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // vi tri fragment

        switch (position){
            case 0 :
               return new  FragmentHome();
            case 1 :
               return new FragmentHistory();
            case 2:
                return new FragmentSearch();
            default:
               return new FragmentHome();
        }
    }

    @Override
    public int getCount() {
        // tra ve 3 fragment
        return 3;
    }
}
