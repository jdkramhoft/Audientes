package a3.audientes.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import a3.audientes.R;
import utils.Utils;

public class MainMenu extends Fragment implements View.OnClickListener {


    private static final String PREF_USER_FIRST_TIME = "user_first_time";
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    private Button hearingProfile, settings, more, normal, noisy, quiet, cinema;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View rod = i.inflate(R.layout.activity_main_menu, container, false);

        hearingProfile = rod.findViewById(R.id.hearingProfile);
        hearingProfile.setOnClickListener(this);

        settings = rod.findViewById(R.id.settings);
        settings.setOnClickListener(this);

        more = rod.findViewById(R.id.more);
        more.setOnClickListener(this);

        normal = rod.findViewById(R.id.normal);
        normal.setOnClickListener(this);

        noisy = rod.findViewById(R.id.noisy);
        noisy.setOnClickListener(this);

        quiet = rod.findViewById(R.id.quiet);
        quiet.setOnClickListener(this);

        cinema = rod.findViewById(R.id.cinema);
        cinema.setOnClickListener(this);

        //TODO: Check whether any audiogram is stored to determine if user requires setup.
        FragmentActivity activity = getActivity();
        if(activity != null){
            String isUserFirstTime = Utils.readSharedSetting(activity, PREF_USER_FIRST_TIME, TRUE);
            if (isUserFirstTime.equals(TRUE))
                launchHearingTestScreen();
        }
        /*
        Intent introIntent = new Intent(MainMenu.this, OnboardingActivity.class);
        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);
        */
        return rod;
    }

    @Override
    public void onClick(View v) {

        if (v == hearingProfile) {
            /*
            Intent intent = new Intent(this, HearingProfile.class);
            startActivity(intent);
            */
            if (getActivity()==null) return;
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction()
                    .replace(R.id.emptyFrame, new HearingProfile() )
                    .addToBackStack(null)
                    .commit();
        } else if (v == settings) {
            /*
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            */
            if (getActivity()==null) return;
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction()
                    .replace(R.id.emptyFrame, new Settings() )
                    .addToBackStack(null)
                    .commit();
        } else if (v == more) {
            /*
            Intent intent = new Intent(this, Programs.class);
            startActivity(intent);
            */
            if (getActivity()==null) return;
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction()
                    .replace(R.id.emptyFrame, new Programs() )
                    .addToBackStack(null)
                    .commit();

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
    private void launchHearingTestScreen(){
        /*
        Utils.saveSharedSetting(OnboardingActivity.this, MainMenu.PREF_USER_FIRST_TIME, "false");

        Intent hearingTestIntent = new Intent(this, BeginHearingTestActivity.class);
        hearingTestIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(hearingTestIntent);
        finish();
        */
        if (getActivity()==null) return;
        assert getFragmentManager() != null;

        Utils.saveSharedSetting(getActivity(), MainMenu.PREF_USER_FIRST_TIME, FALSE);

        getFragmentManager().beginTransaction()
                .replace(R.id.emptyFrame, new OnboardingActivity())
                .addToBackStack(null)
                .commit();

    }
}
