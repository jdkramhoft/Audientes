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

public class BeginHearingTestActivity extends Fragment implements View.OnClickListener {

    private Button mBeginBtn;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View rod = i.inflate(R.layout.activity_begin_hearing_test, container, false);

        mBeginBtn = rod.findViewById(R.id.begin_test_btn);
        mBeginBtn.setOnClickListener(this);
        return rod;
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
                    .replace(R.id.fragmentindhold, new HearingTest() )
                    .addToBackStack(null)
                    .commit();
        }
    }
}
