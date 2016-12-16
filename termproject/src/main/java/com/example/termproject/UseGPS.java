package com.example.termproject;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by lsn94 on 2016-12-16.
 */

public class UseGPS {

    Context mContext;
    LocationManager lm;
    double lati,longi;

    UseGPS(Context mContext){
        this.mContext = mContext;
        lm = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    void getGPSInfo(){
        try{
            Location location;

            if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }else{
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
                location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            lati = location.getLatitude();
            longi = location.getLongitude();

        }catch(SecurityException ex){

            Toast.makeText(mContext, "수신실패", Toast.LENGTH_SHORT).show();

        }
    }

    private final LocationListener mLocationListener = new LocationListener() {

        public void onLocationChanged(Location location) {

            double latitude = location.getLatitude();   //위도
            double longitude = location.getLongitude(); //경도
            double altitude = location.getAltitude();   //고도
            float accuracy = location.getAccuracy();    //정확도
            String provider = location.getProvider();   //위치제공자
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.

        }
        public void onProviderDisabled(String provider) {
            // Disabled시
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
        }
    };

}
