package a3.audientes.activities;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import a3.audientes.R;

public class Main extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
/*
        if (savedInstanceState == null) {
            //Fragment fragment = new SplashScreen();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.topFrame, new Onboarding())
                    .add(R.id.middleFrame, new Onboarding())
                    .add(R.id.bottomFrame, new Onboarding())
                    .commit();
        }

        //setTitle("Main");

 */
    }

}
