package com.example.mylocationlogger01;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lsn94 on 2016-10-28.
 */

public class GPSDB {

    private DBHelper helper;
    String dbName = "gpsdb.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "SQLite"; // Log 에 사용할 tag
    Context mContext;
    LinearLayout ll;

    GPSDB(Context mContext, LinearLayout ll){
        helper = new DBHelper(mContext, dbName, null, dbVersion);
        this.mContext = mContext;
        this.ll = ll;

        try{
            db = helper.getWritableDatabase();
        }catch(SQLiteException sqle){
            Log.e(tag, "데이터베이스를 얻어올 수 없음");
        }
    }

    GPSDB(Context mContext){
        helper = new DBHelper(mContext, dbName, null, dbVersion);
        this.mContext = mContext;

        try{
            db = helper.getWritableDatabase();
        }catch(SQLiteException sqle){
            Log.e(tag, "데이터베이스를 얻어올 수 없음");
        }
    }

    void delete(int i) {
        db.execSQL("delete from gpstable where id=" + i + ";");
        Log.e(tag, "delete 완료");
    }

    void deleteall(){
        Cursor c = db.rawQuery("select * from gpstable;", null);
        while(c.moveToNext()) {
            int id = c.getInt(0);
            db.execSQL("delete from gpstable where id=" + id +";");
        }

        Log.e(tag,"delete 완료");
    }

    void update(int i, double lati, double longi) {
        db.execSQL("update gpstable set latitude=" + lati + " longitude=" + longi + " where id=" + i + ";");
        Log.e(tag, "update 완료");
    }

    void select() {
        Cursor c = db.rawQuery("select * from gpstable;", null);
        while(c.moveToNext()) {
            int id = c.getInt(0);
            double lati = c.getDouble(1);
            double longi = c.getDouble(2);
            Log.e(tag,"id:" + id + ", latitude:" + lati + ", longitude" + longi);
        }
    }

    void insert (double lati, double longi) {
        db.execSQL("insert into gpstable (latitude,longitude) values(" + lati + "," + longi +");");
        Log.e(tag, "insert 성공");
    }

    void show(){
        Cursor c = db.rawQuery("select * from gpstable;", null);
        ll.removeAllViews();
        while(c.moveToNext()) {
            int id = c.getInt(0);
            double lati = c.getDouble(1);
            double longi = c.getDouble(2);

            TextView tv = new TextView(mContext);
            tv.setText("순서:" + id + ", 위도:" + lati + ", 경도" + longi);
            ll.addView(tv);
        }
    }

}