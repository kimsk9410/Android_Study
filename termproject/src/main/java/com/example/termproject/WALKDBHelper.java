package com.example.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lsn94 on 2016-12-17.
 */

public class WALKDBHelper extends SQLiteOpenHelper{

    public WALKDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table walktable(walkday text, walkcount integer, calories double);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table walktable;";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

}
