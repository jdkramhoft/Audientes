package a3.audientes.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a3.audientes.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment implements View.OnClickListener {

    private Dialog myDialog;
    private static final String ARG_USER_PROGRAMS = "user_programs";
    private static final String NEW_BUTTON_ID = "newBtn";

    private final Map<Button, Program> btnProgramMap = new HashMap<>();
    private final List<Program> default_programs = new ArrayList<>();
    private final List<Program> user_programs = new ArrayList<>();

    {
        default_programs.add(new Program(25));
        default_programs.add(new Program(50));
        default_programs.add(new Program(75));
        default_programs.add(new Program(100));
    }

    private OnFragmentInteractionListener listener;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user_programs List of stored user defined programs..
     * @return A new instance of fragment Tab1.
     */
    public static Tab1 newInstance(ArrayList<Program> user_programs) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_USER_PROGRAMS, user_programs);
        fragment.setArguments(args);
        return fragment;
    }

    public static Tab1 newInstance() {
        return newInstance(null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            List<Program> saved_instance_user_programs = getArguments().getParcelableArrayList(ARG_USER_PROGRAMS);
            if(saved_instance_user_programs != null)
                user_programs.addAll(saved_instance_user_programs);
        }

        getView();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){
        View root = inflater.inflate(R.layout.fragment_select_modes, container, false);
        //TableLayout table = root.findViewById(R.id.table);
        //table.addView(new Button(getContext()));
        createButtonsProgrammatically(root);
        return root;
    }

    private void createButtonsProgrammatically(View root) {
        List<Program> allPrograms = new ArrayList<>();
        allPrograms.addAll(default_programs);
        allPrograms.addAll(user_programs);
        ButtonCreator bc = new ButtonCreator(root, btnProgramMap, this);
        bc.createButtonsFromPrograms(allPrograms);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onTab1Interaction(uri);
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

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        Program p = btnProgramMap.get(b);


        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_popup, null);
        Button button1 =  (Button)dialogView.findViewById(R.id.button1);
        Button button2=  (Button)dialogView.findViewById(R.id.button2);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Cancel");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Yes");
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        if(p == null)

            System.out.println("Should create new program");
        else
            System.out.println("Pressed program: " + p);
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
        void onTab1Interaction(Uri uri);
    }
}
