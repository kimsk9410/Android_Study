package com.example.termproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by lsn94 on 2016-11-26.
 */

public class FragmentReport extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.report, container, false);

        PieChart pieChart = (PieChart) v.findViewById(R.id.chart);
        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<Entry> entries = new ArrayList<Entry>();

        TASKDB db = new TASKDB(getContext());
        db.statsDB(entries, labels);
        PieDataSet dataset = new PieDataSet(entries,"");
        PieData data = new PieData(labels, dataset);
        pieChart.setData(data);
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);
        pieChart.animateY(1000);
        dataset.setValueTextSize(15f);
        pieChart.setDescription("");

        return v;
    }
}
