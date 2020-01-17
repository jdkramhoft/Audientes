package a3.audientes.view.activities;


import android.app.Activity;
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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
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
    AlertDialog dialog;
    public static final int HEARING_TEST = 1;
    public static final int TEST_OKAY = 13;
    public static final int TEST_NOT_COMPLETE = 37;
    // Sound
    SoundDAO soundDAO = SoundDAO.getInstance();

    Handler handler = new Handler();

    public HearingTest() {}

    @Override
    public void onResume() {
        super.onResume();
        audiogramDAO.resetAudiogram();
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null);
        HearingTest.this.finish();
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

        if(v == testButton){
            testIndex++;
            testButton.setEnabled(false);
            handler.removeCallbacksAndMessages(null);
            if(testIndex != soundDAO.getSounds().size()){
                handler.postDelayed(() -> testButton.setEnabled(true), ONE_SECOND);
            }
            stepView.go(testIndex, true);

            if (testIndex == soundDAO.getSounds().size()){
                testButton.setEnabled(false);
                stepView.done(true);
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
                    dialog.cancel();
                    audiogramDAO.addIndexToCurrentAudiogram(new int[]{currentHz, currentIndex});
                    audiogramDAO.getCurrentAudiogram().setId(audiogramDAO.getNextId());
                    audiogramDAO.getCurrentAudiogram().setDate(new Date());
                    audiogramViewModel.Insert(audiogramDAO.getCurrentAudiogram());
                    audiogramDAO.saveCurrentAudiogram();
                    SharedPrefUtil.saveSharedSetting(getBaseContext(),"currentAudiogram", Integer.toString(audiogramDAO.getCurrentAudiogram().getId()));
                    updateDefualtPrograms(audiogramDAO.getCurrentAudiogram());
                    Activity activity = Objects.requireNonNull(this);
                    activity.setResult(TEST_OKAY, null);
                    Intent i = new Intent(getBaseContext(), HearingProfile.class);
                    i.putExtra("ARG_PAGE", 1);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    activity.finish();
                });
            }
            else{
                AnimBtnUtil.bounce(v, this);
                audiogramDAO.addIndexToCurrentAudiogram(new int[]{currentHz, currentIndex});
                startTest(testIndex);
            }
        }
    }

    private void startTest(int testIndex){
        for (int i = 1; i <=10 ;i++) {
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

        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sound.getSampleRate(), AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, sound.getGeneratedSnd().length,
                AudioTrack.MODE_STATIC);
        audioTrack.write(sound.getGeneratedSnd(), 0, sound.getGeneratedSnd().length);
        audioTrack.play();
    }

    private void updateDefualtPrograms(Audiogram audiogram){
        for(int i = 1; i <= 4; i++){
            Program program = programDAO.getProgram(i);
            System.out.println(program.getId());
            ArrayList<Integer> y = audiogram.getY();
            program.setLow(programDAO.defaultLevel(y.get(0),i));
            program.setLow_plus(programDAO.defaultLevel(y.get(1),i));
            program.setMiddle(programDAO.defaultLevel(y.get(2),i));
            program.setHigh(programDAO.defaultLevel(y.get(3),i));
            program.setHigh_plus(programDAO.defaultLevel(y.get(4),i));
            programDAO.updateDefault(program);
            programViewModel.Update(program);
        }
    }
}
