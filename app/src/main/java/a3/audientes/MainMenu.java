package a3.audientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import utils.Utils;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {


    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    private boolean isUserFirstTime;

    Button hearingProfile;
    Button settings;
    Button more;
    Button normal;
    Button noisy;
    Button quiet;
    Button cinema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        hearingProfile = findViewById(R.id.hearingProfile);
        hearingProfile.setOnClickListener(this);

        settings = findViewById(R.id.settings);
        settings.setOnClickListener(this);

        more = findViewById(R.id.more);
        more.setOnClickListener(this);

        normal = findViewById(R.id.normal);
        normal.setOnClickListener(this);

        noisy = findViewById(R.id.noisy);
        noisy.setOnClickListener(this);

        quiet = findViewById(R.id.quiet);
        quiet.setOnClickListener(this);

        cinema = findViewById(R.id.cinema);
        cinema.setOnClickListener(this);



        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(MainMenu.this, PREF_USER_FIRST_TIME, "true"));
        Intent introIntent = new Intent(MainMenu.this, OnboardingActivity.class);
        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);


        // TODO: don't forget to uncomment before release!
        //if (isUserFirstTime)
            startActivity(introIntent);


    }

    @Override
    public void onClick(View v) {

        if (v == hearingProfile) {
            Intent intent = new Intent(this, HearingProfil.class);
            startActivity(intent);

        } else if (v == settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);

        } else if (v == more) {
            Intent intent = new Intent(this, Programs.class);
            startActivity(intent);

        } else if (v == normal || v == noisy || v == quiet || v == cinema) {
            switch (v.getId()){
                case R.id.normal:
                    noisy.setBackgroundColor(Color.parseColor("#e3e3e3"));
                    quiet.setBackgroundColor(Color.parseColor("#e3e3e3"));
                    cinema.setBackgroundColor(Color.parseColor("#e3e3e3"));
                    normal.setBackgroundColor(Color.parseColor("#BBBBBB"));
                    break;
                case R.id.noisy:
                    quiet.setBackgroundColor(Color.parseColor("#e3e3e3"));
                    cinema.setBackgroundColor(Color.parseColor("#e3e3e3"));
                    normal.setBackgroundColor(Color.parseColor("#e3e3e3"));
                    noisy.setBackgroundColor(Color.parseColor("#BBBBBB"));
                    break;
                case R.id.quiet:
                    cinema.setBackgroundColor(Color.parseColor("#e3e3e3"));
                    normal.setBackgroundColor(Color.parseColor("#e3e3e3"));
                    noisy.setBackgroundColor(Color.parseColor("#e3e3e3"));
                    quiet.setBackgroundColor(Color.parseColor("#BBBBBB"));
                    break;
                case R.id.cinema:
                    normal.setBackgroundColor(Color.parseColor("#e3e3e3"));
                    noisy.setBackgroundColor(Color.parseColor("#e3e3e3"));
                    quiet.setBackgroundColor(Color.parseColor("#e3e3e3"));
                    cinema.setBackgroundColor(Color.parseColor("#BBBBBB"));
                    break;
            }

        }

    }
}
