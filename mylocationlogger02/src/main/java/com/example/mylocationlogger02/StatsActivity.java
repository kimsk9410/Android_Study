package com.example.mylocationlogger02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by lsn94 on 2016-11-15.
 */

public class StatsActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        //Intent intent=new Intent(this.getIntent());

        PieChart pieChart = (PieChart) findViewById(R.id.chart);
        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<String> labels = new ArrayList<String>();
        GPSDB db = new GPSDB(this);
        db.statsDB(entries, labels);
        PieDataSet dataset = new PieDataSet(entries,"");
        PieData data = new PieData(labels, dataset);
        pieChart.setData(data);
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);
        pieChart.animateY(1000);
        dataset.setValueTextSize(15f);
        pieChart.setDescription("");
    }

}
