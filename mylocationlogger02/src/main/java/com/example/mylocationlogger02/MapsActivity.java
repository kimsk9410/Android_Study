package com.example.mylocationlogger02;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    TextView tv;
    LinearLayout ll, type_ll;
    Spinner spinner;
    EditText editText;
    GPSDB db;
    final int INTEVAL_TIME = 1000*60*10;
    public ArrayList<LatLng> gps_list = new ArrayList<>();
    public Context context = MapsActivity.this;
    String[] memo_arr = {"학교", "식사", "카페", "취미", "운동"};
    String type_str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

        tv = (TextView) findViewById(R.id.textView);
        tv.setText("수신 위치 정보");

        ll = (LinearLayout)findViewById(R.id.gpslist);

        Button add_Button = (Button)findViewById(R.id.buttonadd);
        add_Button.setOnClickListener(this);
        Button show_Button = (Button)findViewById(R.id.buttonshow);
        show_Button.setOnClickListener(this);
        Button del_Button = (Button)findViewById(R.id.buttondelete);
        del_Button.setOnClickListener(this);
        Button start_Button = (Button)findViewById(R.id.buttonstart);
        start_Button.setOnClickListener(this);
        Button stop_Button = (Button)findViewById(R.id.buttonstop);
        stop_Button.setOnClickListener(this);
        Button stats_Button = (Button)findViewById(R.id.buttonstats);
        stats_Button.setOnClickListener(this);

        db = new GPSDB(this);
        db.loadDB(gps_list);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonadd:
                UseGPS gps = new UseGPS(this);
                gps.getGPSInfo();
                tv.setText("위도 : " + gps.lati + " 경도 : " + gps.longi);
                db.insert(gps.lati, gps.longi);
                gps_list.add(new LatLng(gps.lati, gps.longi));
                break;
            case R.id.buttonstart:
                Reserve reserve = new Reserve(this);
                reserve.setReserve(INTEVAL_TIME);
                break;
            case R.id.buttonshow:
                db.select();
                LatLng ll_prev = new LatLng(37.55,127);
                db.loadDB(gps_list);
                db.show(ll);
                mMap.clear();
                for(LatLng ll : gps_list){
                    if(ll_prev.latitude != ll.latitude || ll_prev.longitude != ll.longitude) {
                        String time = db.getTime(ll.latitude, ll.longitude);
                        String memo = db.getMemo(ll.latitude, ll.longitude);
                        mMap.addMarker(new MarkerOptions().position(ll).title(time).snippet(memo));
                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                final double markerlati = marker.getPosition().latitude;
                                final double markerlongi = marker.getPosition().longitude;
                                AlertDialog.Builder adb = new AlertDialog.Builder(MapsActivity.this);
                                type_ll = new LinearLayout(MapsActivity.this);
                                setSpinner();
                                setEditText();
                                type_ll.addView(spinner);
                                type_ll.addView(editText);
                                type_ll.setPadding(50,0,0,0);

                                adb
                                        .setTitle("메모")
                                        .setCancelable(false)
                                        .setMessage("메모를 입력해 주세요.")
                                        .setView(type_ll)
                                        .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String memo = editText.getText().toString();
                                                db.insert_memo(markerlati, markerlongi, type_str, memo);
                                                type_str = "";
                                                Toast.makeText(MapsActivity.this,"저장되었습니다. '표시'를 눌러주세요",Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog ad = adb.create();
                                ad.show();
                            }
                        });
                        ll_prev = ll;
                    }
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll_prev,12));
                break;
            case R.id.buttondelete:
                db.deleteall();
                break;
            case R.id.buttonstop:
                reserve = new Reserve(this);
                reserve.releaseReserve();
                break;
            case R.id.buttonstats:
                Intent intent=new Intent(MapsActivity.this,StatsActivity.class);
                startActivity(intent);
                break;
        }
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(MapsActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(MapsActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        UseGPS gps = new UseGPS(this);
        gps.getGPSInfo();

        LatLng cl = new LatLng(gps.lati, gps.longi);
        mMap.addMarker(new MarkerOptions().position(cl).title("current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cl,11));
    }

    public void setSpinner(){
        spinner = new Spinner(this);
        ArrayAdapter memoAdapter = new ArrayAdapter(MapsActivity.this, android.R.layout.simple_spinner_item, memo_arr);
        memoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(memoAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_str = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setEditText(){
        editText = new EditText(this);
        editText.setHint("메모를 입력하세요.");
        editText.setHintTextColor(0x50000000);
        editText.setEms(12);
    }

}
