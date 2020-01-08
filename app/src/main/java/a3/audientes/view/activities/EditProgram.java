package a3.audientes.view.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Equalizer.OnParameterChangeListener;
import android.net.Uri;
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
import a3.audientes.model.AudiogramManager;
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
    private Equalizer trackEq;
    private MediaPlayer musicTrack;
    private int resumePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_program);

        programViewModel = ViewModelProviders.of(this).get(ProgramViewModel.class);

        // Setting up Equalizer
        musicTrack = MediaPlayer.create(this, R.raw.song);
        trackEq = new Equalizer(0, musicTrack.getAudioSessionId());
        trackEq.setEnabled(true);
        int noPresets = trackEq.getNumberOfPresets();
        System.out.println("Modes:"+noPresets);
        short[] levelRange = trackEq.getBandLevelRange();
        short mMinLevel = levelRange[0];
        short mMaxLevel = levelRange[1];
        System.out.println("Min:"+mMinLevel);
        System.out.println("Max:"+mMaxLevel);
        int bands = trackEq.getNumberOfBands();
        System.out.println("Num of bands:"+bands);

        int centerBand = trackEq.getCenterFreq((short)0);
        System.out.println("Center:"+centerBand);

        for(int i = 0; i < bands; i++){
            trackEq.setBandLevel((short)i, (short)0);
            int[] bandHz = trackEq.getBandFreqRange((short)i);
            System.out.println("HZ band"+i+": "+bandHz[0]+" - "+bandHz[1]);
            short bandLevel = trackEq.getBandLevel((short)i);
            System.out.println("BandLevel: "+bandLevel+"\n");
        }


        low_plus_txt = findViewById(R.id.low_plus).findViewById(R.id.seekbar_text);
        low_txt = findViewById(R.id.low).findViewById(R.id.seekbar_text);
        medium_txt = findViewById(R.id.medium).findViewById(R.id.seekbar_text);
        high_txt = findViewById(R.id.high).findViewById(R.id.seekbar_text);
        high_plus_txt = findViewById(R.id.high_plus).findViewById(R.id.seekbar_text);

        low_plus = findViewById(R.id.low_plus).findViewById(R.id.seekbar);
        low = findViewById(R.id.low).findViewById(R.id.seekbar);
        medium = findViewById(R.id.medium).findViewById(R.id.seekbar);
        high = findViewById(R.id.high).findViewById(R.id.seekbar);
        high_plus = findViewById(R.id.high_plus).findViewById(R.id.seekbar);

        low_plus.setMax(mMaxLevel - mMinLevel);
        low.setMax(mMaxLevel - mMinLevel);
        medium.setMax(mMaxLevel - mMinLevel);
        high.setMax(mMaxLevel - mMinLevel);
        high_plus.setMax(mMaxLevel - mMinLevel);

        low_plus.setProgress(mMaxLevel);
        low.setProgress(mMaxLevel);
        medium.setProgress(mMaxLevel);
        high.setProgress(mMaxLevel);
        high_plus.setProgress(mMaxLevel);

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


        // Start music
        musicTrack.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        musicTrack.start();
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
        musicTrack.stop();
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
                        Integer.parseInt(low_txt.getText().toString()),
                        Integer.parseInt(low_plus_txt.getText().toString()),
                        Integer.parseInt(medium_txt.getText().toString()),
                        Integer.parseInt(high_txt.getText().toString()),
                        Integer.parseInt(high_plus_txt.getText().toString()),
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

        if( seekBar.equals(low) ){
            trackEq.setBandLevel((short)0, (short)(progress - 1500));
            short bandLevel = trackEq.getBandLevel((short)0);
            System.out.println("BandLevel: 0 "+bandLevel);
            low_txt.setText("" + progress);
        }
        if( seekBar.equals(low_plus) ){
            trackEq.setBandLevel((short)1, (short)(progress - 1500));
            short bandLevel = trackEq.getBandLevel((short)1);
            System.out.println("BandLevel: 1 "+bandLevel);
            low_plus_txt.setText("" + progress);
        }
        if( seekBar.equals(medium) ){
            trackEq.setBandLevel((short)2, (short)(progress - 1500));
            short bandLevel = trackEq.getBandLevel((short)2);
            System.out.println("BandLevel: 2 "+bandLevel);
            medium_txt.setText("" + progress);
        }
        if( seekBar.equals(high) ){
            trackEq.setBandLevel((short)3, (short)(progress - 1500));
            short bandLevel = trackEq.getBandLevel((short)3);
            System.out.println("BandLevel: 3 "+bandLevel);
            high_txt.setText("" + progress);
        }
        if( seekBar.equals(high_plus) ){
            trackEq.setBandLevel((short)4, (short)(progress - 1500));
            short bandLevel = trackEq.getBandLevel((short)4);
            System.out.println("BandLevel: 4 "+bandLevel);
            high_plus_txt.setText("" + progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        /*
        if( seekBar.equals(low) ){
            musicTrack.pause();
            resumePosition = musicTrack.getCurrentPosition();
        }
        if( seekBar.equals(low_plus) ){
            musicTrack.pause();
            resumePosition = musicTrack.getCurrentPosition();
        }
        if( seekBar.equals(medium) ){
            musicTrack.pause();
            resumePosition = musicTrack.getCurrentPosition();
        }
        if( seekBar.equals(high) ){
            musicTrack.pause();
            resumePosition = musicTrack.getCurrentPosition();
        }
        if( seekBar.equals(high_plus) ){
            musicTrack.pause();
            resumePosition = musicTrack.getCurrentPosition();
        }

         */
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        /*
        if( seekBar.equals(low) ){
            musicTrack.seekTo(resumePosition);
            musicTrack.start();
        }
        if( seekBar.equals(low_plus) ){
            musicTrack.seekTo(resumePosition);
            musicTrack.start();
        }
        if( seekBar.equals(medium) ){
            musicTrack.seekTo(resumePosition);
            musicTrack.start();
        }
        if( seekBar.equals(high) ){
            musicTrack.seekTo(resumePosition);
            musicTrack.start();
        }
        if( seekBar.equals(high_plus) ){
            musicTrack.seekTo(resumePosition);
            musicTrack.start();
        }

         */

    }



    public void updateSliders(Program program){
        name.setText(program.getName());
        low_plus_txt.setText(String.valueOf(program.getLow_plus()));
        low_txt.setText(String.valueOf(program.getLow()));
        medium_txt.setText(String.valueOf(program.getMiddle()));
        high_txt.setText(String.valueOf(program.getHigh()));
        high_plus_txt.setText(String.valueOf(program.getHigh_plus()));

        low_plus.setProgress(program.getLow_plus());
        low.setProgress(program.getLow());
        medium.setProgress(program.getMiddle());
        high.setProgress(program.getHigh());
        high_plus.setProgress(program.getHigh_plus());
    }




}
