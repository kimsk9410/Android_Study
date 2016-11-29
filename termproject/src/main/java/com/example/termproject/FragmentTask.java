package com.example.termproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;


/**
 * Created by lsn94 on 2016-11-26.
 */

public class FragmentTask extends Fragment implements View.OnClickListener{

    Button bt_plan;
    DatePicker dp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.task, container, false);

        bt_plan = (Button)v.findViewById(R.id.buttonPlan);
        dp = new DatePicker(v.getContext());

        bt_plan.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.buttonPlan:
                AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
                adb
                        .setView(dp)
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("선택", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog ad = adb.create();
                ad.show();
                break;
        }

    }
}
