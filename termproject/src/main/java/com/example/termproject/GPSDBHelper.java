package com.example.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lsn94 on 2016-12-16.
 */

public class GPSDBHelper extends SQLiteOpenHelper {

    public GPSDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table gpstable(walkday text, start_lati double, start_longi double, end_lati double, end_longi double);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table gpstable;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

}
