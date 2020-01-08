package a3.audientes.view.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import a3.audientes.R;
import a3.audientes.model.Program;
import a3.audientes.model.ProgramManager;
import a3.audientes.viewmodel.ProgramViewModel;

public class EditProgram extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView low_plus_txt, low_txt, medium_txt, high_txt, high_plus_txt, name;
    private SeekBar low_plus, low, medium, high, high_plus;
    private Button save_btn_config;
    private int programId;
    private ProgramManager programManager = ProgramManager.getInstance();
    private ProgramViewModel programViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_program);

        programViewModel = ViewModelProviders.of(this).get(ProgramViewModel.class);

        low_plus_txt = findViewById(R.id.low_plus).findViewById(R.id.seekbar_text);
        low_txt = findViewById(R.id.low).findViewById(R.id.seekbar_text);
        medium_txt = findViewById(R.id.medium).findViewById(R.id.seekbar_text);
        high_txt = findViewById(R.id.high).findViewById(R.id.seekbar_text);
        high_plus_txt = findViewById(R.id.high_plus).findViewById(R.id.seekbar_text);

        low_plus_txt.setText("low+");
        low_txt.setText("low");
        medium_txt.setText("medium");
        high_txt.setText("high");
        high_plus_txt.setText("high+");


        low_plus = findViewById(R.id.low_plus).findViewById(R.id.seekbar);
        low = findViewById(R.id.low).findViewById(R.id.seekbar);
        medium = findViewById(R.id.medium).findViewById(R.id.seekbar);
        high = findViewById(R.id.high).findViewById(R.id.seekbar);
        high_plus = findViewById(R.id.high_plus).findViewById(R.id.seekbar);

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
            if(extras.getBoolean("new") == false){
                programId = Integer.parseInt(extras.getString("id"));
                updateSliders(programManager.getProgram(programId));

                if(extras.getBoolean("edit") == false){
                    low_plus.setEnabled(false);
                    low.setEnabled(false);
                    medium.setEnabled(false);
                    high.setEnabled(false);
                    high_plus.setEnabled(false);
                    save_btn_config.setVisibility(View.INVISIBLE);
                    name.setEnabled(false);
                }else{
                    low_plus.setEnabled(true);
                    low.setEnabled(true);
                    medium.setEnabled(true);
                    high.setEnabled(true);
                    high_plus.setEnabled(true);
                    save_btn_config.setVisibility(View.VISIBLE);
                    name.setEnabled(true);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if(extras.getBoolean("new") == false){
                programId = Integer.parseInt(extras.getString("id"));
                updateSliders(programManager.getProgram(programId));

                if(extras.getBoolean("edit") == false){
                    low_plus.setEnabled(false);
                    low.setEnabled(false);
                    medium.setEnabled(false);
                    high.setEnabled(false);
                    high_plus.setEnabled(false);
                    save_btn_config.setVisibility(View.INVISIBLE);
                    name.setEnabled(false);
                }else{
                    low_plus.setEnabled(true);
                    low.setEnabled(true);
                    medium.setEnabled(true);
                    high.setEnabled(true);
                    high_plus.setEnabled(true);
                    save_btn_config.setVisibility(View.VISIBLE);
                    name.setEnabled(true);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if(extras.getBoolean("edit") == true){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_popup_edit_program, null);
                Button button1 = dialogView.findViewById(R.id.button1);
                Button button2 = dialogView.findViewById(R.id.button2);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                button1.setOnClickListener(v12 ->{
                    dialog.dismiss();
                });
                button2.setOnClickListener(v1 -> {
                    EditProgram.this.finish();
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }else{
                EditProgram.this.finish();
            }
        }
    }


    @Override
    public void onClick(View v) {

        if(v == save_btn_config){

            String nameInput = name.getText().toString();
            if (nameInput.length() == 0) {
                System.out.println("Min 1 characters");
                name.setError("Min 1 characters");
                return;
            }
            if (nameInput.length() > 5) {
                System.out.println("Max 5 characters");
                name.setError("Max 5 characters");
                return;
            }

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Program newProgram = new Program(
                        name.getText().toString(),
                        low.getProgress(),
                        low_plus.getProgress(),
                        medium.getProgress(),
                        high.getProgress(),
                        high_plus.getProgress(),
                        1,
                        true
                );

                if(extras.getBoolean("new") == false){
                    newProgram.setId(programId);
                    System.out.println(programId);
                    programManager.update(newProgram);
                    programViewModel.Update(newProgram);
                }else{
                    int nextindex = programManager.getNextId();
                    newProgram.setId(nextindex);
                    programManager.addProgram(newProgram);
                    programViewModel.Insert(newProgram);
                    programManager.programadapter.notifyItemInserted(nextindex);
                }
            }

            Intent intent = new Intent(EditProgram.this, HearingProfile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            EditProgram.this.startActivity(intent);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        /*
        if( seekBar.equals(low) ){
            low_txt.setText("" + progress);
        }
        if( seekBar.equals(low_plus) ){
            low_plus_txt.setText("" + progress);
        }
        if( seekBar.equals(medium) ){
            medium_txt.setText("" + progress);
        }
        if( seekBar.equals(high) ){
            high_txt.setText("" + progress);
        }
        if( seekBar.equals(high_plus) ){
            high_plus_txt.setText("" + progress);
        }
         */
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }



    public void updateSliders(Program program){
        name.setText(program.getName());
        low_plus.setProgress(program.getLow_plus());
        low.setProgress(program.getLow());
        medium.setProgress(program.getMiddle());
        high.setProgress(program.getHigh());
        high_plus.setProgress(program.getHigh_plus());
    }



}
