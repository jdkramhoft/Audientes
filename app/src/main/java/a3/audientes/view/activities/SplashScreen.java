package a3.audientes.view.activities;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import org.w3c.dom.ls.LSOutput;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import a3.audientes.R;
import a3.audientes.dao.AudiogramDAO;
import a3.audientes.dao.ProgramDAO;
import a3.audientes.dto.Program;
import a3.audientes.viewmodel.AudiogramViewModel;
import a3.audientes.viewmodel.ProgramViewModel;
import a3.audientes.utils.SharedPrefUtil;

import static android.bluetooth.BluetoothProfile.GATT;
import static android.bluetooth.BluetoothProfile.GATT_SERVER;
import static android.bluetooth.BluetoothProfile.HEADSET;

public final class SplashScreen extends AppCompatActivity {

    public static final int SPLASH_TIME = 2500;
    private final Handler handler = new Handler();
    private ProgramDAO programDAO = ProgramDAO.getInstance();
    private AudiogramDAO audiogramDAO = AudiogramDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        if (savedInstanceState == null){
            // TODO: only onboarding on first visit
            handler.postDelayed(splash, SPLASH_TIME);
        }


        ProgramViewModel programviewmodel = ViewModelProviders.of(this).get(ProgramViewModel.class);
        programviewmodel.getAllPrograms().observe(this, programs -> {
            System.out.println(programs.size());
            setDefaultNames(programs);
            programDAO.setProgramList(programs);
        });
        AudiogramViewModel audiogramViewModel = ViewModelProviders.of(this).get(AudiogramViewModel.class);
        audiogramViewModel.getAllAudiogram().observe(this, audiograms -> {
            audiogramDAO.setAudiograms(audiograms);

            System.out.println("Audiograms in db: "+audiograms.size());
        });




    }

    private boolean isHearableConnected() {
        // TODO: somehow check if the correct device is already connected
        BluetoothManager manager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);

        List<BluetoothDevice> connected = new ArrayList<>();
        BluetoothAdapter adapter = manager.getAdapter();

        if (adapter != null){
            for (BluetoothDevice e: adapter.getBondedDevices()) {
                if(isConnected(e)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isConnected(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("isConnected", (Class[]) null);
            boolean connected = (boolean) m.invoke(device, (Object[]) null);
            return connected;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    private final Runnable splash = () -> {
        assert getFragmentManager() != null;

        boolean newVisitor = Boolean.valueOf(SharedPrefUtil.readSharedSetting(this, getString(R.string.new_visitor_pref), "true"));
        int currentAudiogramId = Integer.parseInt(SharedPrefUtil.readSharedSetting(getBaseContext(), "currentAudiogram", "0"));
        System.out.println("Current: "+ currentAudiogramId);
        System.out.println(newVisitor);

        Intent nextActivity;
        if (newVisitor || currentAudiogramId == 0){
            nextActivity = new Intent(this, Onboarding.class);
        } else if (isHearableConnected()){
            nextActivity = new Intent(this, HearingProfile.class);
        } else {
            nextActivity = new Intent(this, BluetoothPairing.class);
        }
        startActivity(nextActivity);
        finish();
    };

    private void setDefaultNames(List<Program> programList) {
        if(programList.size() >= 4){
            String defaultName1 = getString(R.string.quiet_btn);
            String defaultName2 = getString(R.string.loud_btn);
            String defaultName3 = getString(R.string.home_btn);
            String defaultName4 = getString(R.string.windy);
            programList.get(0).setName(defaultName1);
            programList.get(1).setName(defaultName2);
            programList.get(2).setName(defaultName3);
            programList.get(3).setName(defaultName4);
            for(int i = 0; i < 4; i++){
                programDAO.update(programList.get(i));
            }
        }

    }

}
