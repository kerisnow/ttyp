package com.ttyp.tiantao.ttmb.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mlist;


    public MyFragmentAdapter(FragmentManager fm,List<Fragment> mlist) {
        super(fm);
        this.mlist = mlist;
    }

    @Override
    public Fragment getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
}
