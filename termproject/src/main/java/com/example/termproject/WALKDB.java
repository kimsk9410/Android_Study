package com.example.termproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by lsn94 on 2016-12-17.
 */

public class WALKDB{

    private WALKDBHelper helper;
    String dbName = "walkdb.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "SQLite"; // Log 에 사용할 tag
    Context mContext;


    WALKDB(Context mContext){
        helper = new WALKDBHelper(mContext, dbName, null, dbVersion);
        this.mContext = mContext;

        try{
            db = helper.getWritableDatabase();
        }catch(SQLiteException sqle){
            Log.e(tag, "데이터베이스를 얻어올 수 없음");
        }
    }

    void insertWalk(String walkday, int count){
        double calories = count;
        calories /= 30.0;
        db.execSQL("insert into walktable (walkday,walkcount,calories) values('" + walkday + "'," + count + "," + calories + ");");
        Log.e(tag, "insert 성공");
    }

    void showWalk(String str_date, TextView tv_walk, TextView tv_calories){
        Cursor c = db.rawQuery("select * from walktable;", null);
        int sum_walk = 0;
        double sum_calories = 0;
        while(c.moveToNext()){
            Log.e("Tag","0:"+c.getString(0)+" / 1:"+c.getInt(1)+" / 2:"+c.getDouble(2));
            if(str_date.equals(c.getString(0))){
                sum_walk += c.getInt(1);
                sum_calories += c.getDouble(2);
            }
        }
        sum_calories = Double.parseDouble(String.format("%.2f",sum_calories));
        tv_walk.setText(sum_walk+"걸음");
        tv_calories.setText(sum_calories+"칼로리");
    }

}
