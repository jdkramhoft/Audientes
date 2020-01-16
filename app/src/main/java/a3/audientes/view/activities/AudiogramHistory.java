package a3.audientes.view.activities;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import a3.audientes.R;
import a3.audientes.dto.Audiogram;
import a3.audientes.dao.AudiogramDAO;
import a3.audientes.dao.ProgramDAO;
import a3.audientes.view.adapter.AudiogramAdapter;
import a3.audientes.viewmodel.ProgramViewModel;

public class AudiogramHistory extends AppCompatActivity implements a3.audientes.view.fragments.Audiogram.OnFragmentInteractionListener{
    private AudiogramDAO audiogramDAO = AudiogramDAO.getInstance();
    LottieAnimationView action_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audiogram_history);
        action_btn = findViewById(R.id.action_button_1);

        RecyclerView recyclerView = findViewById(R.id.audiogram_history_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Audiogram> audiogramList = new ArrayList<>(audiogramDAO.getAudiogramList());

        Collections.sort(audiogramList);
        FragmentManager fragmentManager = getSupportFragmentManager();

        ProgramDAO programDAO = ProgramDAO.getInstance();
        ProgramViewModel programViewModel = ViewModelProviders.of(this).get(ProgramViewModel.class);

        recyclerView.setAdapter(new AudiogramAdapter(audiogramList, fragmentManager, audiogramDAO, programViewModel, programDAO, getBaseContext()));
    }


    @Override
    public void onTab2ChildInteraction(Uri uri) {

    }


}