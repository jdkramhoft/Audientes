package a3.audientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HearingProfil extends AppCompatActivity implements View.OnClickListener {

    Button hearing_test, oldTest, thisHearing, maint_and_repair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_profil);

        hearing_test = findViewById(R.id.hearing_test);
        hearing_test.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if ( v == hearing_test) {
            Intent intent = new Intent(this, BeginHearingTestActivity.class);
            startActivity(intent);
        }
    }
}
