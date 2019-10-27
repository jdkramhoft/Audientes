package a3.audientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HearingTest extends AppCompatActivity implements View.OnClickListener {

    private Button mHeardSoundBtn, mRestartBtn, mCancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_test);

        mHeardSoundBtn = findViewById(R.id.heard_sound_btn);
        mHeardSoundBtn.setOnClickListener(this);

        mRestartBtn = findViewById(R.id.restart_btn);
        mRestartBtn.setOnClickListener(this);

        mCancelBtn = findViewById(R.id.cancel_btn);
        mCancelBtn.setOnClickListener(this);


        // TODO: play test sound after a few seconds
    }



    @Override
    public void onClick(View v) {
        if (v == mHeardSoundBtn){
            // TODO: sound is heard, log data


            // TODO: if end of test, calculate audiogram and redirect user to audiogram activity
            if (false){
                Intent i = new Intent(this, HearingTestAudiogramActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
            else {
                // TODO: else call next heard_sound method from interface

            }

        }
        else if (v == mRestartBtn){
            // TODO: erase saved data and init test from interface

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Test is restarted",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (v == mCancelBtn){
            Intent i = new Intent(this, MenuActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }
}
