package com.example.termproject;

import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lsn94 on 2016-11-26.
 */

public class FragmentWalk extends Fragment implements View.OnClickListener, OnMapReadyCallback{

    private GoogleMap mMap;
    private MapView mapView;
    Button btn_date;
    DatePicker dp;
    String pick_date;
    TextView tv_walk, tv_calroies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.walk, container, false);

        mapView = (MapView)v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        btn_date = (Button)v.findViewById(R.id.walkbuttondate);
        btn_date.setOnClickListener(this);

        long long_starttime = System.currentTimeMillis();
        Date date_starttime = new Date(long_starttime);
        SimpleDateFormat sdf_starttime = new SimpleDateFormat("yyyy년MM월dd일");
        pick_date = sdf_starttime.format(date_starttime);

        tv_walk = (TextView)v.findViewById(R.id.walktext);
        tv_calroies = (TextView)v.findViewById(R.id.calorytext);

        return v;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.walkbuttondate:
                AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
                LinearLayout ll = new LinearLayout(v.getContext());
                dp = new DatePicker(v.getContext());
                dp.init(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), new DatePicker.OnDateChangedListener(){
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        pick_date = String.format("%d년%d월%d일",year, monthOfYear+1, dayOfMonth);
                    }
                });
                ll.addView(dp);
                ll.setPadding(20,20,0,0);

                adb
                        .setView(ll)
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("선택", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                btn_date.setText(pick_date);
                                GPSDB gpsdb = new GPSDB(getActivity());
                                gpsdb.showMap(mMap, pick_date);
                                WALKDB walkdb = new WALKDB(getContext());
                                walkdb.showWalk(pick_date, tv_walk, tv_calroies);

                            }
                        });
                AlertDialog ad = adb.create();
                ad.show();
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        UseGPS gps = new UseGPS(getContext());
        gps.getGPSInfo();

        LatLng cl = new LatLng(gps.lati, gps.longi);
        mMap.addMarker(new MarkerOptions().position(cl).title("current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cl,11));
    }


}
