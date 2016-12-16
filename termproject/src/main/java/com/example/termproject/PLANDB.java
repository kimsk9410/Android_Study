package com.example.termproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by lsn94 on 2016-12-16.
 */

public class PLANDB {

    private PLANDBHelper helper;
    String dbName = "plandb.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "SQLite"; // Log 에 사용할 tag
    Context mContext;

    PLANDB(Context mContext){
        helper = new PLANDBHelper(mContext, dbName, null, dbVersion);
        this.mContext = mContext;

        try{
            db = helper.getWritableDatabase();
        }catch(SQLiteException sqle){
            Log.e(tag, "데이터베이스를 얻어올 수 없음");
        }
    }

    void insertPlan(String type, int planday, int plancount, int plantime){
        String starttime;
        String endtime;

        long long_starttime = System.currentTimeMillis();
        Date date_starttime = new Date(long_starttime);
        SimpleDateFormat sdf_starttime = new SimpleDateFormat("yyyy년MM월dd일00시00분00초");
        starttime = sdf_starttime.format(date_starttime);

        if(planday == 1){
            long long_endtime = System.currentTimeMillis() + 1000*3600*24 - 1;
            Date date_endtime = new Date(long_endtime);
            SimpleDateFormat sdf_endtime = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
            endtime = sdf_endtime.format(date_endtime);
        }else if(planday == 7){
            long long_endtime = System.currentTimeMillis() + 1000*3600*24*7 - 1;
            Date date_endtime = new Date(long_endtime);
            SimpleDateFormat sdf_endtime = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
            endtime = sdf_endtime.format(date_endtime);
        }else{
            long long_endtime = System.currentTimeMillis() + 1000*3600*24*30 - 1;
            Date date_endtime = new Date(long_endtime);
            SimpleDateFormat sdf_endtime = new SimpleDateFormat("yyyy년MM월dd일HH시mm분ss초");
            endtime = sdf_endtime.format(date_endtime);
        }

        db.execSQL("insert into plantable (starttime,endtime,type,planday,plancount,plantime) values('" + starttime + "','" + endtime + "','" + type + "'," + planday + ", " + plancount + ", " + plantime + ");");
        Log.e(tag, "insert 성공");
    }

    void showPlan(TableLayout tl, int pd){
        Cursor c = db.rawQuery("select * from plantable;", null);
        tl.removeAllViews();
        while(c.moveToNext()){
            int planday = c.getInt(3);
            if(planday == pd){
                TableRow tr = new TableRow(mContext);
                LinearLayout ll = new LinearLayout(mContext);
                TextView tv_term = new TextView(mContext);
                TextView tv_plan = new TextView(mContext);

                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setPadding(50,50,50,50);
                ll.setBackgroundResource(R.drawable.roundborder_55555555);
                tv_term.setText("기간~기간");
                tv_term.setTextSize(20);
                tv_term.setPaintFlags(tv_term.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                if(c.getString(2).equals("걷기")) {
                    tv_plan.setText(c.getString(2) + " : " + c.getInt(4) + "회" + c.getInt(5) + "걸음");
                }else{
                    tv_plan.setText(c.getString(2) + " : " + c.getInt(4) + "회" + c.getInt(5) + "시간");
                }
                ll.addView(tv_term);
                ll.addView(tv_plan);
                tr.addView(ll);
                tl.addView(tr);

            }
        }
    }
}
