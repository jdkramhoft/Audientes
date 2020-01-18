package a3.audientes.view.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
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

    private ImageButton hearing_button;
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
    final Runnable updater = this::updateDbDisplay;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hearing_test_start);
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

        hearing_button = findViewById(R.id.hearing_button);
        hearing_button.setOnClickListener(this);
        dbDisplay = findViewById(R.id.volume);
        dbDisplay.setVisibility(View.GONE);
        dbDisplay.setTextColor(colors[5]);

        chart = findViewById(R.id.chart);
        chart.setVisibility(View.GONE);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(false);
        chart.setDrawBorders(false);
        chart.setBorderColor(colors[4]);


        // Styling of chart
        XAxis xAxis = chart.getXAxis();
        YAxis leftAxis = chart.getAxisLeft();
        YAxis rightAxis = chart.getAxisRight();

        xAxis.setEnabled(false);
        leftAxis.setEnabled(false);
        rightAxis.setEnabled(false);

        xAxis.setDrawGridLines(false);
        leftAxis.setDrawGridLines(false);

        leftAxis.setDrawLabels(false);
        rightAxis.setDrawLabels(false);


        xAxis.setAvoidFirstLastClipping(true);
        leftAxis.setDrawLimitLinesBehindData(true);




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
            stop();
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
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) { };
                            if(!runThread){
                                return;
                            }
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

            if (runner == null) {
                stop();
                mRecorder = new MediaRecorder();
                try {
                    start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Tread
                runThread = true;
                runner = new Thread(){
                    public void run() {
                        while (runner != null) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) { };
                            if(!runThread){
                                return;
                            }
                            handler.post(updater);
                        }
                    }
                };
                runner.start();
            }
        }

    }

    @Override
    public void onBackPressed() {
        if(runner != null){
            runThread = false;
            runner = null;
            stop();
        }
        finish();
    }


    @Override
    public void onClick(View v) {
        if (v == hearing_button) {
            if(runner != null){
                runThread = false;
                runner = null;
                stop();
            }
            startActivity(new Intent(this, HearingTest.class));
            //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

        if(runThread){
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
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet.setCubicIntensity(0.2f);
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.invalidate();
            dbDisplay.setText(((int)audioVolume) + " dB");
        }
    }

}
