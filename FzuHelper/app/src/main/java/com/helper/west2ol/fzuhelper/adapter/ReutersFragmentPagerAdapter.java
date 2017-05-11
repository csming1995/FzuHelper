package com.helper.west2ol.fzuhelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by deng on 2016/11/3.
 */

public class ReutersFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> list;
    private String[] tabNames;

    public ReutersFragmentPagerAdapter(FragmentManager fm , ArrayList<Fragment> list , String[] tabNames){
        super(fm);
        this.list = list;
        this.tabNames = tabNames;
    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

}
