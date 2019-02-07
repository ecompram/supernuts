package com.parsjavid.supernuts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.parsjavid.supernuts.fragments.UserProfileInfoFragment;
import com.parsjavid.supernuts.fragments.UserProfileProductsFragment;

public class UserProfilePagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public UserProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public UserProfilePagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                UserProfileInfoFragment userProfileInfoFragment = new UserProfileInfoFragment();
                return userProfileInfoFragment;
            case 1:
                UserProfileProductsFragment userProfileProductsFragment = new UserProfileProductsFragment();
                return userProfileProductsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
