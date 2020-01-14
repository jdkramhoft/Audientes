package a3.audientes.view.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import a3.audientes.R;
import a3.audientes.model.AudiogramManager;
import a3.audientes.model.ProgramManager;
import a3.audientes.bluetooth.BluetoothPairingActivity;
import a3.audientes.viewmodel.AudiogramViewModel;
import a3.audientes.viewmodel.ProgramViewModel;
import utils.SharedPrefUtil;

public final class SplashScreen extends Fragment {

    private final Handler handler = new Handler();
    private ProgramViewModel programviewmodel;
    private AudiogramViewModel audiogramViewModel;
    private ProgramManager programManager = ProgramManager.getInstance();
    private AudiogramManager audiogramManager = AudiogramManager.getInstance();
    private boolean newVisitor;
    private int currentAudiogramId;

    private final Runnable splash = () -> {
        if (getActivity()==null) return;
        assert getFragmentManager() != null;

        newVisitor = Boolean.valueOf(SharedPrefUtil.readSharedSetting(getActivity(), getString(R.string.new_visitor_pref), "true"));
        currentAudiogramId = Integer.parseInt(SharedPrefUtil.readSharedSetting(getContext(), "currentAudiogram", "0"));
        System.out.println("Current: "+currentAudiogramId);
        System.out.println(newVisitor);
        if (newVisitor || currentAudiogramId == 0){
            getFragmentManager().beginTransaction()
                    .replace(R.id.emptyFrame, new Onboarding() )
                    .addToBackStack(null)
                    .commit();
        }
        else if (!isHearableConnected()){
            Intent intent = new Intent(getContext(), BluetoothPairingActivity.class);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (savedInstanceState == null){
            // TODO: only onboarding on first visit
            handler.postDelayed(splash, 2000);
        }

        programviewmodel = ViewModelProviders.of(this).get(ProgramViewModel.class);
        programviewmodel.getAllPrograms().observe(this, programs -> {
            System.out.println(programs.size());
            programManager.setProgramList(programs);
        });
        audiogramViewModel = ViewModelProviders.of(this).get(AudiogramViewModel.class);
        audiogramViewModel.getAllAudiogram().observe(this, audiograms -> {
            audiogramManager.setAudiograms(audiograms);

            System.out.println("Audiograms in db: "+audiograms.size());
        });


        return i.inflate(R.layout.splash_screen, container, false);
    }

    private boolean isHearableConnected() {
        // TODO: somehow check if the correct device is already connected
        return false;
    }

}
