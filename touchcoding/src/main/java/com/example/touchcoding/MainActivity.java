package com.example.touchcoding;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View vw = new View(this);
        vw.setOnTouchListener(TouchListener);

        setContentView(R.layout.activity_main);
    }

    class TouchListenerClass implements View.OnTouchListener{
        public boolean onTouch(View v, MotionEvent event){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                Toast.makeText(MainActivity.this, "Touch Event Received", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }
    }

    TouchListenerClass TouchListener = new TouchListenerClass();
}
