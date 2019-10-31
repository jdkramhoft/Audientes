package a3.audientes.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import a3.audientes.R;

public class HearingTestAudiogramActivity extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View rod = i.inflate(R.layout.activity_hearing_test_audiogram, container, false);
        Button butt = rod.findViewById(R.id.button);
        butt.setOnClickListener(this);
        return rod;
    }

    @Override
    public void onClick(View v) {
        launchHearingTestScreen();
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
        getFragmentManager().beginTransaction()
                .replace(R.id.emptyFrame, new MainMenu() )
                .addToBackStack(null)
                .commit();

    }


}
