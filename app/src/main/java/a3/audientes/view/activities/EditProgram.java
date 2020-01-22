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
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import a3.audientes.R;
import a3.audientes.dto.Program;
import a3.audientes.dao.ProgramDAO;
import a3.audientes.viewmodel.ProgramViewModel;
import a3.audientes.utils.SharedPrefUtil;

public class EditProgram extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    public static final String EDIT = "edit";
    public static final String NEW = "new";
    public static final String ID = "id";

    private TextView name;
    private SeekBar low_plus, low, medium, high, high_plus;
    private Button save_btn_config;
    private int programId;
    private ProgramDAO programDAO = ProgramDAO.getInstance();
    private ProgramViewModel programViewModel;
    private Equalizer trackEq;
    private MediaPlayer musicTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_program_settings);

        programViewModel = ViewModelProviders.of(this).get(ProgramViewModel.class);
        musicTrack = MediaPlayer.create(this, R.raw.song);
        trackEq = new Equalizer(0, musicTrack.getAudioSessionId());
        trackEq.setEnabled(true);

        for(int i = 0; i < trackEq.getNumberOfBands(); i++){
            trackEq.setBandLevel((short)i, (short)0);
        }

        save_btn_config = findViewById(R.id.save_btn_config);
        save_btn_config.setOnClickListener(this);

        // Removes keyboard on enter pressed
        name = findViewById(R.id.editText);
        name.setOnEditorActionListener((v, actionId, event) -> {
            if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(name.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            }
            return false;
        });

        setupSliders();
        updateLayout();
        musicTrack.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        musicTrack.start();
        updateLayout();
    }

    private void setSliderBehaviour(boolean editDecider, int visibility) {
        low_plus.setEnabled(editDecider);
        low.setEnabled(editDecider);
        medium.setEnabled(editDecider);
        high.setEnabled(editDecider);
        high_plus.setEnabled(editDecider);
        name.setEnabled(editDecider);
        save_btn_config.setVisibility(visibility);
    }

    @Override
    public void onBackPressed() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if(extras.getBoolean("edit")){
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_Dialog);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_popup_edit_program, null);
                Button button1 = dialogView.findViewById(R.id.button1);
                Button button2 = dialogView.findViewById(R.id.button2);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                button1.setOnClickListener(v12 -> dialog.dismiss());
                button2.setOnClickListener(v1 -> {
                    dialog.cancel();
                    musicTrack.stop();
                    finish();
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }else{
                musicTrack.stop();
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v == save_btn_config){
            String nameInput = name.getText().toString();
            if (nameInput.length() == 0) {
                name.setError(getString(R.string.minchar));
                return;
            }
            if (nameInput.length() > 5) {
                name.setError(getString(R.string.maxchar));
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

                if(!extras.getBoolean("new")){
                    newProgram.setId(programId);
                    System.out.println(programId);
                    programDAO.update(newProgram);
                    programViewModel.Update(newProgram);
                }else{
                    int nextindex = programDAO.getNextId();
                    newProgram.setId(nextindex);
                    programDAO.addProgram(newProgram);
                    programViewModel.Insert(newProgram);
                    programDAO.programadapter.notifyItemInserted(nextindex);
                    SharedPrefUtil.saveSetting(this,"currentProgram", Integer.toString(nextindex));
                }
            }

            musicTrack.stop();
            Intent intent = new Intent(EditProgram.this, HearingProfile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            EditProgram.this.finish();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if( seekBar.equals(low) ){
            trackEq.setBandLevel((short)0, (short)(progress - 1500));
        }
        else if( seekBar.equals(low_plus) ){
            trackEq.setBandLevel((short)1, (short)(progress - 1500));
        }
        else if( seekBar.equals(medium) ){
            trackEq.setBandLevel((short)2, (short)(progress - 1500));
        }
        else if( seekBar.equals(high) ){
            trackEq.setBandLevel((short)3, (short)(progress - 1500));
        }
        else if( seekBar.equals(high_plus) ){
            trackEq.setBandLevel((short)4, (short)(progress - 1500));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }

    private void updateLayout() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if(!extras.getBoolean("new")){
                programId = Integer.parseInt(extras.getString("id"));
                updateSliders(programDAO.getProgram(programId));

                if(!extras.getBoolean("edit")){
                    setSliderBehaviour(false, View.INVISIBLE);
                }
                else{
                    setSliderBehaviour(true, View.VISIBLE);
                }
            }
        }
    }

    public void updateSliders(Program program){
        name.setText(program.getName());
        low_plus.setProgress(program.getLow_plus());
        low.setProgress(program.getLow());
        medium.setProgress(program.getMiddle());
        high.setProgress(program.getHigh());
        high_plus.setProgress(program.getHigh_plus());
    }

    private void setupSliders() {
        View low_plus_view = findViewById(R.id.low_plus);
        View low_view = findViewById(R.id.low);
        View medium_view = findViewById(R.id.medium);
        View high_view = findViewById(R.id.high);
        View high_plus_view = findViewById(R.id.high_plus);

        ((TextView) low_plus_view.findViewById(R.id.seekbar_text)).setText(R.string.lowplus);
        ((TextView) low_view.findViewById(R.id.seekbar_text)).setText(R.string.low);
        ((TextView) medium_view.findViewById(R.id.seekbar_text)).setText(R.string.medium);
        ((TextView) high_view.findViewById(R.id.seekbar_text)).setText(R.string.high);
        ((TextView) high_plus_view.findViewById(R.id.seekbar_text)).setText(R.string.highplus);

        low_plus = low_plus_view.findViewById(R.id.seekBar);
        low = low_view.findViewById(R.id.seekBar);
        medium = medium_view.findViewById(R.id.seekBar);
        high = high_view.findViewById(R.id.seekBar);
        high_plus = high_plus_view.findViewById(R.id.seekBar);
        low_plus.setOnSeekBarChangeListener(this);
        low.setOnSeekBarChangeListener(this);
        medium.setOnSeekBarChangeListener(this);
        high.setOnSeekBarChangeListener(this);
        high_plus.setOnSeekBarChangeListener(this);

        short[] levelRange = trackEq.getBandLevelRange();
        short mMinLevel = levelRange[0];
        short mMaxLevel = levelRange[1];

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
    }
}
