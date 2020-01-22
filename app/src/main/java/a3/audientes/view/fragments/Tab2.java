package a3.audientes.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import a3.audientes.R;
import a3.audientes.dao.AudiogramDAO;
import a3.audientes.view.activities.AudiogramHistory;
import a3.audientes.view.activities.StartHearingTest;
import a3.audientes.utils.SharedPrefUtil;

public class Tab2 extends Fragment implements View.OnClickListener {
    private AudiogramDAO audiogramDAO = AudiogramDAO.getInstance();
    private Button take_new_test_btn, audiogram_history_btn;
    private OnFragmentInteractionListener listener;
    private static Fragment child;
    private TextView desc;
    private String date;

    public Tab2() { }

    public static Tab2 newInstance(Fragment child) {
        Tab2.child = child;
        return new Tab2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab2, container, false);
        take_new_test_btn = root.findViewById(R.id.take_new_test_btn);
        take_new_test_btn.setOnClickListener(this);

        audiogram_history_btn = root.findViewById(R.id.audiogram_history_btn);
        audiogram_history_btn.setOnClickListener(this);

        // retrieve active audiogram and set date
        audiogramDAO.setCurrentAudiogramByID(Integer.parseInt(SharedPrefUtil.readSetting(getContext(), "currentAudiogram", "0")));
        date = audiogramDAO.getCurrentAudiogram().getDateString();
        desc = root.findViewById(R.id.audiogram_desc_id);
        desc.setText(String.format("%s\n%s", getString(R.string.selected_audiogram), date));
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        audiogramDAO.setCurrentAudiogramByID(Integer.parseInt(SharedPrefUtil.readSetting(getContext(), "currentAudiogram", "0")));
        date = audiogramDAO.getCurrentAudiogram().getDateString();
        desc.setText(String.format("%s\n%s", getString(R.string.selected_audiogram), date));
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, child).commit();
    }

    public static class test extends Fragment {
        public test() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.hearing_test_start, container, false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == take_new_test_btn){
            Intent intent = new Intent(getActivity(), StartHearingTest.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        else if (v == audiogram_history_btn){
            startActivity(new Intent(getContext(), AudiogramHistory.class));
            Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        void onTab2ParentInteraction(Uri uri);
    }

    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onTab2ParentInteraction(uri);
        }
    }
}
