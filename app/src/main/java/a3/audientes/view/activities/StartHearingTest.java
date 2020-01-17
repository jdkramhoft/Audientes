package a3.audientes.view.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import a3.audientes.R;

public class StartHearingTest extends AppCompatActivity implements View.OnClickListener {

    ImageButton hearing_button;
    private MediaRecorder mRecorder;
    private double audioVolume;
    private TextView dbDisplay;
    private Thread runner;
    private TelephonyManager tm;
    private String networkOperator;
    private LineChart chart;
    private int lineIndex = 0;
    List<Entry> entries;
    List<Integer> circleColors;
    int[] colors;
    Boolean runThread;
    final Runnable updater = new Runnable(){
        public void run(){
            updateDbDisplay();
        };
    };

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hearing_test_start);
        hearing_button = findViewById(R.id.hearing_button);
        hearing_button.setOnClickListener(this);
        dbDisplay = findViewById(R.id.volume);
        chart = findViewById(R.id.chart);
        chart.setVisibility(View.GONE);
        dbDisplay.setVisibility(View.GONE);

        entries = new ArrayList<>();
        circleColors = new ArrayList<>();
        colors = new int[]{
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.red),
                getResources().getColor(R.color.line),
                getResources().getColor(R.color.border),
                getResources().getColor(R.color.hole)
        };

        // Styling of chart
        chart.getDescription().setEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawLabels(false);
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawLabels(false);
        chart.getAxisLeft().setDrawLimitLinesBehindData(true);
        chart.setTouchEnabled(false);
        chart.setDrawBorders(true);
        chart.setBorderColor(colors[4]);
        dbDisplay.setTextColor(colors[5]);


        // Android permission for RECORD_AUDIO popup
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    0);
        }

        // Checking if code is running on emulator.
        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        networkOperator = tm.getNetworkOperatorName();

        if ("Android".equals(networkOperator)) {
            System.out.println("Emulator");
            System.out.println("Chart is not displayed");
        }else{
            System.out.println("Mobil");
            mRecorder = new MediaRecorder();
            try {
                start();
                System.out.println("MediaRecorder current volume: "+mRecorder.getMaxAmplitude());
                audioVolume = getNoiseLevel(mRecorder.getMaxAmplitude());
                System.out.println(audioVolume);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            // Display on init.
            chart.setVisibility(View.VISIBLE);
            dbDisplay.setVisibility(View.VISIBLE);
            LineDataSet dataSet = new LineDataSet(entries, "");
            chart.getLegend().setEnabled(false);
            dataSet.setCircleHoleColor(colors[5]);
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.invalidate();

            // Tread
            if (runner == null) {
                runThread = true;
                runner = new Thread(){
                    public void run() {
                        while (runner != null) {
                            if(!runThread){
                                return;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) { };
                            handler.post(updater);
                        }
                    }
                };
                runner.start();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();


        if ("Android".equals(networkOperator)) {
            System.out.println("Emulator resume");
        }else{
            System.out.println("Mobil resume");

            // Tread
            if (runner == null) {
                runThread = true;
                runner = new Thread(){
                    public void run() {
                        while (runner != null) {
                            if(!runThread){
                                return;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) { };
                            handler.post(updater);
                        }
                    }
                };
                runner.start();
            }
        }

    }


    @Override
    public void onClick(View v) {
        if (v == hearing_button) {
            if(runner != null){
                runThread = false;
                runner = null;
            }

            startActivity(new Intent(this, HearingTest.class));
        }
    }

    // MediaRecorder
    public void start() throws IOException {
         mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
         mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
         mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
         mRecorder.setOutputFile("/dev/null");
         mRecorder.prepare();
         mRecorder.start();
    }

    public void stop() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public double getNoiseLevel(double volume) {
        System.out.println("MediaRecorder current volume: "+volume);
        double db = (20 * Math.log10(volume / 0.1));
        if(db>0) {
            return db;
        } else {
            return 0;
        }
    }

    public void updateDbDisplay(){

        if ("Android".equals(networkOperator)) {
            System.out.println("Emulator");
            audioVolume = getNoiseLevel(3000);
            System.out.println(audioVolume);
        }else{
            System.out.println("Mobil");
            audioVolume = getNoiseLevel(mRecorder.getMaxAmplitude());
            System.out.println(audioVolume);
        }

        if(((int)audioVolume) <= 80){
            dbDisplay.setTextColor(colors[0]);
            circleColors.add(colors[0]);
        }else if(((int)audioVolume) >= 81 && ((int)audioVolume) <= 91){
            dbDisplay.setTextColor(colors[1]);
            circleColors.add(colors[1]);
        }else{
            dbDisplay.setTextColor(colors[2]);
            circleColors.add(colors[2]);
        }

        lineIndex++;
        entries.add(new Entry(lineIndex, (float)audioVolume));
        if(entries.size() > 10){
            entries.remove(0);
            circleColors.remove(0);
        }
        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setColor(colors[3]);
        dataSet.setCircleHoleColor(colors[5]);
        dataSet.setCircleColors(circleColors);
        dataSet.setValueTextColor(colors[4]);
        dataSet.setValueTextSize(0);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
        dbDisplay.setText(((int)audioVolume) + " dB");
    }

}
