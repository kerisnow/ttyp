package com.ttyp.tiantao.ttmb.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.ttyp.tiantao.ttmb.activity.goods.GoodsListFragment;
import com.ttyp.tiantao.ttmb.entity.ClassifyMenuList;

import java.util.List;

public class GoodsListFragmentAdpter extends FragmentStatePagerAdapter {
    List<ClassifyMenuList> keyList;
    public GoodsListFragmentAdpter(FragmentManager fm,List<ClassifyMenuList> keyList) {
        super(fm);
        this.keyList = keyList;
    }

    @Override
    public Fragment getItem(int i) {
        return GoodsListFragment.getInstance(keyList.get(i).getUrl());
    }

    @Override
    public int getCount() {
        return keyList.size();
    }

    /**
     * 销毁对应位置上的object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup arg0, int arg1) {
        // TODO Auto-generated method stub
        return super.instantiateItem(arg0,arg1);
    }
}
