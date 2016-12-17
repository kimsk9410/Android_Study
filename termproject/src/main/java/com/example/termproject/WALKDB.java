package com.example.termproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;

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
            if(str_date.equals(c.getString(0))){
                sum_walk += c.getInt(1);
                sum_calories += c.getDouble(2);
            }
        }
        sum_calories = Double.parseDouble(String.format("%.2f",sum_calories));
        tv_walk.setText(sum_walk+"걸음");
        tv_calories.setText(sum_calories+"칼로리");
    }

    void checkPlan(String str_start, String str_end, TextView tv_plan){
        Cursor c = db.rawQuery("select * from walktable;", null);
        str_start += "일";
        str_end += "일";
        int sum_walk = 0;
        int sum_count = 0;
        while(c.moveToNext()){
            StringTokenizer tokenizer = new StringTokenizer(c.getString(0));
            String walk_day_year = tokenizer.nextToken("년|월|일");
            String walk_day_month = tokenizer.nextToken("년|월|일");
            String walk_day_date = tokenizer.nextToken("년|월|일");
            tokenizer = new StringTokenizer(str_start);
            String walk_start_year = tokenizer.nextToken("년|월|일");
            String walk_start_month = tokenizer.nextToken("년|월|일");
            String walk_start_date = tokenizer.nextToken("년|월|일");
            tokenizer = new StringTokenizer(str_end);
            String walk_end_year = tokenizer.nextToken("년|월|일");
            String walk_end_month = tokenizer.nextToken("년|월|일");
            String walk_end_date = tokenizer.nextToken("년|월|일");

            if(walk_start_year.equals(walk_end_year)){
                if(walk_start_month.equals(walk_end_month)){
                    if(walk_day_year.equals(walk_start_year) && walk_day_month.equals(walk_start_month)){
                        if(Integer.parseInt(walk_day_date) >= Integer.parseInt(walk_start_date) && Integer.parseInt(walk_day_date) <= Integer.parseInt(walk_end_date)){
                            sum_walk += c.getInt(1);
                            sum_count++;
                        }
                    }
                }
                else{
                    if(walk_day_year.equals(walk_start_year) && walk_day_month.equals(walk_start_month)){
                        if(Integer.parseInt(walk_day_date) >= Integer.parseInt(walk_start_date) && Integer.parseInt(walk_day_date) <= 31){
                            sum_walk += c.getInt(1);
                            sum_count++;
                        }
                    }else if(walk_day_year.equals(walk_start_year) && walk_day_month.equals(walk_end_month)){
                        if(Integer.parseInt(walk_day_date) >= 1 && Integer.parseInt(walk_day_date) <= Integer.parseInt(walk_end_date)){
                            sum_walk += c.getInt(1);
                            sum_count++;
                        }
                    }
                }
            }else{
                if(walk_day_year.equals(walk_start_year) && walk_day_month.equals(walk_start_month)){
                    if(Integer.parseInt(walk_day_date) >= Integer.parseInt(walk_start_date) && Integer.parseInt(walk_day_date) <= 31){
                        sum_walk += c.getInt(1);
                        sum_count++;
                    }
                }else if(walk_day_year.equals(walk_end_year) && walk_day_month.equals(walk_end_month)){
                    if(Integer.parseInt(walk_day_date) >= 1 && Integer.parseInt(walk_day_date) <= Integer.parseInt(walk_end_date)){
                        sum_walk += c.getInt(1);
                        sum_count++;
                    }
                }
            }

        }
        tv_plan.append("\n달성 : "+sum_count+"회 "+sum_walk+"걸음");
    }
}
