package a3.audientes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a3.audientes.EditProgram;
import a3.audientes.R;
import a3.audientes.adapter.ProgramAdapter;
import a3.audientes.models.Program;
import utils.AnimBtnUtil;

public class Modes_Tab1 extends Fragment implements View.OnClickListener, View.OnLongClickListener {


    private List<Program> programList = new ArrayList<>();
    private ProgramAdapter mAdapter;
    private final Map<Button, a3.audientes.fragments.Program> btnProgramMap = new HashMap<>();
    private final List<a3.audientes.fragments.Program> default_programs = new ArrayList<>();
    private final List<a3.audientes.fragments.Program> user_programs = new ArrayList<>();
    private final int DEFAULT_PROGRAMS = 4;


    private View addBtn, mbtn1, mbtn2, mbtn3, mbtn4, prevProgram;

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

        //TODO for testing only
        addfakelist();
        Program p;
        for (int defaultProgram = 1; defaultProgram <= DEFAULT_PROGRAMS; defaultProgram++){
            p = new Program("test"+defaultProgram,1,1,1,1,1,1,false);
            programList.add(p);
        }
        p = new Program("test5",1,1,1,1,1,2,false);
        programList.add(p);


        View rod = inflater.inflate(R.layout.fragment_tab1, container, false);
        setupbuttons(rod);
        setupRecyclerView(rod);

        return rod;
    }

    @Override
    public void onClick(View v) {
        if (v == addBtn){
            // TODO: send program data to be edited with activity
            Intent intent = new Intent(getActivity(), EditProgram.class);
            startActivity(intent);
        }
        else {
            ImageView imageView;
            if (prevProgram != null) {
                // restore prev program layout
                imageView = prevProgram.findViewById(R.id.program_bg_id);

                if (isProgramDefault(prevProgram)) setDefaultLayout(prevProgram, imageView);
                else {
                    prevProgram.findViewById(R.id.canceltext).setVisibility(View.VISIBLE);
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.xml_program, null));
                }
            }

            // set layout when program is selected
            imageView = v.findViewById(R.id.program_bg_id);

            if (isProgramDefault(v)) setDefaultLayout(v, imageView);
            else v.findViewById(R.id.canceltext).setVisibility(View.GONE);

            imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_image_leftear_max, null));

            prevProgram = v;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        System.out.println("Long click");
        if (v.getId() != R.id.canceltext){
            AnimBtnUtil.bounceSlow(v, getActivity());
            startActivity(new Intent(getActivity(), EditProgram.class));
            // TODO: send program data to be edited with activity
        }
        else {
            // TODO: delete program
        }

        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_popup_edit_program, null);
        Button button1 = dialogView.findViewById(R.id.button1);
        Button button2 = dialogView.findViewById(R.id.button2);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        button1.setOnClickListener(v12 -> dialog.dismiss());
        button2.setOnClickListener(v1 -> {
            startActivity(new Intent(getActivity(), EditProgram.class));
            dialog.dismiss();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

         */
        return true;
    }

    public void setupbuttons(View rod){
        addBtn = rod.findViewById(R.id.addprogram_btn);
        mbtn1 = rod.findViewById(R.id.mainbtn1);
        mbtn2 = rod.findViewById(R.id.mainbtn2);
        mbtn3 = rod.findViewById(R.id.mainbtn3);
        mbtn4 = rod.findViewById(R.id.mainbtn4);

        addBtn.setOnClickListener(this);
        mbtn1.setOnClickListener(this);
        mbtn2.setOnClickListener(this);
        mbtn3.setOnClickListener(this);
        mbtn4.setOnClickListener(this);

        //addBtn.setOnLongClickListener(this);
        mbtn1.setOnLongClickListener(this);
        mbtn2.setOnLongClickListener(this);
        mbtn3.setOnLongClickListener(this);
        mbtn4.setOnLongClickListener(this);

    }
    public void setupRecyclerView(View rod){
        RecyclerView recyclerView = rod.findViewById(R.id.programRecycler);
        mAdapter = new ProgramAdapter(programList, this, this, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    public void addfakelist(){
        programList.add(new a3.audientes.models.Program("test1",1,1,1,1,1,1,false));
        programList.add(new a3.audientes.models.Program("test2",1,1,1,1,1,1,false));
        programList.add(new a3.audientes.models.Program("test3",1,1,1,1,1,1,false));
        programList.add(new a3.audientes.models.Program("test4",1,1,1,1,1,1,false));
        programList.add(new a3.audientes.models.Program("+",1,1,1,1,1,3,false));

    }

    private void setDefaultLayout(View v, ImageView imageView) {
        if (v == mbtn1) imageView.setImageDrawable(getResources().getDrawable(R.drawable.xml_program_upleft, null));
        else if (v == mbtn2) imageView.setImageDrawable(getResources().getDrawable(R.drawable.xml_program_upright, null));
        else if (v == mbtn3) imageView.setImageDrawable(getResources().getDrawable(R.drawable.xml_program_botleft, null));
        else if (v == mbtn4) imageView.setImageDrawable(getResources().getDrawable(R.drawable.xml_program_botright, null));
    }

    private boolean isProgramDefault(View v) {
        return v == mbtn1 || v == mbtn2 || v == mbtn3 || v == mbtn4;
    }

}
