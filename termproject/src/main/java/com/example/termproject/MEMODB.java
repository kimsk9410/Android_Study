package com.example.termproject;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by lsn94 on 2016-12-03.
 */

public class MEMODB {

    private MemoDBHelper helper;
    String dbName = "memodb.db";
    int dbVersion = 1; // 데이터베이스 버전
    private SQLiteDatabase db;
    String tag = "SQLite"; // Log 에 사용할 tag
    Context mContext;

    MEMODB(Context mContext){
        helper = new MemoDBHelper(mContext, dbName, null, dbVersion);
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

    void delete(int i) {
        db.execSQL("delete from tasktable where id=" + i + ";");
        Log.e(tag, "delete 완료");
    }

    void deleteall(){
        Cursor c = db.rawQuery("select * from tasktable;", null);
        while(c.moveToNext()) {
            int id = c.getInt(0);
            db.execSQL("delete from tasktable where id=" + id +";");
        }

        Log.e(tag,"delete 완료");
    }

    void update(int i, double lati, double longi) {
        db.execSQL("update gpstable set latitude=" + lati + ", longitude=" + longi + " where id=" + i + ";");
        Log.e(tag, "update 완료");
    }

    void select() {
        Cursor c = db.rawQuery("select * from gpstable;", null);
        while(c.moveToNext()) {
            int id = c.getInt(0);
            double lati = c.getDouble(1);
            double longi = c.getDouble(2);
            String memo = c.getString(4);
            memo += c.getString(5);
            Log.e(tag,"id:" + id + ", latitude:" + lati + ", longitude" + longi);
            Log.e(tag, memo);
        }
    }

    String getTime(double lati_find, double longi_find){
        String time = "";

        Cursor c = db.rawQuery("select * from gpstable;", null);
        while(c.moveToNext()){
            double lati = c.getDouble(1);
            double longi = c.getDouble(2);

            if(lati_find == lati && longi_find == longi){
                time = c.getString(3);
                break;
            }
        }
        return time;
    }

    String getMemo(double lati_find, double longi_find){
        String memo = "메모없음";

        Cursor c = db.rawQuery("select * from gpstable;", null);
        while(c.moveToNext()){
            double lati = c.getDouble(1);
            double longi = c.getDouble(2);

            if(lati_find == lati && longi_find == longi){
                try{
                    memo = c.getString(4);
                    memo += " : ";
                    memo += c.getString(5);
                    break;
                }catch (Exception e){
                    Log.e(tag,"getMemo실패");
                    break;
                }
            }
        }
        return memo;
    }

    void insert (double lati, double longi) {
        if(isExist(lati, longi)) {
            Toast.makeText(mContext,"이미 저장된 위치입니다",Toast.LENGTH_SHORT).show();
        }else{
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String strNow = sdfNow.format(date);
            db.execSQL("insert into gpstable (latitude,longitude,starttime,type,memo) values(" + lati + "," + longi + ",'" + strNow + "','분류','메모없음');");
            Log.e(tag, "insert 성공");
        }
    }

    void show(LinearLayout ll){
        Cursor c = db.rawQuery("select * from gpstable;", null);
        ll.removeAllViews();
        while(c.moveToNext()) {
            //int id = c.getInt(0);
            double lati = c.getDouble(1);
            double longi = c.getDouble(2);
            String time = c.getString(3);

            TextView tv = new TextView(mContext);
            tv.setText("· " + time + " 위도:" + lati + " 경도" + longi);
            ll.addView(tv);
        }
    }

    void loadDB(ArrayList<LatLng> gps_list){
        Cursor c = db.rawQuery("select * from gpstable;", null);
        gps_list.clear();
        while(c.moveToNext()) {
            double lati = c.getDouble(1);
            double longi = c.getDouble(2);

            gps_list.add(new LatLng(lati,longi));
        }
    }

    boolean isExist(double lati_in, double longi_in){
        Cursor c = db.rawQuery("select * from gpstable;", null);
        while(c.moveToNext()){
            double lati = c.getDouble(1);
            double longi = c.getDouble(2);
            if(lati == lati_in && longi == longi_in){
                return true;
            }
        }
        return false;
    }

    void statsDB(ArrayList<Entry> al_e, ArrayList<String> al_s){
        Cursor c = db.rawQuery("select * from gpstable;", null);
        int type_num = 5;
        int[] count_arr = new int[type_num];
        while(c.moveToNext()){
            String type = c.getString(4);
            switch (type){
                case "분류":
                    break;
                case "학교":
                    count_arr[0]++;
                    break;
                case "식사":
                    count_arr[1]++;
                    break;
                case "카페":
                    count_arr[2]++;
                    break;
                case "취미":
                    count_arr[3]++;
                    break;
                case "운동":
                    count_arr[4]++;
                    break;
            }
        }
        al_s.add("학교");
        al_s.add("식사");
        al_s.add("카페");
        al_s.add("취미");
        al_s.add("운동");
        for(int i = 0; i < type_num; i++){
            al_e.add(new Entry((float)count_arr[i],i));
        }
        /*al_e.add(new Entry((float)count_arr[0],0));
        al_e.add(new Entry((float)count_arr[1],1));
        al_e.add(new Entry((float)count_arr[2],2));
        al_e.add(new Entry((float)count_arr[3],3));
        al_e.add(new Entry((float)count_arr[4],4));
        al_e.add(new Entry((float)count_arr[5],5));*/
    }

}
