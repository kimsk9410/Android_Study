package com.example.termproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Paint;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.StringTokenizer;

/**
 * Created by lsn94 on 2016-12-03.
 */

public class MEMODB {

    private MEMODBHelper helper;
    String dbName = "memodb.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "SQLite"; // Log 에 사용할 tag
    Context mContext;

    MEMODB(Context mContext){
        helper = new MEMODBHelper(mContext, dbName, null, dbVersion);
        this.mContext = mContext;

        try{
            db = helper.getWritableDatabase();
        }catch(SQLiteException sqle){
            Log.e(tag, "데이터베이스를 얻어올 수 없음");
        }
    }

    void insertMemo(String type, String starttime, String endtime, String memo){
        db.execSQL("insert into memotable (starttime,endtime,memo,type) values('" + starttime + "','" + endtime + "','" + memo + "','" + type + "');");
        Log.e(tag, "insert 성공");
    }

    void showMemo(TableLayout tl, String str_date){
        Cursor c = db.rawQuery("select * from memotable;", null);
        tl.removeAllViews();
        while(c.moveToNext()){
            String starttime = c.getString(0);
            StringTokenizer tokens = new StringTokenizer(starttime);
            starttime = tokens.nextToken("일");
            if(starttime.equals(str_date)){
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

            }
        }
    }

}
