package a3.audientes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import a3.audientes.EditProgram;
import a3.audientes.R;
import a3.audientes.adapter.ProgramAdapter;
import a3.audientes.models.Program;
import a3.audientes.models.ProgramManager;
import a3.audientes.viewModels.ProgramViewModel;
import utils.AnimBtnUtil;

public class Modes_Tab1 extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    private View addBtn, mbtn1, mbtn2, mbtn3, mbtn4, prevProgram;
    private Button newCustomProgram;
    private List<Program> programList = new ArrayList<>();
    private ProgramAdapter adapter;
    private ProgramViewModel programviewmodel;
    private ProgramManager programManager = ProgramManager.getInstance();
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

        programviewmodel = ViewModelProviders.of(this).get(ProgramViewModel.class);
        programList = programManager.getProgramList();

        View rod = inflater.inflate(R.layout.fragment_tab1, container, false);

        newCustomProgram = rod.findViewById(R.id.newCustomMode);
        newCustomProgram.setOnClickListener(this);

        setupRecyclerView(rod);

        return rod;
    }

    @Override
    public void onResume() {
        super.onResume();
        programList = programManager.getProgramList();

        for(int i = 0; i < programList.size(); i++){
            System.out.println(programList.get(i).getName());
        }
        adapter.notifyDataSetChanged();
       System.out.println("Back to fragment model tab 1");
    }

    @Override
    public void onClick(View v) {


        if (v == newCustomProgram){
            // TODO: Create new program
            Intent intent = new Intent(getActivity(), EditProgram.class);
            //intent.putExtra();
            startActivity(intent);
        }

        if (v.getId() == R.id.canceltext){
            System.out.println("Delete short");
        }
    }


    @Override
    public boolean onLongClick(View v) {


        if (v == newCustomProgram){
            // TODO: Create new program
            Intent intent = new Intent(getActivity(), EditProgram.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.canceltext){
            ConstraintLayout view =  (ConstraintLayout)v.getParent();
            TextView currentId = view.findViewById(R.id.hiddenId);
            System.out.println("Delete long");
            System.out.println(v.getId());
            System.out.println(currentId.getText().toString());
            Intent intent = new Intent(getActivity(), EditProgram.class);
            intent.putExtra("id", currentId.getText().toString());
            intent.putExtra("delete", true);
            startActivity(intent);


        }else{
            TextView currentId = v.findViewById(R.id.hiddenId);
            System.out.println(currentId.getText().toString());
            System.out.println("edit long");

            Intent intent = new Intent(getActivity(), EditProgram.class);
            intent.putExtra("id", currentId.getText().toString());
            intent.putExtra("delete", false);
            startActivity(intent);
        }

        return true;
    }


    public void setupRecyclerView(View rod){
        RecyclerView recyclerView = rod.findViewById(R.id.programRecycler);
        adapter = new ProgramAdapter(programList, this, this, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void addFakeList(){
        programList.add(new a3.audientes.models.Program("test1",1,1,1,1,1,1,false));
        programList.add(new a3.audientes.models.Program("test2",1,1,1,1,1,1,false));
        programList.add(new a3.audientes.models.Program("test3",1,1,1,1,1,1,false));
        programList.add(new a3.audientes.models.Program("test4",1,1,1,1,1,1,false));
        programList.add(new a3.audientes.models.Program("+",1,1,1,1,1,3,false));

    }

    private void updateLayout(View v) {
        ImageView imageView;

        // restore prev program layout
        if (prevProgram != null) {
            imageView = prevProgram.findViewById(R.id.program_bg_id);

            if (programIsDefault(prevProgram)) setDefaultLayout(prevProgram, imageView);
            else {
                prevProgram.findViewById(R.id.canceltext).setVisibility(View.VISIBLE);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.xml_program, null));
            }
        }

        // set layout when program is selected
        imageView = v.findViewById(R.id.program_bg_id);

        if (programIsDefault(v)) setDefaultLayout(v, imageView);
        else v.findViewById(R.id.canceltext).setVisibility(View.GONE);

        // TODO: update drawable to a more appropriate one
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_image_leftear_max, null));
        prevProgram = v;

        // TODO: should selected program be moved to beginning of recyclerView?
    }

    private void setDefaultLayout(View v, ImageView imageView) {
        if (v == mbtn1) imageView.setImageDrawable(getResources().getDrawable(R.drawable.xml_program_upleft, null));
        else if (v == mbtn2) imageView.setImageDrawable(getResources().getDrawable(R.drawable.xml_program_upright, null));
        else if (v == mbtn3) imageView.setImageDrawable(getResources().getDrawable(R.drawable.xml_program_botleft, null));
        else if (v == mbtn4) imageView.setImageDrawable(getResources().getDrawable(R.drawable.xml_program_botright, null));
    }

    private boolean programIsDefault(View v) {
        return v == mbtn1 || v == mbtn2 || v == mbtn3 || v == mbtn4;
    }
}
