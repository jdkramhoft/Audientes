package a3.audientes.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import a3.audientes.view.activities.EditProgram;
import a3.audientes.R;
import a3.audientes.view.adapter.ProgramAdapter;
import a3.audientes.model.Program;
import a3.audientes.model.ProgramManager;
import a3.audientes.viewmodel.ProgramViewModel;
import utils.SharedPrefUtil;
import utils.animation.AnimBtnUtil;

public class Tab1 extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    private View addBtn, mbtn1, mbtn2, mbtn3, mbtn4, prevProgram;
    private FloatingActionButton newCustomProgram;
    private List<Program> programList = new ArrayList<>();
    private ProgramAdapter adapter;
    private ProgramViewModel programviewmodel;
    private ProgramManager programManager = ProgramManager.getInstance();
    private Equalizer trackEq;
    private MediaPlayer musicTrack;
    private int currentProgramId;

    public Tab1() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new Tab1();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        programviewmodel = ViewModelProviders.of(this).get(ProgramViewModel.class);
        programList = programManager.getProgramList();

        View rod = inflater.inflate(R.layout.fragment_tab1, container, false);

        newCustomProgram = rod.findViewById(R.id.fab);
        newCustomProgram.setOnClickListener(this);

        currentProgramId = Integer.parseInt(SharedPrefUtil.readSharedSetting(getContext(), "currentProgram", "1"));
        setupRecyclerView(rod);

        musicTrack = MediaPlayer.create(getContext(), R.raw.song);
        trackEq = new Equalizer(0, musicTrack.getAudioSessionId());
        trackEq.setEnabled(true);
        changeEqualizer(currentProgramId);
        return rod;
    }


    @Override
    public void onResume() {
        super.onResume();
        programList = programManager.getProgramList();

        for(int i = 0; i < programList.size(); i++){
            System.out.println(programList.get(i).getName());
        }
        currentProgramId = Integer.parseInt(SharedPrefUtil.readSharedSetting(getContext(), "currentProgram", "1"));
        System.out.println(currentProgramId);
        changeEqualizer(currentProgramId);
        selecedProgram(currentProgramId);
    }

    @Override
    public void onClick(View v) {

        if (v == newCustomProgram){
            System.out.println("Create short");
            Intent intent = new Intent(getActivity(), EditProgram.class);
            intent.putExtra("new", true);
            intent.putExtra("edit", true);
            startActivity(intent);


        }
        else if (v.getId() == R.id.canceltext){
            ConstraintLayout view =  (ConstraintLayout)v.getParent();
            TextView currentId = view.findViewById(R.id.hiddenId);
            System.out.println("Delete short");
            deletePopup(v, Integer.parseInt(currentId.getText().toString()));
        }else {
            updateLayout(v);
        }
    }


    @Override
    public boolean onLongClick(View v) {


        if (v == newCustomProgram){
            Intent intent = new Intent(getActivity(), EditProgram.class);
            intent.putExtra("new", true);
            intent.putExtra("edit", true);
            startActivity(intent);
        }

        if (v.getId() == R.id.canceltext){
            ConstraintLayout view =  (ConstraintLayout)v.getParent();
            TextView currentId = view.findViewById(R.id.hiddenId);
            System.out.println("Delete long");
            deletePopup(v, Integer.parseInt(currentId.getText().toString()));
        }else{
            AnimBtnUtil.bounceSlow(v, getActivity());
            TextView currentId = v.findViewById(R.id.hiddenId);
            System.out.println(currentId.getText().toString());
            System.out.println("edit long");

            Intent intent = new Intent(getActivity(), EditProgram.class);
            intent.putExtra("new", false);

            // Check if default program is selected... id 1,2,3 are default
            if(currentId.getText().toString().equals("1")
            || currentId.getText().toString().equals("2")
            || currentId.getText().toString().equals("3")){
                intent.putExtra("id", currentId.getText().toString());
                intent.putExtra("edit", false);
            }else{
                intent.putExtra("id", currentId.getText().toString());
                intent.putExtra("edit", true);
            }
            startActivity(intent);
        }
        return true;
    }


    public void setupRecyclerView(View rod){
        RecyclerView recyclerView = rod.findViewById(R.id.programRecycler);
        adapter = new ProgramAdapter(programList, this, this, getActivity(), currentProgramId);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void changeEqualizer(int id){
        Program currentProgram = programManager.getProgram(id);
        // Set Equalizer bands
        trackEq.setBandLevel((short)0, (short)(currentProgram.getLow()-1500));
        trackEq.setBandLevel((short)1, (short)(currentProgram.getLow_plus()-1500));
        trackEq.setBandLevel((short)2, (short)(currentProgram.getMiddle()-1500));
        trackEq.setBandLevel((short)3, (short)(currentProgram.getHigh()-1500));
        trackEq.setBandLevel((short)4, (short)(currentProgram.getHigh_plus()-1500));
    }

    private void selecedProgram(int id){
        RecyclerView v = this.getView().findViewById(R.id.programRecycler);
        ArrayList<LinearLayout> rootView = new ArrayList<LinearLayout>();
        System.out.println(v.toString());
        System.out.println("Child elements: "+v.getChildCount());

        // Reset loop
        for(int i = 0; i < v.getChildCount(); i++){
            LinearLayout temp = (LinearLayout)v.getChildAt(i);
            ((TextView)temp.findViewById(R.id.programName)).setTextColor(getResources().getColor(R.color.white));
            ((ImageView)temp.findViewById(R.id.program_bg_id)).setImageDrawable(getResources().getDrawable(R.drawable.xml_program, null));
        }

        for(int i = 0; i < v.getChildCount(); i++){
            LinearLayout temp = (LinearLayout)v.getChildAt(i);
            System.out.println(temp.toString());
            TextView view = temp.findViewById(R.id.hiddenId);
            int tempId = Integer.parseInt(view.getText().toString());
            if(tempId == id){
                ((TextView)temp.findViewById(R.id.programName)).setTextColor(getResources().getColor(R.color.textColor));
                ((ImageView)temp.findViewById(R.id.program_bg_id)).setImageDrawable(getResources().getDrawable(R.drawable.xml_program_selected, null));
            }
        }

    }


    private void updateLayout(View v) {
        // Change Equalizer
        currentProgramId = getID(v);
        System.out.println("Current program: "+currentProgramId);
        SharedPrefUtil.saveSharedSetting(getContext(),"currentProgram", Integer.toString(currentProgramId));
        changeEqualizer(currentProgramId);
        selecedProgram(currentProgramId);
    }

    private boolean programIsDefault(View v) {
        TextView view = v.findViewById(R.id.hiddenId);
        int id = Integer.parseInt(view.getText().toString());
        return id == 1 || id == 2 || id == 3;
    }

    private int getID(View v) {
        TextView view = v.findViewById(R.id.hiddenId);
        int id = Integer.parseInt(view.getText().toString());
        return id;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTab1Interaction(Uri uri);
    }

    public void deletePopup(View v, int programid){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_popup_delete_program, null);
        Button button1 = dialogView.findViewById(R.id.button1);
        Button button2 = dialogView.findViewById(R.id.button2);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        button1.setOnClickListener(v12 -> dialog.dismiss());
        button2.setOnClickListener(v1 -> {
            System.out.println("Delete program");
            Program currentProgram = programManager.getProgram(programid);
            programManager.deleteProgram(currentProgram);
            programviewmodel.Delete(currentProgram);

            System.out.println("current "+currentProgramId);
            System.out.println("program to delete "+programid);
            if(currentProgramId == programid){
                currentProgramId = 1;
                SharedPrefUtil.saveSharedSetting(getContext(),"currentProgram", Integer.toString(currentProgramId));
                selecedProgram(currentProgramId);
                changeEqualizer(currentProgramId);
            }

            dialog.dismiss();
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
