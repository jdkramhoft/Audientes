package a3.audientes.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import java.util.Objects;

import a3.audientes.EditProgram;
import a3.audientes.MyBounceInterpolator;
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


    private View addbtn, mbtn1, mbtn2, mbtn3, mbtn4, prevBtn;

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



        View rod = inflater.inflate(R.layout.fragment_tab1, container, false);
        setupbuttons(rod);
        setupRecyclerView(rod);

        return rod;
    }

    @Override
    public void onClick(View view) {
        boolean createBtn = view == addbtn;
        boolean deletebtn = view.getId() == R.id.canceltext;

        if (createBtn){
            System.out.println("+");
            Intent intent = new Intent(getActivity(), EditProgram.class);
            startActivity(intent);
            // TODO: ADD NEW PROGRAM
        }
        else if(deletebtn){
            System.out.println("x");
        }
        else {
            ImageView imageView;
            if (prevBtn != null) {
                imageView = prevBtn.findViewById(R.id.program_bg_id);

                // restore normal program layout
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.xml_program, null));


                // TODO: make sure prev program is deleteable if not a default program
                //       findViewById(R.id.canceltext).setVisibility(View.VISIBLE);
            }

            imageView = view.findViewById(R.id.program_bg_id);

            // visuals when program is selected
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_image_leftear_max, null));
            //view.findViewById(R.id.canceltext).setVisibility(View.GONE);

            prevBtn = view;
        }

        System.out.println("short click");
    }

    @Override
    public boolean onLongClick(View v) {
        System.out.println("Long click");
        AnimBtnUtil.bounceSlow(v, getActivity());
        startActivity(new Intent(getActivity(), EditProgram.class));

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
        addbtn = rod.findViewById(R.id.addprogram_btn);
        mbtn1 = rod.findViewById(R.id.mainbtn1);
        mbtn2 = rod.findViewById(R.id.mainbtn2);
        mbtn3 = rod.findViewById(R.id.mainbtn3);
        mbtn4 = rod.findViewById(R.id.mainbtn4);

        addbtn.setOnClickListener(this);
        mbtn1.setOnClickListener(this);
        mbtn2.setOnClickListener(this);
        mbtn3.setOnClickListener(this);
        mbtn4.setOnClickListener(this);

        addbtn.setOnLongClickListener(this);
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
        Program p;
        for (int defaultProgram = 1; defaultProgram <= DEFAULT_PROGRAMS; defaultProgram++){
            p = new Program("test"+defaultProgram,1,1,1,1,1,1,false);
            p.setId(defaultProgram);
            programList.add(p);
        }
        p = new Program("test5",1,1,1,1,1,2,false);
        p.setId(5);
        programList.add(p);
        p = new Program("test6",1,1,1,1,1,2,false);
        p.setId(5);
        programList.add(p);

    }

}
