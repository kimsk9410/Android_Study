package com.example.mylocationlogger02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by lsn94 on 2016-11-09.
 */

public class ReserveReceive extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        MapsActivity ma = new MapsActivity();
        UseGPS gps = new UseGPS(context);
        gps.getGPSInfo();
        GPSDB db = new GPSDB(context);
        db.insert(gps.lati, gps.longi);
        ma.gps_list.add(new LatLng(gps.lati, gps.longi));

        db.select();

    }

}