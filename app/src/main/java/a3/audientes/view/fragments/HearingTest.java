package a3.audientes.view.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import a3.audientes.R;
import a3.audientes.model.Audiogram;
import a3.audientes.model.AudiogramManager;
import a3.audientes.model.Program;
import a3.audientes.model.ProgramManager;
import a3.audientes.model.Sound;
import a3.audientes.model.SoundManager;
import a3.audientes.view.activities.HearingProfile;
import a3.audientes.viewmodel.AudiogramViewModel;
import a3.audientes.viewmodel.ProgramViewModel;
import utils.SharedPrefUtil;
import utils.animation.AnimBtnUtil;


public class HearingTest extends Fragment implements View.OnClickListener {

    private int testIndex = 0;
    private int currentIndex = 1;
    private int currentHz = 0;
    private AudiogramManager audiogramManager = AudiogramManager.getInstance();
    private ProgramManager programManager = ProgramManager.getInstance();
    private ProgramViewModel programViewModel;
    private AudiogramViewModel audiogramViewModel;
    private ImageButton testButton;

    public static final int HEARING_TEST = 1;
    public static final int TEST_OKAY = 13;
    public static final int TEST_NOT_COMPLETE = 37;
    // Sound
   SoundManager soundManager = SoundManager.getInstance();

    Handler handler = new Handler();

    public HearingTest() {}

    public static HearingTest newInstance(String param1, String param2) {
        HearingTest fragment = new HearingTest();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        audiogramManager.resetAudiogram();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audiogramManager.resetAudiogram();
        audiogramViewModel = ViewModelProviders.of(this).get(AudiogramViewModel.class);
        programViewModel = ViewModelProviders.of(this).get(ProgramViewModel.class);
        startTest(testIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rod =  inflater.inflate(R.layout.hearing_test, container, false);
        testButton = rod.findViewById(R.id.hearing_button);
        testButton.setOnClickListener(this);
        return rod;
    }


    @Override
    public void onClick(View v) {
        testIndex++;
        testButton.setEnabled(false);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(() -> {
            testButton.setEnabled(true);
        }, 1000);

        if (testIndex == soundManager.getSounds().size()){
            System.out.println("done");
            System.out.println("Index: "+currentIndex);
            System.out.println("Hz: "+currentHz);
            audiogramManager.addIndexToCurrentAudiogram(new int[]{currentHz, currentIndex});
            audiogramManager.getCurrentAudiogram().setId(audiogramManager.getNextId());
            audiogramManager.getCurrentAudiogram().setDate(new Date());
            audiogramViewModel.Insert(audiogramManager.getCurrentAudiogram());
            audiogramManager.saveCurrentAudiogram();
            SharedPrefUtil.saveSharedSetting(getContext(),"currentAudiogram", Integer.toString(audiogramManager.getCurrentAudiogram().getId()));
            updateDefualtPrograms(audiogramManager.getCurrentAudiogram());
            Activity activity = Objects.requireNonNull(getActivity());
            activity.setResult(TEST_OKAY, null);
            Intent i = new Intent(getContext(), HearingProfile.class);
            i.putExtra("ARG_PAGE", 1);
            startActivity(i);
            activity.finish();
        }
        else{
            AnimBtnUtil.bounce(v, getActivity());
            System.out.println("Index: "+currentIndex);
            System.out.println("Hz: "+currentHz);
            audiogramManager.addIndexToCurrentAudiogram(new int[]{currentHz, currentIndex});
            startTest(testIndex);
        }

    }

    private void startTest(int testIndex){
        for (int i = 1; i <=10 ;i++) {
            int volume = i;
            handler.postDelayed(() -> {
                currentIndex = volume;
                currentHz = soundManager.getSounds().get(testIndex).getFreqOfTone();
                playSound(soundManager.getSounds().get(testIndex), volume);
            }, 1000 * i);
        }
    }



    private void playSound(Sound sound, int volume){
        if (getActivity() == null) return;
        AudioManager audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
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
            Program program = programManager.getProgram(i);
            System.out.println(program.getId());
            ArrayList<Integer> y = audiogram.getY();
            program.setLow(programManager.defaultLevel(y.get(0),i));
            program.setLow_plus(programManager.defaultLevel(y.get(1),i));
            program.setMiddle(programManager.defaultLevel(y.get(2),i));
            program.setHigh(programManager.defaultLevel(y.get(3),i));
            program.setHigh_plus(programManager.defaultLevel(y.get(4),i));
            programManager.updateDefault(program);
            programViewModel.Update(program);
        }
    }
}
