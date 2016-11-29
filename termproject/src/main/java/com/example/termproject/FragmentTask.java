package com.example.termproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * Created by lsn94 on 2016-11-26.
 */

public class FragmentTask extends Fragment implements View.OnClickListener{

    final int dip = TypedValue.COMPLEX_UNIT_DIP;

    Button bt_plan;
    String str_task="";
    Spinner spinner_task;
    final String[] task_arr = {"걷기", "공부", "식사", "운동", "회의"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.task, container, false);

        bt_plan = (Button)v.findViewById(R.id.buttonPlan);

        bt_plan.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.buttonPlan:
                AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
                TableLayout tl = new TableLayout(v.getContext());
                TableRow tr1 = new TableRow(v.getContext());
                TextView tv1 = new TextView(v.getContext());

                tl.setPadding(100, 100, 100, 100);

                tv1.setText("-분류 : ");
                tv1.setTextSize(20);

                spinner_task = new Spinner(v.getContext());
                ArrayAdapter memoAdapter = new ArrayAdapter(v.getContext(), R.layout.spinner_dropdown_item15, task_arr);
                memoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_task.setAdapter(memoAdapter);
                spinner_task.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        str_task = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinner_task.setBackgroundResource(R.drawable.roundborder_55555555);
                spinner_task.setPadding(20,20,20,20);

                tr1.addView(tv1);
                tr1.addView(spinner_task);
                tl.addView(tr1);

                adb
                        .setView(tl)
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog ad = adb.create();
                ad.show();
                break;
        }

    }

    public void setSpinner(Spinner spinner, String[] str_arr, View v) {

    }
}
