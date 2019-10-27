package a3.audientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BeginHearingTestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBeginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_hearing_test);

        mBeginBtn = findViewById(R.id.begin_test_btn);
        mBeginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBeginBtn){
            Intent intent = new Intent(this, HearingTest.class);
            startActivity(intent);
        }
    }
}
