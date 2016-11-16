package com.example.quickcoding02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn_biggger, btn_smaller, btn_bingo;
    TextView tv;
    String str_answer;
    String str_count;
    FindNumber fn = new FindNumber();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_biggger = (Button)findViewById(R.id.bigger);
        btn_smaller = (Button)findViewById(R.id.smaller);
        btn_bingo = (Button)findViewById(R.id.bingo);
        tv = (TextView)findViewById(R.id.answer);

        fn.start();
        str_answer = fn.str_num;
        tv.setText("Your Number is"+str_answer+"!!");
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.bigger:
                fn.bigger();
                str_answer = fn.str_num;
                tv.setText("Your Number is"+str_answer+"!!");
                break;
            case R.id.smaller:
                fn.smaller();
                str_answer = fn.str_num;
                tv.setText("Your Number is"+str_answer+"!!");
                break;
            case R.id.bingo:
                str_answer = fn.str_num;
                str_count = Integer.toString(fn.count);
                tv.setText("Bingo !! Count is "+str_count);
        }
    }
}
