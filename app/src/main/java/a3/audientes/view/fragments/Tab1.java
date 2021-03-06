package a3.audientes.view.fragments;

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
import java.util.Objects;

import a3.audientes.view.activities.EditProgram;
import a3.audientes.R;
import a3.audientes.view.adapter.ProgramAdapter;
import a3.audientes.dto.Program;
import a3.audientes.dao.ProgramDAO;
import a3.audientes.viewmodel.ProgramViewModel;
import a3.audientes.utils.SharedPrefUtil;
import a3.audientes.utils.animation.AnimBtnUtil;

public class Tab1 extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    private ProgramDAO programDAO = ProgramDAO.getInstance();
    private List<Program> programList = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private ProgramViewModel programViewModel;
    private int currentProgramId;
    private Equalizer trackEq;

    public Tab1() { }

    public static Fragment newInstance() {
        return new Tab1();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab1, container, false);

        floatingActionButton = root.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);

        programViewModel = ViewModelProviders.of(this).get(ProgramViewModel.class);
        programList = programDAO.getProgramList();
        currentProgramId = Integer.parseInt(SharedPrefUtil.readSetting(getContext(), SharedPrefUtil.CURRENT_PROGRAM, "1"));

        MediaPlayer musicTrack = MediaPlayer.create(getContext(), R.raw.song);
        trackEq = new Equalizer(0, musicTrack.getAudioSessionId());
        trackEq.setEnabled(true);
        changeEqualizer(currentProgramId);

        setupRecyclerView(root);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        programList = programDAO.getProgramList();
        currentProgramId = Integer.parseInt(SharedPrefUtil.readSetting(Objects.requireNonNull(getContext()), SharedPrefUtil.CURRENT_PROGRAM, "1"));
        changeEqualizer(currentProgramId);
        selectProgram(currentProgramId);
    }

    @Override
    public void onClick(View v) {
        if (v == floatingActionButton){
            Intent intent = new Intent(getActivity(), EditProgram.class);
            intent.putExtra(EditProgram.NEW, true);
            intent.putExtra(EditProgram.EDIT, true);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        else if (v.getId() == R.id.canceltext){
            ConstraintLayout view =  (ConstraintLayout)v.getParent();
            TextView currentId = view.findViewById(R.id.hiddenId);
            popupForDelete(v, Integer.parseInt(currentId.getText().toString()));
        }else {
            updateLayout(v);
        }
    }


    @Override
    public boolean onLongClick(View v) {
        if (v == floatingActionButton){
            Intent intent = new Intent(getActivity(), EditProgram.class);
            intent.putExtra(EditProgram.NEW, true);
            intent.putExtra(EditProgram.EDIT, true);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        else if (v.getId() == R.id.canceltext){
            ConstraintLayout view =  (ConstraintLayout)v.getParent();
            TextView currentId = view.findViewById(R.id.hiddenId);
            popupForDelete(v, Integer.parseInt(currentId.getText().toString()));
        }
        else {
            // program is long clicked
            AnimBtnUtil.bounceSlow(v, getActivity());

            Intent intent = new Intent(getActivity(), EditProgram.class);
            intent.putExtra(EditProgram.NEW, false);

            // Check if default program is selected
            TextView currentId = v.findViewById(R.id.hiddenId);
            if(programIsDefault(currentId)){
                intent.putExtra(EditProgram.ID, currentId.getText().toString());
                intent.putExtra(EditProgram.EDIT, false);
            }
            else {
                intent.putExtra(EditProgram.ID, currentId.getText().toString());
                intent.putExtra(EditProgram.EDIT, true);
            }

            startActivity(intent);
            Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        return true;
    }

    public void setupRecyclerView(View rod){
        RecyclerView recyclerView = rod.findViewById(R.id.programRecycler);
        ProgramAdapter adapter = new ProgramAdapter(programList, this, this, getActivity(), currentProgramId);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void changeEqualizer(int id){
        Program currentProgram = programDAO.getProgram(id);
        trackEq.setBandLevel((short)0, (short)(currentProgram.getLow()-1500));
        trackEq.setBandLevel((short)1, (short)(currentProgram.getLow_plus()-1500));
        trackEq.setBandLevel((short)2, (short)(currentProgram.getMiddle()-1500));
        trackEq.setBandLevel((short)3, (short)(currentProgram.getHigh()-1500));
        trackEq.setBandLevel((short)4, (short)(currentProgram.getHigh_plus()-1500));
    }

    private void selectProgram(int id){
        RecyclerView v = this.getView().findViewById(R.id.programRecycler);

        // set all programs to not selected
        for(int i = 0; i < v.getChildCount(); i++){
            LinearLayout temp = (LinearLayout)v.getChildAt(i);
            ((TextView)temp.findViewById(R.id.programName)).setTextColor(getResources().getColor(R.color.white,null));
            ((ImageView)temp.findViewById(R.id.program_bg_id)).setImageDrawable(getResources().getDrawable(R.drawable.xml_program, null));
            ((ImageView)temp.findViewById(R.id.canceltext)).setImageDrawable(getResources().getDrawable(R.drawable.tab1_program_delete_icon, null));
        }

        // find selected program
        for(int i = 0; i < v.getChildCount(); i++){
            LinearLayout temp = (LinearLayout)v.getChildAt(i);
            TextView view = temp.findViewById(R.id.hiddenId);
            int tempId = Integer.parseInt(view.getText().toString());
            if(tempId == id){
                ((TextView)temp.findViewById(R.id.programName)).setTextColor(getResources().getColor(R.color.darkBlue, null));
                ((ImageView)temp.findViewById(R.id.program_bg_id)).setImageDrawable(getResources().getDrawable(R.drawable.xml_program_selected, null));
                ((ImageView)temp.findViewById(R.id.canceltext)).setImageDrawable(getResources().getDrawable(R.drawable.tab1_program_delete_icon_pressed, null));
            }
        }
    }

    private void updateLayout(View v) {
        currentProgramId = getID(v);
        SharedPrefUtil.saveSetting(Objects.requireNonNull(getContext()),SharedPrefUtil.CURRENT_PROGRAM, Integer.toString(currentProgramId));
        changeEqualizer(currentProgramId);
        selectProgram(currentProgramId);
    }


    private boolean programIsDefault(View v) {
        TextView view = v.findViewById(R.id.hiddenId);
        int id = Integer.parseInt(view.getText().toString());
        return id == 1 || id == 2 || id == 3 || id == 4;
    }

    private int getID(View v) {
        TextView view = v.findViewById(R.id.hiddenId);
        return Integer.parseInt(view.getText().toString());
    }

    public interface OnFragmentInteractionListener {
        void onTab1Interaction(Uri uri);
    }

    public void popupForDelete(View v, int programid){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.Theme_Dialog);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_popup_delete_program, null);
        Button doNothingBtn = dialogView.findViewById(R.id.button1);
        Button deleteBtn = dialogView.findViewById(R.id.button2);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();


        doNothingBtn.setOnClickListener(v12 -> dialog.dismiss());
        deleteBtn.setOnClickListener(v1 -> {
            Program currentProgram = programDAO.getProgram(programid);
            programDAO.deleteProgram(currentProgram);
            programViewModel.Delete(currentProgram);

            if(currentProgramId == programid){
                currentProgramId = 1;
                SharedPrefUtil.saveSetting(getContext(),SharedPrefUtil.CURRENT_PROGRAM, Integer.toString(currentProgramId));
                selectProgram(currentProgramId);
                changeEqualizer(currentProgramId);
            }

            dialog.dismiss();
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
