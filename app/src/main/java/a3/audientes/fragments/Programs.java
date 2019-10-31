package a3.audientes.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;

import a3.audientes.R;

public class Programs extends Fragment implements View.OnClickListener {

    ImageButton noisy_edit, normal_edit, quiet_edit, cinema_edit;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View rod = i.inflate(R.layout.activity_programs, container, false);

        noisy_edit = rod.findViewById(R.id.noisy_edit);
        noisy_edit.setOnClickListener(this);

        normal_edit = rod.findViewById(R.id.normal_edit);
        normal_edit.setOnClickListener(this);

        quiet_edit = rod.findViewById(R.id.quiet_edit);
        quiet_edit.setOnClickListener(this);

        cinema_edit = rod.findViewById(R.id.cinema_edit);
        cinema_edit.setOnClickListener(this);

        return rod;
    }

    @Override
    public void onClick(View v) {
        if (v == noisy_edit) {
            if (getActivity()==null) return;
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction()
                    .replace(R.id.emptyFrame, new EditProgram() )
                    .addToBackStack(null)
                    .commit();

        } else if (v == normal_edit) {
            if (getActivity()==null) return;
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction()
                    .replace(R.id.emptyFrame, new EditProgram() )
                    .addToBackStack(null)
                    .commit();
        } else if (v == quiet_edit) {
            if (getActivity()==null) return;
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction()
                    .replace(R.id.emptyFrame, new EditProgram() )
                    .addToBackStack(null)
                    .commit();
        } else if (v == cinema_edit) {
            if (getActivity()==null) return;
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction()
                    .replace(R.id.emptyFrame, new EditProgram() )
                    .addToBackStack(null)
                    .commit();
        }
    }
}
