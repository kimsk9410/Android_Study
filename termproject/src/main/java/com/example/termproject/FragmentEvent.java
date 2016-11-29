package com.example.termproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

/**
 * Created by lsn94 on 2016-11-26.
 */

public class FragmentEvent extends Fragment implements View.OnClickListener{

    Button btn_date;
    DatePicker dp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.event, container, false);

        btn_date = (Button)v.findViewById(R.id.eventbuttondate);
        btn_date.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.eventbuttondate:
                AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
                LinearLayout ll = new LinearLayout(v.getContext());
                dp = new DatePicker(v.getContext());
                ll.addView(dp);
                ll.setPadding(20,20,0,0);

                adb
                        .setView(ll)
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
