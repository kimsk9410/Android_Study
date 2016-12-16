package com.example.termproject;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by lsn94 on 2016-12-01.
 */

public class TASKDB {

    private TASKDBHelper helper;
    String dbName = "taskdb.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "SQLite"; // Log 에 사용할 tag
    Context mContext;

    TASKDB(Context mContext){
        helper = new TASKDBHelper(mContext, dbName, null, dbVersion);
        this.mContext = mContext;

        try{
            db = helper.getWritableDatabase();
        }catch(SQLiteException sqle){
            Log.e(tag, "데이터베이스를 얻어올 수 없음");
        }
    }

    void insertTask(String type, String starttime, String endtime, int runtime){
        db.execSQL("insert into tasktable (starttime,endtime,runtime,type) values('" + starttime + "','" + endtime + "'," + runtime + ",'" + type + "');");
        Log.e(tag, "insert 성공");
    }

    void showLog(TableLayout tl, String str_date){
        Cursor c = db.rawQuery("select * from tasktable;", null);
        tl.removeAllViews();
        while(c.moveToNext()){
            String start_time = c.getString(0);
            final String starttime = c.getString(0);
            final String endtime = c.getString(1);
            final String type = c.getString(3);
            StringTokenizer tokens = new StringTokenizer(start_time);
            start_time = tokens.nextToken("일");
            if(start_time.equals(str_date)){
                LinearLayout ll = new LinearLayout(mContext);
                LinearLayout ll_space = new LinearLayout(mContext);
                TextView tv_name = new TextView(mContext);
                TextView tv_time = new TextView(mContext);

                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setPadding(50,50,50,50);
                ll.setBackgroundResource(R.drawable.roundborder_55555555);
                ll_space.setMinimumHeight(50);
                tv_name.setText(c.getString(3));
                tv_name.setTextSize(20);
                tv_name.setPaintFlags(tv_name.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                tv_time.setText(c.getString(2));
                ll.addView(tv_name);
                ll.addView(tv_time);
                tl.addView(ll);
                tl.addView(ll_space);

                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
                        final EditText et = new EditText(mContext);

                        adb
                                .setTitle("메모")
                                .setView(et)
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        MEMODB memodb = new MEMODB(mContext);
                                        memodb.insertMemo(type, starttime, endtime, et.getText().toString());
                                    }
                                });
                        AlertDialog ad = adb.create();
                        ad.show();
                    }
                });

            }
        }
    }

    boolean isExist(double lati_in, double longi_in){
        Cursor c = db.rawQuery("select * from gpstable;", null);
        while(c.moveToNext()){
            double lati = c.getDouble(1);
            double longi = c.getDouble(2);
            if(lati == lati_in && longi == longi_in){
                return true;
            }
        }
        return false;
    }

    void statsDB(ArrayList<Entry> al_e, ArrayList<String> al_s){
        Cursor c = db.rawQuery("select * from gpstable;", null);
        int type_num = 5;
        int[] count_arr = new int[type_num];
        while(c.moveToNext()){
            String type = c.getString(4);
            switch (type){
                case "분류":
                    break;
                case "학교":
                    count_arr[0]++;
                    break;
                case "식사":
                    count_arr[1]++;
                    break;
                case "카페":
                    count_arr[2]++;
                    break;
                case "취미":
                    count_arr[3]++;
                    break;
                case "운동":
                    count_arr[4]++;
                    break;
            }
        }
        al_s.add("학교");
        al_s.add("식사");
        al_s.add("카페");
        al_s.add("취미");
        al_s.add("운동");
        for(int i = 0; i < type_num; i++){
            al_e.add(new Entry((float)count_arr[i],i));
        }
    }

}
