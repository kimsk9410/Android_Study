package com.example.mylocationlogger01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

/**
 * Created by lsn94 on 2016-10-29.
 */

public class ReserveReceive extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        UseGPS gps = new UseGPS(context);
        gps.getGPSInfo();
        GPSDB db = new GPSDB(context);
        db.insert(gps.lati, gps.longi);
        db.select();

    }
}