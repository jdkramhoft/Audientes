package a3.audientes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class EditProgram extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private SeekBar low_plus, low, medium, high, high_plus;
    private TextView low_plus_txt, low_txt, medium_txt, high_txt, high_plus_txt;
    private Button save_btn_config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_program);

        low_plus_txt = findViewById(R.id.set_one);
        low_txt = findViewById(R.id.set_two);
        medium_txt = findViewById(R.id.set_three);
        high_txt = findViewById(R.id.set_four);
        high_plus_txt = findViewById(R.id.set_five);

        low_plus = findViewById(R.id.low_plus);
        low = findViewById(R.id.low);
        medium = findViewById(R.id.medium);
        high = findViewById(R.id.high);
        high_plus = findViewById(R.id.high_plus);

        low_plus.setOnSeekBarChangeListener(this);
        low.setOnSeekBarChangeListener(this);
        medium.setOnSeekBarChangeListener(this);
        high.setOnSeekBarChangeListener(this);
        high_plus.setOnSeekBarChangeListener(this);

        save_btn_config = findViewById(R.id.save_btn_config);
        save_btn_config.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // TODO: if any changes were made
              /*
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_popup_edit_program, null);
        Button button1 = dialogView.findViewById(R.id.button1);
        Button button2 = dialogView.findViewById(R.id.button2);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        button1.setOnClickListener(v12 -> dialog.dismiss());
        button2.setOnClickListener(v1 -> {
            startActivity(new Intent(getActivity(), EditProgram.class));
            dialog.dismiss();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

         */

          // TODO: else save and redirect to hearingprofile


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.low_plus: low_plus_txt.setText("" + progress); break;
            case R.id.low: low_txt.setText("" + progress); break;
            case R.id.medium: medium_txt.setText("" + progress); break;
            case R.id.high: high_txt.setText("" + progress); break;
            case R.id.high_plus: high_plus_txt.setText("" + progress); break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
