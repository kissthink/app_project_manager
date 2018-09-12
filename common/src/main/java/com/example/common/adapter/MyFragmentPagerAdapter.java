package com.example.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zhangyinlei on 2018/3/20 20:24
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    //存储所有的fragment
    private List<Fragment> list;
    private List<String> titles;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> titles) {
        super(fm);
        // TODO Auto-generated constructor stub
        this.list = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

}
