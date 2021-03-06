package a3.audientes.view.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.Date;

import a3.audientes.R;
import a3.audientes.dto.Audiogram;
import a3.audientes.dao.AudiogramDAO;
import a3.audientes.dto.Program;
import a3.audientes.dao.ProgramDAO;
import a3.audientes.dto.Sound;
import a3.audientes.dao.SoundDAO;
import a3.audientes.viewmodel.AudiogramViewModel;
import a3.audientes.viewmodel.ProgramViewModel;
import a3.audientes.utils.SharedPrefUtil;
import a3.audientes.utils.animation.AnimBtnUtil;


public class HearingTest extends AppCompatActivity implements View.OnClickListener {

    private static final int SOUND_AMOUNT = 10;
    private int testIndex = 0;
    private int currentIndex = 1;
    private int currentHz = 0;
    private AudiogramDAO audiogramDAO = AudiogramDAO.getInstance();
    private ProgramDAO programDAO = ProgramDAO.getInstance();
    private ProgramViewModel programViewModel;
    private AudiogramViewModel audiogramViewModel;
    private ImageButton testButton;
    private StepView stepView;
    private final int ONE_SECOND = 1000;
    private AlertDialog dialog;
    private AudioTrack audioTrack;
    private boolean isRunning = true;
    private static final int NUM_OF_LEVELS = 5;
    private SoundDAO soundDAO = SoundDAO.getInstance();
    private final Handler handler = new Handler();


    @Override
    public void onResume() {
        super.onResume();
        audiogramDAO.resetAudiogram();
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null);
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hearing_test);
        audiogramDAO.resetAudiogram();
        audiogramViewModel = ViewModelProviders.of(this).get(AudiogramViewModel.class);
        programViewModel = ViewModelProviders.of(this).get(ProgramViewModel.class);
        testButton = findViewById(R.id.hearing_button);
        testButton.setOnClickListener(this);
        startTest(testIndex);
        autoChangeSound();
        stepView = findViewById(R.id.step_view);
        stepView.getState()
                .animationType(StepView.ANIMATION_LINE)
                .steps(new ArrayList<String>() {{
                    add("Low");
                    add("Low+");
                    add("Medium");
                    add("High");
                    add("High+");
                }})
                .stepsNumber(5)
                .animationDuration(ONE_SECOND)
                .commit();
    }

    @Override
    public void onClick(View v) {
        goToNextSound.run();
        if(testIndex != soundDAO.getSounds().size()){
            AnimBtnUtil.bounce(v, this);
        }
    }

    private final Runnable goToNextSound = () -> {
        if(!isRunning) {
            return;
        }

        testIndex++;
        testButton.setEnabled(false);
        handler.removeCallbacksAndMessages(null);
        if(testIndex != soundDAO.getSounds().size()){
            handler.postDelayed(() -> testButton.setEnabled(true), ONE_SECOND);
        }
        stepView.go(testIndex, true);

        if (testIndex == soundDAO.getSounds().size()){
            isRunning = false;
            stepView.done(true);
            alertDialog();
        }
        else{
            audiogramDAO.addIndexToCurrentAudiogram(new int[]{currentHz, currentIndex});
            startTest(testIndex);
            autoChangeSound();
        }
    };

    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_Dialog);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_popup_hearing_test_ended, null);
        Button okay = dialogView.findViewById(R.id.okay);
        builder.setView(dialogView);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        okay.setOnClickListener(v12 -> {
            updateDAO();
            dialog.cancel();
            Intent i = new Intent(getBaseContext(), HearingProfile.class);
            i.putExtra("ARG_PAGE", 1);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
    }
    private void updateDAO() {
        audiogramDAO.addIndexToCurrentAudiogram(new int[]{currentHz, currentIndex});
        audiogramDAO.getCurrentAudiogram().setId(audiogramDAO.getNextId());
        audiogramDAO.getCurrentAudiogram().setDate(new Date());
        audiogramViewModel.Insert(audiogramDAO.getCurrentAudiogram());
        audiogramDAO.saveCurrentAudiogram();
        SharedPrefUtil.saveSetting(getBaseContext(),"currentAudiogram", Integer.toString(audiogramDAO.getCurrentAudiogram().getId()));
        updateDefualtPrograms(audiogramDAO.getCurrentAudiogram());
    }


    private void autoChangeSound(){
        handler.postDelayed(goToNextSound, ONE_SECOND * (SOUND_AMOUNT + 1));
    }

    private void startTest(int testIndex){
        for (int i = 1; i <= SOUND_AMOUNT ;i++) {
            int volume = i;
            handler.postDelayed(() -> {
                currentIndex = volume;
                currentHz = soundDAO.getSounds().get(testIndex).getFreqOfTone();
                playSound(soundDAO.getSounds().get(testIndex), volume);
            }, ONE_SECOND * i);
        }
    }


    private void playSound(Sound sound, int volume){
        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        assert audioManager != null;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);

        if(audioTrack == null){
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    sound.getSampleRate(), AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, sound.getGeneratedSnd().length,
                    AudioTrack.MODE_STATIC);
        } else {
            audioTrack.stop();
        }

        audioTrack.write(sound.getGeneratedSnd(), 0, sound.getGeneratedSnd().length);
        audioTrack.play();

    }

    private void updateDefualtPrograms(Audiogram audiogram){
        for(int i = 1; i <= 4; i++){
            Program program = programDAO.getProgram(i);
            ArrayList<Integer> y = audiogram.getY();
            if (y.size() < 5){
                program.setLow(5);
                program.setLow_plus(5);
                program.setMiddle(5);
                program.setHigh(5);
                program.setHigh_plus(5);
            }
            else {
                program.setLow(programDAO.defaultLevel(y.get(0),i));
                program.setLow_plus(programDAO.defaultLevel(y.get(1),i));
                program.setMiddle(programDAO.defaultLevel(y.get(2),i));
                program.setHigh(programDAO.defaultLevel(y.get(3),i));
                program.setHigh_plus(programDAO.defaultLevel(y.get(4),i));
            }
            programDAO.update(program);
            programViewModel.Update(program);
        }
    }


    public AlertDialog getLatestDialog(){
        return dialog;
    }

    public int getNumOfLevels() {
        return NUM_OF_LEVELS;
    }
}
