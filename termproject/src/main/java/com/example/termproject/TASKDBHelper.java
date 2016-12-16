package com.example.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lsn94 on 2016-12-01.
 */

public class TASKDBHelper extends SQLiteOpenHelper {

    public TASKDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table tasktable(starttime text, endtime text, runtime integer, type text, walk text);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table tasktable;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

}
