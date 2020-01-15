package a3.audientes.view.activities;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import a3.audientes.R;

public class StartHearingTest extends AppCompatActivity implements View.OnClickListener {

    ImageButton hearing_button;
    private MediaRecorder mRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_hearing_test);
        
        hearing_button = findViewById(R.id.hearing_button);
        hearing_button.setOnClickListener(this);
        mRecorder = new MediaRecorder();

        if (android.os.Build.MODEL.contains("google_sdk") || android.os.Build.MODEL.contains("Emulator")) {
            mRecorder = new MediaRecorder();
            try {
                start();
                System.out.println("MediaRecorder: jaaaaaa");
                System.out.println(getAmplitude());
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(getAmplitude());
                    }
                }, 5000);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public double getAmplitude() {
        if (mRecorder != null)
            return  mRecorder.getMaxAmplitude();
        else
            return 0;

    }


}
