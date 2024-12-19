package com.example.dubaott;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private static final int NUMBER_OF_PAGES = 3;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new OnbroadingFragment1();
            case 1:
                return new OnbroadingFragment2();
            case 2:
                return new OnbroadingFragment3();
            default:
                return new OnbroadingFragment1();
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }
}
