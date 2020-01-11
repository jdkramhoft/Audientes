package a3.audientes.view.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import a3.audientes.R;
import a3.audientes.model.Audiogram;
import a3.audientes.model.AudiogramManager;
import a3.audientes.view.adapter.AudiogramAdapter;

public class AudiogramHistory extends AppCompatActivity implements a3.audientes.view.fragments.Audiogram.OnFragmentInteractionListener{
    private AudiogramManager audiogramManager = AudiogramManager.getInstance();
    List<Audiogram> audiogramList;
    LottieAnimationView action_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiogram_history);
        action_btn = findViewById(R.id.action_button_1);

        RecyclerView recyclerView = findViewById(R.id.audiogram_history_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        audiogramList = audiogramManager.getAudiograms();
        FragmentManager fragmentManager = getSupportFragmentManager();
        recyclerView.setAdapter(new AudiogramAdapter(audiogramList, fragmentManager ));
    }


    @Override
    public void onTab2ChildInteraction(Uri uri) {

    }


}