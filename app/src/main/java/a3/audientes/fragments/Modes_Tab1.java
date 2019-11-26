package a3.audientes.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a3.audientes.R;
import a3.audientes.adapter.ProgramAdapter;
import a3.audientes.models.Program;
import utils.AnimBtnUtil;

public class Modes_Tab1 extends Fragment implements View.OnClickListener {


    private List<Program> programList = new ArrayList<>();
    private ProgramAdapter mAdapter;
    private final Map<Button, a3.audientes.fragments.Program> btnProgramMap = new HashMap<>();
    private final List<a3.audientes.fragments.Program> default_programs = new ArrayList<>();
    private final List<a3.audientes.fragments.Program> user_programs = new ArrayList<>();

    public Modes_Tab1() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new Modes_Tab1();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        programList.add(new a3.audientes.models.Program("test1",1,1,1,1,1,1,false));
        programList.add(new a3.audientes.models.Program("test2",1,1,1,1,1,1,false));
        programList.add(new a3.audientes.models.Program("test3",1,1,1,1,1,1,false));
        programList.add(new a3.audientes.models.Program("test4",1,1,1,1,1,1,false));
        programList.add(new a3.audientes.models.Program("+",1,1,1,1,1,3,false));

        View rod = inflater.inflate(R.layout.fragment_tab1, container, false);
        RecyclerView recyclerView = rod.findViewById(R.id.programRecycler);
        mAdapter = new ProgramAdapter(programList, this, getActivity());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        return rod;
    }

    @Override
    public void onClick(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_popup_edit_program, null);
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


        if(v.getId() == R.id.createProgram){
            AnimBtnUtil.bounce(v, getActivity());
        }
        else{

        }
    }
}
