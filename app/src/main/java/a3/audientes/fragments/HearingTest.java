package a3.audientes.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import a3.audientes.R;

public class HearingTest extends Fragment implements View.OnClickListener {

    private Button mHeardSoundBtn, mRestartBtn, mCancelBtn;
    //private ImageView circle1, circle2;
    private int numOfTests = 0;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View rod = i.inflate(R.layout.activity_hearing_test, container, false);

        mHeardSoundBtn = rod.findViewById(R.id.heard_sound_btn);
        mHeardSoundBtn.setOnClickListener(this);

        mRestartBtn = rod.findViewById(R.id.restart_btn);
        mRestartBtn.setOnClickListener(this);

        mCancelBtn = rod.findViewById(R.id.cancel_btn);
        mCancelBtn.setOnClickListener(this);

        //circle1 = findViewById(R.id.circle1);
        //circle1.setVisibility(View.GONE);

        //circle2 = findViewById(R.id.circle2);
        //circle2.setVisibility(View.GONE);


        // TODO: play test sound after a few seconds

        return rod;
    }



    @Override
    public void onClick(View v) {
        if (v == mHeardSoundBtn){

            //Circles around button when it is pressed - Marina
            /*
            circle1.setVisibility(View.VISIBLE);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            circle2.setVisibility(View.VISIBLE);
            */

            // TODO: sound is heard, log data
            numOfTests = numOfTests + 1;



            // TODO: if end of test, calculate audiogram and redirect user to audiogram activity
            if (numOfTests == 2){
                /*
                Intent i = new Intent(this, HearingTestAudiogramActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                 */
                if (getActivity()==null) return;
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction()
                        .replace(R.id.emptyFrame, new HearingTestAudiogramActivity() )
                        .addToBackStack(null)
                        .commit();
            }
            else {
                // TODO: else call next heard_sound method from interface

            }

        }
        else if (v == mRestartBtn){
            // TODO: erase saved data and init test from interface
            /*
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Test is restarted",
                    Toast.LENGTH_SHORT);
            toast.show();
            */
        }
        else if (v == mCancelBtn){
            /*
            Intent i = new Intent(this, MainMenu.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            */
        }
    }
}
