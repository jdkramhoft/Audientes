package a3.audientes.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import a3.audientes.R;

public class SplashScreen extends Fragment implements Runnable  {

    Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View rod = i.inflate(R.layout.activity_splash_screen, container, false);

        if (savedInstanceState == null) {
            handler.postDelayed(this, 1000);
        }
        return rod;
    }

    public void run() {
        if (getActivity()==null) return;
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentindhold, new MainMenu() )
                .commit();
    }
}
