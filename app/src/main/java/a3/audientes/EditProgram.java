package a3.audientes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import a3.audientes.activities.HearingProfile;
import a3.audientes.models.Program;
import a3.audientes.models.ProgramManager;
import a3.audientes.viewModels.ProgramViewModel;

public class EditProgram extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView low_plus_txt, low_txt, medium_txt, high_txt, high_plus_txt;
    private SeekBar low_plus, low, medium, high, high_plus;
    private Button save_btn_config;
    private EditText name;
    private ProgramViewModel programviewmodel;
    private ProgramManager programManager = ProgramManager.getInstance();
    private int programId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_program);

        programviewmodel = ViewModelProviders.of(this).get(ProgramViewModel.class);

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

        name = findViewById(R.id.editText);

        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(name.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            programId = Integer.parseInt(extras.getString("id"));
        }

    }


    @Override
    public void onClick(View v) {

        if(v == save_btn_config){

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Program newProgram = new Program(
                        name.getText().toString(),
                        Integer.parseInt(low_plus_txt.getText().toString()),
                        Integer.parseInt(low_plus_txt.getText().toString()),
                        Integer.parseInt(low_plus_txt.getText().toString()),
                        Integer.parseInt(low_plus_txt.getText().toString()),
                        Integer.parseInt(low_plus_txt.getText().toString()),
                        1,
                        true
                );
                newProgram.setId(programId);
                System.out.println(programId);

            }else{
                Program newProgram = new Program(
                        name.getText().toString(),
                        Integer.parseInt(low_plus_txt.getText().toString()),
                        Integer.parseInt(low_plus_txt.getText().toString()),
                        Integer.parseInt(low_plus_txt.getText().toString()),
                        Integer.parseInt(low_plus_txt.getText().toString()),
                        Integer.parseInt(low_plus_txt.getText().toString()),
                        1,
                        true
                );
                newProgram.setId(programManager.getNextId());
                programManager.addProgram(newProgram);
                programviewmodel.Insert(newProgram);
                System.out.println(programManager.getNextId());
            }

            Intent intent = new Intent(EditProgram.this, HearingProfile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            EditProgram.this.startActivity(intent);
        }

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

    @Override
    public void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            programId = Integer.parseInt(extras.getString("id"));
        }
    }
}
