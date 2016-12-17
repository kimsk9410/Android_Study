package com.example.termproject;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.SystemClock;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by lsn94 on 2016-11-26.
 */

public class FragmentTask extends Fragment implements View.OnClickListener{

    final int dip = TypedValue.COMPLEX_UNIT_DIP;

    Button bt_plan, bt_startwalk, bt_startstudy, bt_starteat, bt_startexercise, bt_startmeeting;
    Chronometer cm_walk, cm_study, cm_eat, cm_exercise, cm_meeting;
    String str_task="";
    String str_starttime, str_endtime;
    int int_runtime;
    int planday=0, plancount=0, plantime=0;
    double start_lati, start_longi, end_lati, end_longi;
    final String[] task_arr = {"걷기", "공부", "식사", "운동", "회의"};
    ArrayList<Button> al_btstart = new ArrayList<Button>();
    ArrayList<Chronometer> al_cm = new ArrayList<Chronometer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.task, container, false);

        bt_plan = (Button)v.findViewById(R.id.buttonPlan);
        bt_plan.setOnClickListener(this);

        bt_startwalk = (Button)v.findViewById(R.id.start_walk);
        bt_startstudy = (Button)v.findViewById(R.id.start_study);
        bt_starteat = (Button)v.findViewById(R.id.start_eat);
        bt_startexercise = (Button)v.findViewById(R.id.start_exercise);
        bt_startmeeting = (Button)v.findViewById(R.id.start_meeting);

        cm_walk = (Chronometer)v.findViewById(R.id.cm_walk);
        cm_study = (Chronometer)v.findViewById(R.id.cm_study);
        cm_eat = (Chronometer)v.findViewById(R.id.cm_eat);
        cm_exercise = (Chronometer)v.findViewById(R.id.cm_exercise);
        cm_meeting = (Chronometer)v.findViewById(R.id.cm_meeting);

        al_btstart.add(bt_startwalk); al_cm.add(cm_walk);
        al_btstart.add(bt_startstudy); al_cm.add(cm_study);
        al_btstart.add(bt_starteat); al_cm.add(cm_eat);
        al_btstart.add(bt_startexercise); al_cm.add(cm_exercise);
        al_btstart.add(bt_startmeeting); al_cm.add(cm_meeting);

        for(int i = 0; i < al_btstart.size(); i++){
            al_btstart.get(i).setOnClickListener(this);
        }

        return v;
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.buttonPlan:
                AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
                TableLayout tl = new TableLayout(getActivity());
                LinearLayout ll = new LinearLayout(getActivity());
                LinearLayout ll1 = new LinearLayout(getActivity());
                LinearLayout ll2 = new LinearLayout(getActivity());
                final TextView tv1 = new TextView(getActivity());
                final TextView tv2 = new TextView(getActivity());
                Spinner spinner_task = new Spinner(getActivity());
                final EditText et1 = new EditText(getActivity());
                final EditText et2 = new EditText(getActivity());

                final TextView tv_date = new TextView(getActivity());
                final TextView tv_week = new TextView(getActivity());
                final TextView tv_month = new TextView(getActivity());
                final TextView tv_space = new TextView(getActivity());
                final TextView tv_space2 = new TextView(getActivity());
                final TextView tv_space3 = new TextView(getActivity());

                tv_date.setText("하루");tv_date.setBackgroundResource(R.drawable.roundborder_55555555);tv_date.setTextSize(20);tv_date.setPadding(20,20,20,20);
                tv_week.setText("한주");tv_week.setBackgroundResource(R.drawable.roundborder_55555555);tv_week.setTextSize(20);tv_week.setPadding(20,20,20,20);
                tv_month.setText("한달");tv_month.setBackgroundResource(R.drawable.roundborder_55555555);tv_month.setTextSize(20);tv_month.setPadding(20,20,20,20);
                tv_space.setText("");tv_space.setWidth(20);tv_space2.setText("");tv_space2.setWidth(20);tv_space3.setText("");tv_space3.setWidth(20);
                tv1.setText("회");

                tv_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_date.setBackgroundResource(R.drawable.roundborder_55555555_fill);
                        tv_week.setBackgroundResource(R.drawable.roundborder_55555555);
                        tv_month.setBackgroundResource(R.drawable.roundborder_55555555);
                        planday=1;
                    }
                });
                tv_week.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_date.setBackgroundResource(R.drawable.roundborder_55555555);
                        tv_week.setBackgroundResource(R.drawable.roundborder_55555555_fill);
                        tv_month.setBackgroundResource(R.drawable.roundborder_55555555);
                        planday=7;
                    }
                });
                tv_month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_date.setBackgroundResource(R.drawable.roundborder_55555555);
                        tv_week.setBackgroundResource(R.drawable.roundborder_55555555);
                        tv_month.setBackgroundResource(R.drawable.roundborder_55555555_fill);
                        planday=30;
                    }
                });

                tl.setPadding(100, 100, 100, 100);

                et1.setInputType(InputType.TYPE_CLASS_NUMBER);
                et1.setEms(3);
                et2.setInputType(InputType.TYPE_CLASS_NUMBER);
                et2.setEms(3);

                ArrayAdapter memoAdapter = new ArrayAdapter(v.getContext(), R.layout.spinner_dropdown_item15, task_arr);
                memoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_task.setAdapter(memoAdapter);
                spinner_task.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        str_task = parent.getItemAtPosition(position).toString();
                        if(str_task.equals("걷기")){
                            tv2.setText("걸음");
                        }
                        else{
                            tv2.setText("시간");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinner_task.setPadding(20,20,20,20);

                ll.addView(tv_date);ll.addView(tv_space);ll.addView(tv_week);ll.addView(tv_space2);ll.addView(tv_month);
                ll.setPadding(0,0,0,50);
                ll1.addView(spinner_task);
                ll2.addView(et1);
                ll2.addView(tv1);
                ll2.addView(tv_space3);
                ll2.addView(et2);
                ll2.addView(tv2);

                tl.addView(ll);
                tl.addView(ll1);
                tl.addView(ll2);


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
                                plancount = Integer.parseInt(et1.getText().toString());
                                plantime = Integer.parseInt(et2.getText().toString());
                                PLANDB plandb = new PLANDB(getActivity());
                                plandb.insertPlan(str_task, planday, plancount, plantime);
                            }
                        });
                AlertDialog ad = adb.create();
                ad.show();
                break;
            case R.id.start_walk:
                int index = 0;
                if(al_btstart.get(index).getText().equals("시작")) {
                    new TedPermission(getActivity())
                            .setPermissionListener(permissionlistener)
                            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                            .setPermissions(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
                            .check();

                    long long_starttime = System.currentTimeMillis();
                    Date date_starttime = new Date(long_starttime);
                    SimpleDateFormat sdf_starttime = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
                    str_starttime = sdf_starttime.format(date_starttime);
                    Log.d("시작시간",str_starttime);

                    al_btstart.get(index).setText("종료");
                    al_btstart.get(index).setTextColor(0xffff0000);
                    al_cm.get(index).setBase(SystemClock.elapsedRealtime());
                    al_cm.get(index).start();
                    for(int i = 0; i < al_btstart.size(); i++){
                        if(i != index){
                            al_btstart.get(i).setText("대기");
                            al_btstart.get(i).setTextColor(0xffffee00);
                        }
                    }

                    UseGPS gps = new UseGPS(getContext());
                    gps.getGPSInfo();
                    start_lati = gps.lati;
                    start_longi = gps.longi;
                }
                else if(al_btstart.get(index).getText().equals("대기")){
                    Toast.makeText(getActivity(), "다른 작업이 진행중입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    long long_endtime = System.currentTimeMillis();
                    Date date_endtime = new Date(long_endtime);
                    SimpleDateFormat sdf_endtime = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
                    str_endtime = sdf_endtime.format(date_endtime);

                    al_cm.get(index).stop();
                    long long_runtime = SystemClock.elapsedRealtime() - al_cm.get(index).getBase();
                    int_runtime = (int)long_runtime/1000;

                    for(int i = 0; i < al_btstart.size(); i++){
                        al_btstart.get(i).setText("시작");
                        al_btstart.get(i).setTextColor(0xff00ff00);
                    }
                    TASKDB taskdb = new TASKDB(getActivity());
                    taskdb.insertTask(task_arr[index], str_starttime, str_endtime, int_runtime);

                    UseGPS gps = new UseGPS(getContext());
                    gps.getGPSInfo();
                    end_lati = gps.lati;
                    end_longi = gps.longi;
                    SimpleDateFormat sdf_walkday = new SimpleDateFormat("yyyy년MM월dd일");
                    String str_walkday = sdf_walkday.format(date_endtime);

                    GPSDB gpsdb = new GPSDB(getContext());
                    gpsdb.insertGPS(str_walkday, start_lati, start_longi, end_lati, end_longi);
                }
                break;
            case R.id.start_study:
                index = 1;
                if(al_btstart.get(index).getText().equals("시작")) {
                    long long_starttime = System.currentTimeMillis();
                    Date date_starttime = new Date(long_starttime);
                    SimpleDateFormat sdf_starttime = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
                    str_starttime = sdf_starttime.format(date_starttime);
                    Log.d("시작시간",str_starttime);

                    al_btstart.get(index).setText("종료");
                    al_btstart.get(index).setTextColor(0xffff0000);
                    al_cm.get(index).setBase(SystemClock.elapsedRealtime());
                    al_cm.get(index).start();
                    for(int i = 0; i < al_btstart.size(); i++){
                        if(i != index){
                            al_btstart.get(i).setText("대기");
                            al_btstart.get(i).setTextColor(0xffffee00);
                        }
                    }
                }
                else if(al_btstart.get(index).getText().equals("대기")){
                    Toast.makeText(getActivity(), "다른 작업이 진행중입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    long long_endtime = System.currentTimeMillis();
                    Date date_endtime = new Date(long_endtime);
                    SimpleDateFormat sdf_endtime = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
                    str_endtime = sdf_endtime.format(date_endtime);
                    Log.d("종료시간",str_endtime);

                    al_cm.get(index).stop();
                    long long_runtime = SystemClock.elapsedRealtime() - al_cm.get(index).getBase();
                    int_runtime = (int)long_runtime/1000;
                    Log.d("런타임", Integer.toString(int_runtime));

                    for(int i = 0; i < al_btstart.size(); i++){
                        al_btstart.get(i).setText("시작");
                        al_btstart.get(i).setTextColor(0xff00ff00);
                    }
                    TASKDB taskdb = new TASKDB(getActivity());
                    taskdb.insertTask(task_arr[index], str_starttime, str_endtime, int_runtime);
                }
                break;
            case R.id.start_eat:
                index = 2;
                if(al_btstart.get(index).getText().equals("시작")) {
                    long long_starttime = System.currentTimeMillis();
                    Date date_starttime = new Date(long_starttime);
                    SimpleDateFormat sdf_starttime = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
                    str_starttime = sdf_starttime.format(date_starttime);
                    Log.d("시작시간",str_starttime);

                    al_btstart.get(index).setText("종료");
                    al_btstart.get(index).setTextColor(0xffff0000);
                    al_cm.get(index).setBase(SystemClock.elapsedRealtime());
                    al_cm.get(index).start();
                    for(int i = 0; i < al_btstart.size(); i++){
                        if(i != index){
                            al_btstart.get(i).setText("대기");
                            al_btstart.get(i).setTextColor(0xffffee00);
                        }
                    }
                }
                else if(al_btstart.get(index).getText().equals("대기")){
                    Toast.makeText(getActivity(), "다른 작업이 진행중입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    long long_endtime = System.currentTimeMillis();
                    Date date_endtime = new Date(long_endtime);
                    SimpleDateFormat sdf_endtime = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
                    str_endtime = sdf_endtime.format(date_endtime);
                    Log.d("종료시간",str_endtime);

                    al_cm.get(index).stop();
                    long long_runtime = SystemClock.elapsedRealtime() - al_cm.get(index).getBase();
                    int_runtime = (int)long_runtime/1000;
                    Log.d("런타임", Integer.toString(int_runtime));

                    for(int i = 0; i < al_btstart.size(); i++){
                        al_btstart.get(i).setText("시작");
                        al_btstart.get(i).setTextColor(0xff00ff00);
                    }
                    TASKDB taskdb = new TASKDB(getActivity());
                    taskdb.insertTask(task_arr[index], str_starttime, str_endtime, int_runtime);
                }
                break;
            case R.id.start_exercise:
                index = 3;
                if(al_btstart.get(index).getText().equals("시작")) {
                    long long_starttime = System.currentTimeMillis();
                    Date date_starttime = new Date(long_starttime);
                    SimpleDateFormat sdf_starttime = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
                    str_starttime = sdf_starttime.format(date_starttime);
                    Log.d("시작시간",str_starttime);

                    al_btstart.get(index).setText("종료");
                    al_btstart.get(index).setTextColor(0xffff0000);
                    al_cm.get(index).setBase(SystemClock.elapsedRealtime());
                    al_cm.get(index).start();
                    for(int i = 0; i < al_btstart.size(); i++){
                        if(i != index){
                            al_btstart.get(i).setText("대기");
                            al_btstart.get(i).setTextColor(0xffffee00);
                        }
                    }
                }
                else if(al_btstart.get(index).getText().equals("대기")){
                    Toast.makeText(getActivity(), "다른 작업이 진행중입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    long long_endtime = System.currentTimeMillis();
                    Date date_endtime = new Date(long_endtime);
                    SimpleDateFormat sdf_endtime = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
                    str_endtime = sdf_endtime.format(date_endtime);
                    Log.d("종료시간",str_endtime);

                    al_cm.get(index).stop();
                    long long_runtime = SystemClock.elapsedRealtime() - al_cm.get(index).getBase();
                    int_runtime = (int)long_runtime/1000;
                    Log.d("런타임", Integer.toString(int_runtime));

                    for(int i = 0; i < al_btstart.size(); i++){
                        al_btstart.get(i).setText("시작");
                        al_btstart.get(i).setTextColor(0xff00ff00);
                    }
                    TASKDB taskdb = new TASKDB(getActivity());
                    taskdb.insertTask(task_arr[index], str_starttime, str_endtime, int_runtime);
                }
                break;
            case R.id.start_meeting:
                index = 4;
                if(al_btstart.get(index).getText().equals("시작")) {
                    long long_starttime = System.currentTimeMillis();
                    Date date_starttime = new Date(long_starttime);
                    SimpleDateFormat sdf_starttime = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
                    str_starttime = sdf_starttime.format(date_starttime);
                    Log.d("시작시간",str_starttime);

                    al_btstart.get(index).setText("종료");
                    al_btstart.get(index).setTextColor(0xffff0000);
                    al_cm.get(index).setBase(SystemClock.elapsedRealtime());
                    al_cm.get(index).start();
                    for(int i = 0; i < al_btstart.size(); i++){
                        if(i != index){
                            al_btstart.get(i).setText("대기");
                            al_btstart.get(i).setTextColor(0xffffee00);
                        }
                    }
                }
                else if(al_btstart.get(index).getText().equals("대기")){
                    Toast.makeText(getActivity(), "다른 작업이 진행중입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    long long_endtime = System.currentTimeMillis();
                    Date date_endtime = new Date(long_endtime);
                    SimpleDateFormat sdf_endtime = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
                    str_endtime = sdf_endtime.format(date_endtime);
                    Log.d("종료시간",str_endtime);

                    al_cm.get(index).stop();
                    long long_runtime = SystemClock.elapsedRealtime() - al_cm.get(index).getBase();
                    int_runtime = (int)long_runtime/1000;
                    Log.d("런타임", Integer.toString(int_runtime));

                    for(int i = 0; i < al_btstart.size(); i++){
                        al_btstart.get(i).setText("시작");
                        al_btstart.get(i).setTextColor(0xff00ff00);
                    }
                    TASKDB taskdb = new TASKDB(getActivity());
                    taskdb.insertTask(task_arr[index], str_starttime, str_endtime, int_runtime);
                }
                break;

        }

    }
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };
}
