package com.example.touchcoding;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View vw = new View(this);

        setContentView(R.layout.activity_main);

        TextView tv = (TextView)findViewById(R.id.text);

        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.e("ACTION_","DOWN");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("ACTION_","UP");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.e("ACTION_","CANCEL");
                        break;
                    /*case MotionEvent.ACTION_MOVE:
                        Log.e("ACTION_","MOVE");
                        break;*/
                }

                return true;
            }
        });
    }
}
