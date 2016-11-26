package com.example.quickcodingpedometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    SensorManager sm = (SensorManager)getSystemService(SENSOR_SERVICE);
    Sensor sensor_gravity = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
    Sensor sensor_accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    Sensor sensor_gyroscope = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
}
