package com.example.termproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by lsn94 on 2016-11-26.
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
                FragmentTask fragmentTask = new FragmentTask();
                return fragmentTask;
            case 1:
                FragmentLog fragmentLog = new FragmentLog();
                return fragmentLog;
            case 2:
                FragmentEvent fragmentEvent = new FragmentEvent();
                return fragmentEvent;
            case 3:
                FragmentWalk fragmentWalk = new FragmentWalk();
                return fragmentWalk;
            case 4:
                FragmentPlan fragmentPlan = new FragmentPlan();
                return fragmentPlan;
            case 5:
                FragmentReport fragmentReport = new FragmentReport();
                return fragmentReport;
            default:
                return null;
        }
    }

    @Override
    public int getCount(){

        return tabCount;
    }
}
