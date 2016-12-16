package com.example.termproject;

import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.util.Date;

/**
 * Created by lsn94 on 2016-11-26.
 */

public class FragmentEvent extends Fragment implements View.OnClickListener{

    Button btn_date;
    DatePicker dp;
    TableLayout memo_tl;
    String pick_date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.event, container, false);

        btn_date = (Button)v.findViewById(R.id.eventbuttondate);
        btn_date.setOnClickListener(this);

        memo_tl = (TableLayout) v.findViewById(R.id.memotl);

        long long_starttime = System.currentTimeMillis();
        Date date_starttime = new Date(long_starttime);
        SimpleDateFormat sdf_starttime = new SimpleDateFormat("yyyy년MM월dd");
        pick_date = sdf_starttime.format(date_starttime);

        btn_date.setText("날짜선택");

        return v;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.eventbuttondate:
                AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
                LinearLayout ll = new LinearLayout(v.getContext());
                dp = new DatePicker(v.getContext());
                dp.init(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), new DatePicker.OnDateChangedListener(){
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        pick_date = String.format("%d년%d월%d",year, monthOfYear+1, dayOfMonth);
                    }
                });
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
                                btn_date.setText(pick_date+"일");
                                MEMODB memodb = new MEMODB(getActivity());
                                memodb.showMemo(memo_tl, pick_date);
                            }
                        });
                AlertDialog ad = adb.create();
                ad.show();
                break;
        }
    }
}
