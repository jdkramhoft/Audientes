package a3.audientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Programs extends AppCompatActivity implements View.OnClickListener {

    ImageButton noisy_edit, normal_edit, cinema_edit, quiet_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);

        noisy_edit = findViewById(R.id.noisy_edit);
        noisy_edit.setOnClickListener(this);

        normal_edit = findViewById(R.id.normal_edit);
        normal_edit.setOnClickListener(this);

        cinema_edit = findViewById(R.id.cinema_edit);
        cinema_edit.setOnClickListener(this);

        quiet_edit = findViewById(R.id.quiet_edit);
        quiet_edit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == noisy_edit) {
            Intent intent = new Intent(this, EditProgram.class);
            startActivity(intent);
        } else if ( v == normal_edit) {
            Intent intent = new Intent(this, EditProgram.class);
            startActivity(intent);
        } else if ( v == cinema_edit) {
            Intent intent = new Intent(this, EditProgram.class);
            startActivity(intent);
        } else if ( v == quiet_edit) {
            Intent intent = new Intent(this, EditProgram.class);
            startActivity(intent);
        }

    }
}
