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

import java.util.Date;

import a3.audientes.R;
import a3.audientes.model.AudiogramManager;
import a3.audientes.view.activities.AudiogramHistory;
import a3.audientes.view.activities.StartHearingTest;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link } interface
 * to handle interaction events.
 * Use the {@link a3.audientes.view.fragments.Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2 extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener listener;
    private Button take_new_test_btn, audiogram_history_btn;
    private TextView desc;
    private static Fragment child;
    private String date;
    private AudiogramManager audiogramManager = AudiogramManager.getInstance();

    public Tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
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


        // TODO: only display button if any audiograms in db
        audiogram_history_btn = root.findViewById(R.id.audiogram_history_btn);
        audiogram_history_btn.setOnClickListener(this);



        desc = root.findViewById(R.id.audiogram_desc_id);
        // TODO: get audiogramDate from db
        date = audiogramManager.getAudiograms().get(audiogramManager.getAudiograms().size()-1).getDateString();
        desc.setText("Newest audiogram " + date);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        date = audiogramManager.getAudiograms().get(audiogramManager.getAudiograms().size()-1).getDateString();
        desc.setText("Newest audiogram " + date);
    }

    // onViewCreated is called after onCreateView
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, child).commit();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onTab2ParentInteraction(uri);
        }
    }

    public static class test extends Fragment {
        public test() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.start_hearing_test, container,
                    false);

            return view;
        }
    }


    @Override
    public void onClick(View v) {


        if (v == take_new_test_btn){
            System.out.println("hurray, take new test btn clicked");
            /*
            Fragment fragment = new test();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.hearing_button, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            */

            Intent intent = new Intent(getActivity(), StartHearingTest.class);
            startActivity(intent);

        }
        else if (v == audiogram_history_btn){
            // show popup with scrollable cards
            startActivity(new Intent(getContext(), AudiogramHistory.class));




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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTab2ParentInteraction(Uri uri);
    }
}
