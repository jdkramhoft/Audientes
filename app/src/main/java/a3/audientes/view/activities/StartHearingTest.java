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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.io.IOException;

import a3.audientes.R;

public class StartHearingTest extends AppCompatActivity implements View.OnClickListener {

    ImageButton hearing_button;
    private MediaRecorder mRecorder;
    private double audioVolume;
    private TextView dbDisplay;
    private Thread runner;

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

        // Android permission for RECORD_AUDIO popup
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    0);
        }

        if (runner == null) {
            runner = new Thread(){
                public void run() {
                    while (runner != null)
                    {
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

    @Override
    public void onClick(View v) {
        if (v == hearing_button) {
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
        double db = (20 * Math.log10(volume / 0.1));
        if(db>0) {
            return db;
        } else {
            return 0;
        }
    }

    public void updateDbDisplay(){

        // Checking if code is running on emulator.
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tm.getNetworkOperatorName();

        if ("Android".equals(networkOperator)) {
            System.out.println("Emulator");
            audioVolume = getNoiseLevel(3000);
            System.out.println(audioVolume);
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
        }

        dbDisplay.setText(Double.toString(audioVolume) + " dB");
    }

}
