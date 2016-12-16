package com.example.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lsn94 on 2016-12-16.
 */

public class PLANDBHelper extends SQLiteOpenHelper{
    public PLANDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table plantable(starttime text, endtime text, type text, planday integer,plancount integer, plantime integer);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table plantable;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
