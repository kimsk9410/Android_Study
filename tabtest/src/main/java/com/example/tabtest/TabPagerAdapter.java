package com.example.tabtest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by lsn94 on 2016-11-24.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount){

        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position){

        switch(position){
            case 0:
                TabFragment1 tabFragment1 = new TabFragment1();
                return tabFragment1;
            case 1:
                TabFragment2 tabFragment2 = new TabFragment2();
                return tabFragment2;
            case 2:
                TabFragment3 tabFragment3 = new TabFragment3();
                return tabFragment3;
            case 3:
                TabFragment4 tabFragment4 = new TabFragment4();
                return tabFragment4;
            case 4:
                TabFragment5 tabFragment5 = new TabFragment5();
                return tabFragment5;
            case 5:
                TabFragment6 tabFragment6 = new TabFragment6();
                return tabFragment6;
            default:
                return null;
        }
    }

    @Override
    public int getCount(){

        return tabCount;
    }
}
