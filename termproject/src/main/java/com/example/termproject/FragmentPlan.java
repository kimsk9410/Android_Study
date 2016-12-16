package com.example.termproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * Created by lsn94 on 2016-11-26.
 */

public class FragmentPlan extends Fragment implements View.OnClickListener{

    TextView tv_date, tv_week, tv_month;
    TableLayout plan_tl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.plan, container, false);

        tv_date = (TextView)v.findViewById(R.id.textViewdate);
        tv_week = (TextView)v.findViewById(R.id.textViewweek);
        tv_month = (TextView)v.findViewById(R.id.textViewmonth);
        tv_date.setOnClickListener(this);
        tv_week.setOnClickListener(this);
        tv_month.setOnClickListener(this);
        plan_tl = (TableLayout)v.findViewById(R.id.plantl);

        return v;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.textViewdate:
                tv_date.setBackgroundResource(R.drawable.roundborder_55555555_fill);
                tv_week.setBackgroundResource(R.drawable.roundborder_55555555);
                tv_month.setBackgroundResource(R.drawable.roundborder_55555555);
                PLANDB plandb = new PLANDB(getActivity());
                plandb.showPlan(plan_tl, 1);
                break;
            case R.id.textViewweek:
                tv_date.setBackgroundResource(R.drawable.roundborder_55555555);
                tv_week.setBackgroundResource(R.drawable.roundborder_55555555_fill);
                tv_month.setBackgroundResource(R.drawable.roundborder_55555555);
                break;
            case R.id.textViewmonth:
                tv_date.setBackgroundResource(R.drawable.roundborder_55555555);
                tv_week.setBackgroundResource(R.drawable.roundborder_55555555);
                tv_month.setBackgroundResource(R.drawable.roundborder_55555555_fill);
                break;
        }
    }
}
