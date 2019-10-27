package a3.audientes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HearingTest extends AppCompatActivity implements View.OnClickListener {

    private Button mHeardSoundBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_test);

        mHeardSoundBtn = findViewById(R.id.heard_sound_btn);
        mHeardSoundBtn.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        if (v == mHeardSoundBtn){




            // TODO: call heard_sound method from interface
        }
    }
}
