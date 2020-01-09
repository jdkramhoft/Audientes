package a3.audientes.view.fragments;


import android.app.Activity;
import android.content.Context;
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
import java.util.Objects;
import a3.audientes.R;
import a3.audientes.model.AudiogramManager;
import a3.audientes.model.Sound;
import a3.audientes.model.SoundManager;
import a3.audientes.view.activities.StartHearingTest;
import a3.audientes.viewmodel.AudiogramViewModel;
import a3.audientes.viewmodel.ProgramViewModel;
import utils.animation.AnimBtnUtil;


public class HearingTest extends Fragment implements View.OnClickListener {

    private int testIndex = 0;
    private int currentIndex = 0;
    private int currentHz = 0;
    private AudiogramManager audiogramManager = AudiogramManager.getInstance();
    private AudiogramViewModel audiogramViewModel;



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
        startTest(testIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rod =  inflater.inflate(R.layout.hearing_test, container, false);
        rod.findViewById(R.id.hearing_button).setOnClickListener(this);
        return rod;
    }


    @Override
    public void onClick(View v) {
        testIndex++;
        if (testIndex == soundManager.getSounds().size()){
            System.out.println("done");
            handler.removeCallbacksAndMessages(null);
            audiogramManager.addIndexToCurrentAudiogram(new int[]{currentHz, currentIndex});
            audiogramManager.getCurrentAudiogram().setId(audiogramManager.getNextId());
            audiogramViewModel.Insert(audiogramManager.getCurrentAudiogram());
            audiogramManager.saveCurrentAudiogram();
            Activity activity = Objects.requireNonNull(getActivity());
            activity.setResult(TEST_OKAY, null);
            activity.finish();
        }
        else{
            AnimBtnUtil.bounce(v, getActivity());
            handler.removeCallbacksAndMessages(null);
            System.out.println(currentIndex);
            System.out.println(currentHz);
            audiogramManager.addIndexToCurrentAudiogram(new int[]{currentHz, currentIndex});
            startTest(testIndex);
        }

    }

    private void startTest(int testIndex){
        for (int i = 0; i <=5 ;i++) {
            int volume = i*2;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentIndex = volume;
                    currentHz = soundManager.getSounds().get(testIndex).getFreqOfTone();
                    playSound(soundManager.getSounds().get(testIndex), volume);
                }
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
}
