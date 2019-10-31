package a3.audientes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import a3.audientes.R;

public class EnabelBluetooth extends Fragment {
    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View rod = i.inflate(R.layout.activity_programs, container, false);

        return rod;
    }
}
