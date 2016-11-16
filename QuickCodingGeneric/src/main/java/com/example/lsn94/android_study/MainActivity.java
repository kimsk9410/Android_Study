package com.example.lsn94.android_study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText et;
    Button enter_bt, end_bt, reset_bt;
    TextView str_text, int_text;

    LinkedList<String> str_list = new LinkedList<>();
    LinkedList<Integer> int_list = new LinkedList<>();

    String input_str;
    int input_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText)findViewById(R.id.editText);
        enter_bt = (Button)findViewById(R.id.enterbutton);
        end_bt = (Button)findViewById(R.id.endbutton);
        reset_bt = (Button)findViewById(R.id.resetbutton);
        str_text = (TextView)findViewById(R.id.strtext);
        int_text = (TextView)findViewById(R.id.inttext);

        enter_bt.setOnClickListener(this);
        end_bt.setOnClickListener(this);
        reset_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.enterbutton:
                input_str = et.getText().toString();
                if(isInteger(input_str)){
                    int_list.add(input_int);
                }
                else{
                    str_list.add(input_str);
                }
                et.setText("");
                break;
            case R.id.endbutton:
                int_text.setText("입력된 숫자 : "+int_list.toString());
                str_text.setText("입력된 문자 : "+str_list.toString());
                break;
            case R.id.resetbutton:
                int_list.clear();
                str_list.clear();
                break;
        }

    }

    public boolean isInteger(String str){
        try{
            input_int = Integer.parseInt(str);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
