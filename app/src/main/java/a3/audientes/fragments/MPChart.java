package a3.audientes.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import a3.audientes.R;

public class MPChart extends AppCompatActivity implements OnChartValueSelectedListener {

    private static int[][] left = {{500, 30},{1000, 40},{1500, 50},{2000, 60},{2500, 70},{3000, 80}};
    private static int[][] right = {{500, 35},{1000, 45},{1500, 55},{2000, 65},{2500, 75},{3000, 85}};
    private int[] freqs = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpchart);

        LineChart lineChart = findViewById(R.id.chart);

        LineDataSet left_ear = new LineDataSet(makeEntries(left), "Left Ear");
        LineDataSet right_ear = new LineDataSet(makeEntries(right), "Right Ear");

        left_ear.setAxisDependency(YAxis.AxisDependency.LEFT);
        right_ear.setAxisDependency(YAxis.AxisDependency.LEFT);


        List<ILineDataSet> data_lines = new ArrayList<>();
        data_lines.add(left_ear);
        data_lines.add(right_ear);

/*
        final String[] quarters = new String[] { "0", "1000", "2000", "3000"};

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return quarters[(int) value];
            }
        };


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

 */


        LineData data = new LineData(data_lines);


        lineChart.setData(data);
        lineChart.invalidate(); // refresh

    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


    private List<Entry> makeEntries(int[][] data){
        int x = 0, y = 1;
        List<Entry> coordinates = new ArrayList<>();

        for (int[] coordinate : data) {
            coordinates.add(new Entry(coordinate[x], coordinate[y]));
        }

        Collections.sort(coordinates, new EntryXComparator());

        return coordinates;
    }
}
