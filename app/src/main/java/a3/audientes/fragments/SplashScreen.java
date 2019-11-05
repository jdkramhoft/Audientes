package a3.audientes.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import a3.audientes.R;
import a3.audientes.activities.HearingProfile;

public final class SplashScreen extends Fragment {

    private final Handler handler = new Handler();
    private final Runnable splash = () -> {

        if (getActivity()==null) return;
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction()
                .replace(R.id.emptyFrame, new Onboarding() )
                .addToBackStack(null)
                .commit();

    };

    @Override
    public View onCreateView(@NonNull LayoutInflater i, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null)
            handler.postDelayed(splash, 1000);

        return i.inflate(R.layout.splash_screen, container, false);
    }

}
