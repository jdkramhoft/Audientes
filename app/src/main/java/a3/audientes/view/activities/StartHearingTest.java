package a3.audientes.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import a3.audientes.R;
import a3.audientes.view.fragments.HearingTest;

public class StartHearingTest extends AppCompatActivity implements View.OnClickListener {

    ImageButton hearing_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_hearing_test);
        
        hearing_button = findViewById(R.id.hearing_button);
        hearing_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == hearing_button) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.start_hearing_test_framelayout, new HearingTest())
                    .addToBackStack(null).commit();
        }

    }


}
