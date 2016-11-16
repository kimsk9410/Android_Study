package com.example.quickcoding02;

import java.util.Random;

/**
 * Created by lsn94 on 2016-10-04.
 */
public class FindNumber {

    int num, max, min, count;
    String str_num;

    public void start(){
        Random rd = new Random();
        num = rd.nextInt(501);
        min = 0;
        max = 500;
        str_num = Integer.toString(num);
        count = 0;
    }

    public void bigger(){
        min = num;
        num = (max + num) / 2;
        str_num = Integer.toString(num);
        count++;
    }

    public void smaller(){
        max = num;
        num = (min + num) / 2;
        str_num = Integer.toString(num);
        count++;
    }

}
