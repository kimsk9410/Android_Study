package com.example.mylocationlogger02;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * Created by lsn94 on 2016-11-09.
 */

public class Reserve {

    Context mContext;

    public Reserve(Context mContext){this.mContext = mContext;}

    void setReserve(long second){
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(mContext, ReserveReceive.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        long t = SystemClock.elapsedRealtime();

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, t, second, pIntent);
    }

    void releaseReserve(){
        AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, ReserveReceive.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        alarmManager.cancel(pIntent);

        // 먼저 실행되는 알람이 있을 경우, 제거하고 새로 알람을 실행하게 된다. 상황에 따라 유용하게 사용 할 수 있다.
        //alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 3000, pIntent);
    }

}
