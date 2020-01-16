package a3.audientes.view.activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import a3.audientes.R;
import a3.audientes.dao.AudiogramDAO;
import a3.audientes.dao.ProgramDAO;
import a3.audientes.dto.Program;
import a3.audientes.viewmodel.AudiogramViewModel;
import a3.audientes.viewmodel.ProgramViewModel;
import a3.audientes.utils.SharedPrefUtil;

public final class SplashScreen extends AppCompatActivity {

    private final Handler handler = new Handler();
    private ProgramViewModel programviewmodel;
    private AudiogramViewModel audiogramViewModel;
    private ProgramDAO programDAO = ProgramDAO.getInstance();
    private AudiogramDAO audiogramDAO = AudiogramDAO.getInstance();
    private boolean newVisitor;
    private int currentAudiogramId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        if (savedInstanceState == null){
            // TODO: only onboarding on first visit
            handler.postDelayed(splash, 2500);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    0);
        }

        programviewmodel = ViewModelProviders.of(this).get(ProgramViewModel.class);
        programviewmodel.getAllPrograms().observe(this, programs -> {
            System.out.println(programs.size());
            programDAO.setProgramList(programs);
        });
        audiogramViewModel = ViewModelProviders.of(this).get(AudiogramViewModel.class);
        audiogramViewModel.getAllAudiogram().observe(this, audiograms -> {
            audiogramDAO.setAudiograms(audiograms);

            System.out.println("Audiograms in db: "+audiograms.size());
        });




    }

    private boolean isHearableConnected() {
        // TODO: somehow check if the correct device is already connected
        return false;
    }

    private final Runnable splash = () -> {
        assert getFragmentManager() != null;

        newVisitor = Boolean.valueOf(SharedPrefUtil.readSharedSetting(this, getString(R.string.new_visitor_pref), "true"));
        currentAudiogramId = Integer.parseInt(SharedPrefUtil.readSharedSetting(getBaseContext(), "currentAudiogram", "0"));
        System.out.println("Current: "+currentAudiogramId);
        System.out.println(newVisitor);
        setDefaultNames();

        Intent nextActivity;
        if (newVisitor || currentAudiogramId == 0){
            nextActivity = new Intent(this, Onboarding.class);
        } else if (!isHearableConnected()){
            nextActivity = new Intent(this, BluetoothPairing.class);
        } else {
            nextActivity = new Intent(this, HearingProfile.class);
        }
        startActivity(nextActivity);
        finish();
    };

    private void setDefaultNames() {
        List<Program> programList = programDAO.getProgramList();
        if(programList != null){
            String defaultName1 = getString(R.string.quiet_btn);
            String defaultName2 = getString(R.string.loud_btn);
            String defaultName3 = getString(R.string.home_btn);
            String defaultName4 = getString(R.string.windy);
            programList.get(0).setName(defaultName1);
            programList.get(1).setName(defaultName2);
            programList.get(2).setName(defaultName3);
            programList.get(3).setName(defaultName4);
        }
        assert programList != null;
        for(Program program : programList){
            programviewmodel.Update(program);
        }
    }

}
