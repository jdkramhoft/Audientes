package a3.audientes.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import a3.audientes.EditProgram;
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
        if(v.getId() == R.id.createProgram){

            Intent intent = new Intent(getActivity(), EditProgram.class);
            startActivity(intent);

        AnimBtnUtil.bounce(v, getActivity());

        }
    }
}
