package a3.audientes.view.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import a3.audientes.R;

public class StartHearingTest extends AppCompatActivity implements View.OnClickListener {
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    private ImageButton hearing_button;
    private MediaRecorder mediaRecorder;
    private double audioVolume;
    private TextView dbDisplay;
    private Thread runner;
    private TelephonyManager tm;
    private String networkOperator;
    private LineChart chart;
    private int lineIndex = 0;
    private List<Entry> entries;
    private List<Integer> circleColors;
    private int[] colors;
    private Boolean runThread;
    private final Runnable updater = this::updateDbDisplay;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hearing_test_start);
        entries = new ArrayList<>();
        circleColors = new ArrayList<>();
        colors = new int[]{
                getResources().getColor(R.color.green, null),
                getResources().getColor(R.color.yellow, null),
                getResources().getColor(R.color.red, null),
                getResources().getColor(R.color.line, null),
                getResources().getColor(R.color.border, null),
                getResources().getColor(R.color.hole, null)
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

        requestAudioPermission();
    }


    @Override
    public void onBackPressed() {
    }

    @Override
    public void onDestroy() {
        stop();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == hearing_button) {
            stop();
            Intent intent = new Intent(this, HearingTest.class);
            startActivity(intent);
            StartHearingTest.this.finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    public void stop() {
        if(runner != null) {
            runThread = false;
            runner = null;
        }
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    public double getNoiseLevel(double volume) {
        System.out.println("MediaRecorder current volume: "+volume);
        double db = (20 * Math.log10(volume / 0.1));
        return db > 0 ? db : 0;
    }

    public void updateDbDisplay(){
        if(runThread){
            if ("Android".equals(networkOperator)) {
                System.out.println("Emulator");
                audioVolume = getNoiseLevel(3000);
                System.out.println(audioVolume);
            }else{
                System.out.println("Mobil");
                audioVolume = getNoiseLevel(mediaRecorder.getMaxAmplitude());
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

    private void requestAudioPermission() {
        boolean permissionIsGranted = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;

        if (permissionIsGranted){
            tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            networkOperator = tm.getNetworkOperatorName();
            recordAudio();
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_RECORD_AUDIO);
        }
    }

    private void recordAudio() {
        mediaRecorder = new MediaRecorder();
        start();

        System.out.println("MediaRecorder current volume: "+ mediaRecorder.getMaxAmplitude());
        audioVolume = getNoiseLevel(mediaRecorder.getMaxAmplitude());
        System.out.println(audioVolume);

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
                        } catch (InterruptedException e) { }
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

    // MediaRecorder
    public void start(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile("/dev/null");
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_RECORD_AUDIO &&
                grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            networkOperator = tm.getNetworkOperatorName();
            recordAudio();
        }
    }
}
