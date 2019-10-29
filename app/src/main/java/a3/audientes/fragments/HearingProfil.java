package a3.audientes.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import a3.audientes.R;
import a3.audientes.fragments.BeginHearingTestActivity;

public class HearingProfil extends Fragment implements View.OnClickListener {

    Button hearing_test, oldTest, thisHearing, maint_and_repair;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View rod = i.inflate(R.layout.activity_hearing_profil, container, false);

        hearing_test = rod.findViewById(R.id.hearing_test);
        hearing_test.setOnClickListener(this);

        return rod;
    }

    @Override
    public void onClick(View v) {

        if ( v == hearing_test) {
            /*
            Intent intent = new Intent(this, BeginHearingTestActivity.class);
            startActivity(intent);
            */
            if (getActivity()==null) return;
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragmentindhold, new BeginHearingTestActivity() )
                    .addToBackStack(null)
                    .commit();
        }
    }
}
