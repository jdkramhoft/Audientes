package a3.audientes.view.fragments;


import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import a3.audientes.R;
import a3.audientes.model.ProgramManager;
import a3.audientes.bluetooth.BluetoothPairingActivity;
import a3.audientes.viewmodel.ProgramViewModel;

public final class SplashScreen extends Fragment {

    private final Handler handler = new Handler();
    private ProgramViewModel programviewmodel;
    private ProgramManager programManager = ProgramManager.getInstance();

    private final Runnable splash = () -> {
        if (getActivity()==null) return;
        assert getFragmentManager() != null;

        // first visit onboarding

        if (true){
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

        return i.inflate(R.layout.splash_screen, container, false);
    }

    private boolean isHearableConnected() {
        // TODO: somehow check if the correct device is already connected
        return false;
    }

}
