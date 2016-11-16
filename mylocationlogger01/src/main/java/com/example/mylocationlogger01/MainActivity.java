package com.example.mylocationlogger01;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv;
    LinearLayout ll;
    GPSDB db;
    final int INTEVAL_TIME = 1000 * 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

        tv = (TextView) findViewById(R.id.textView);
        tv.setText("위치정보 미수신중");

        ll = (LinearLayout)findViewById(R.id.gpslist);

        Button gpsButton = (Button)findViewById(R.id.buttonadd);
        gpsButton.setOnClickListener(this);

        Button show_Button = (Button)findViewById(R.id.buttonshow);
        show_Button.setOnClickListener(this);

        Button del_Button = (Button)findViewById(R.id.buttondelete);
        del_Button.setOnClickListener(this);

        db = new GPSDB(this);

        Reserve reserve = new Reserve(this);
        reserve.setReserve(INTEVAL_TIME);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonadd:
                UseGPS gps = new UseGPS(this);
                gps.getGPSInfo();
                tv.setText("위도 : " + gps.lati + "\n경도 : " + gps.longi);
                db.insert(gps.lati, gps.longi);
                //db.select();
                break;
            case R.id.buttonshow:
                GPSDB showdb = new GPSDB(this, ll);
                showdb.show();
                break;
            case R.id.buttondelete:
                db.deleteall();
                break;
        }
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };
}
