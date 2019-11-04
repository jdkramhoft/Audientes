package a3.audientes.fragments;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

import a3.audientes.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectModes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectModes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectModes extends Fragment {

    private static final String ARG_USER_PROGRAMS = "user_programs";

    private final List<Program> default_programs = new ArrayList<>();
    private List<Program> user_programs;

    {
        default_programs.add(new Program(25));
        default_programs.add(new Program(50));
        default_programs.add(new Program(75));
        default_programs.add(new Program(100));
    }

    private OnFragmentInteractionListener listener;

    public SelectModes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user_programs List of stored user defined programs..
     * @return A new instance of fragment SelectModes.
     */
    public static SelectModes newInstance(List<Program> user_programs) {
        SelectModes fragment = new SelectModes();
        Bundle args = new Bundle();
        args.putString(ARG_USER_PROGRAMS, ARG_USER_PROGRAMS);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user_programs = getArguments().getParcelableArrayList(ARG_USER_PROGRAMS);
        }
        getView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_select_modes, container, false);

        List<Program> allPrograms = new ArrayList<>(default_programs);
        if(user_programs != null) allPrograms.addAll(user_programs);

        TableLayout layout = root.findViewById(R.id.table);
        TableRow row = null;
        for (int i = 0; i < allPrograms.size(); i++) { //TODO: Refactor for readability
            if(i % 2 == 0){
                if(row != null){
                    layout.addView(row);
                    TableLayout.LayoutParams layoutParams = (TableLayout.LayoutParams) row.getLayoutParams();
                    layoutParams.leftMargin = getPx(80);
                    //layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                    row.setLayoutParams(layoutParams);
                }
                row = new TableRow(getActivity());
            }
            Program p = allPrograms.get(i);
            Button b = createButton(p); //TODO: Factory? Or builder pattern?
            row.addView(b);
            TableRow.LayoutParams layoutParams = (TableRow.LayoutParams) b.getLayoutParams();

            layoutParams.leftMargin = getPx(10); //TODO: Refactor for dimension
            layoutParams.rightMargin = getPx(10);
            layoutParams.topMargin = getPx(10);
            layoutParams.bottomMargin = getPx(10);
            b.setLayoutParams(layoutParams);
            if(i == allPrograms.size() -1){
                if(i % 2 != 0){
                    layout.addView(row);
                    TableLayout.LayoutParams tableLayoutParams = (TableLayout.LayoutParams) row.getLayoutParams();
                    tableLayoutParams.leftMargin = getPx(100);
                    row.setLayoutParams(tableLayoutParams);
                    row = new TableRow(getActivity());
                    layout.addView(row);
                    TableLayout.LayoutParams lastRow = (TableLayout.LayoutParams) row.getLayoutParams();
                    lastRow.leftMargin = getPx(100);
                    row.setLayoutParams(lastRow);

                    Button lastButton = createButton(new Program(1337));
                    lastButton.setText("+");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        lastButton.setBackground(getResources().getDrawable(R.drawable.two_round_two_sharp_color));
                    }
                    row.addView(lastButton);
                    lastButton.setLayoutParams(layoutParams);
                } else {
                    layout.addView(row);
                    TableLayout.LayoutParams tableLayoutParams = (TableLayout.LayoutParams) row.getLayoutParams();
                    tableLayoutParams.leftMargin = getPx(100);
                    row.setLayoutParams(tableLayoutParams);

                    Button lastButton = createButton(new Program(1337));
                    lastButton.setText("+");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        lastButton.setBackground(getResources().getDrawable(R.drawable.two_round_two_sharp_color));
                    }
                    row.addView(lastButton);
                    lastButton.setLayoutParams(layoutParams);
                }
            }
        }

        return root;
    }

    private int getPx(int dp) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
        return (int) px;
    }

    private Button createButton(Program p) {
        Button b = new Button(getActivity());
        int height = (int) getResources().getDimension(R.dimen.programHeight);
        int width = (int) getResources().getDimension(R.dimen.programWidth);
        b.setHeight(height);
        b.setWidth(width);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { //TODO: Get rid of if, and simply update min API
            b.setBackground(getResources().getDrawable(R.drawable.two_round_two_sharp));
        }
        b.setText(String.valueOf(p.getVolume()));
        return b;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }
}
