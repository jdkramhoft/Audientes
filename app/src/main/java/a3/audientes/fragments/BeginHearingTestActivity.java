package a3.audientes.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import a3.audientes.R;

public class BeginHearingTestActivity extends Fragment implements View.OnClickListener {


    private TextView running_test_text, hearing_test_title;
    private Button mBeginBtn;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View v = i.inflate(R.layout.fragment_start_hearing_test, container, false);
        //View rod = i.inflate(R.layout.fragment_start_hearing_test, container, false);

        Fragment childFragment = new OnboardingActivity();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, childFragment).commit();

        mBeginBtn = v.findViewById(R.id.begin_test_btn);
        mBeginBtn.setOnClickListener(this);

        running_test_text = v.findViewById(R.id.running_test_text);

        hearing_test_title = v.findViewById(R.id.hearing_test_title);


        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == mBeginBtn){
            /*
            Intent intent = new Intent(this, HearingTest.class);
            startActivity(intent);
            */
            if (getActivity()==null) return;
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction()
                    .replace(R.id.emptyFrame, new HearingTest() )
                    .addToBackStack(null)
                    .commit();
        }
    }
}
