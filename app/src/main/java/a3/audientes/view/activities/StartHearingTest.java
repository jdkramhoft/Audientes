package a3.audientes.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import a3.audientes.R;

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
            startActivity(new Intent(this, HearingTest.class));
        }

    }


}
