package com.idpz.bazarayesh.Adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

import com.idpz.bazarayesh.AdvertisementManagmentFragment;
import com.idpz.bazarayesh.SubProfileActivity;
import com.idpz.bazarayesh.SubProfileFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public ViewPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
               SubProfileFragment subProfileFragment = new SubProfileFragment();
               return subProfileFragment;
            case 1:
                AdvertisementManagmentFragment advertisementManagmentFragment = new AdvertisementManagmentFragment();
                return advertisementManagmentFragment;

            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}