package a3.audientes.activities;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import a3.audientes.R;
import a3.audientes.fragments.OnboardingActivity;
import a3.audientes.fragments.SplashScreen;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
/*
        if (savedInstanceState == null) {
            //Fragment fragment = new SplashScreen();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.topFrame, new OnboardingActivity())
                    .add(R.id.middleFrame, new OnboardingActivity())
                    .add(R.id.bottomFrame, new OnboardingActivity())
                    .commit();
        }

        //setTitle("MainActivity");

 */
    }

}
