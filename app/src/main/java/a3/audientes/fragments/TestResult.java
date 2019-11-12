package a3.audientes.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import a3.audientes.R;

public class TestResult extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View rod = i.inflate(R.layout.test_result, container, false);
        Button butt = rod.findViewById(R.id.button);
        butt.setOnClickListener(this);
        return rod;
    }

    @Override
    public void onClick(View v) {
        launchHearingTestScreen();
    }
    private void launchHearingTestScreen(){

        /* TODO: change X in .replace( , X)
        if (getActivity()==null) return;
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction()
                .replace(R.id.emptyFrame, new MainMenu() )
                .addToBackStack(null)
                .commit();

         */
    }


}
