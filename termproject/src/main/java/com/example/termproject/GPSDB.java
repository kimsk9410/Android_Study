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

import com.google.android.gms.maps.GoogleMap;

import java.util.StringTokenizer;

/**
 * Created by lsn94 on 2016-12-16.
 */

public class GPSDB {

    private GPSDBHelper helper;
    String dbName = "gpsdb.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "SQLite"; // Log 에 사용할 tag
    Context mContext;

    GPSDB(Context mContext){
        helper = new GPSDBHelper(mContext, dbName, null, dbVersion);
        this.mContext = mContext;

        try{
            db = helper.getWritableDatabase();
        }catch(SQLiteException sqle){
            Log.e(tag, "데이터베이스를 얻어올 수 없음");
        }
    }

    void insertGPS(String walkday, double start_lati, double start_longi, double end_lati, double end_longi){
        db.execSQL("insert into gpstable (walkday,start_lati,start_longi,end_lati,end_longi) values('" + walkday + "'," + start_lati + "," + start_longi + "," + end_lati + "," + end_longi + ");");
        Log.e(tag, "insert 성공");
    }

    void showMap(GoogleMap gMap, String str_date){
        Cursor c = db.rawQuery("select * from memotable;", null);
        while(c.moveToNext()){


        }
    }

}
