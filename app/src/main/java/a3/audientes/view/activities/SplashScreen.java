package a3.audientes.view.activities;


import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import org.w3c.dom.ls.LSOutput;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import a3.audientes.R;
import a3.audientes.dao.AudiogramDAO;
import a3.audientes.dao.ProgramDAO;
import a3.audientes.viewmodel.AudiogramViewModel;
import a3.audientes.viewmodel.ProgramViewModel;
import a3.audientes.utils.SharedPrefUtil;

import static android.bluetooth.BluetoothProfile.GATT;
import static android.bluetooth.BluetoothProfile.GATT_SERVER;
import static android.bluetooth.BluetoothProfile.HEADSET;

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
        isHearableConnected();
        if (savedInstanceState == null){
            // TODO: only onboarding on first visit
            handler.postDelayed(splash, 2000);
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
        BluetoothManager manager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);

        List<BluetoothDevice> connected = new ArrayList<>();

        for (BluetoothDevice e: manager.getAdapter().getBondedDevices()) {
            if(isConnected(e)){
                return true;
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

        newVisitor = Boolean.valueOf(SharedPrefUtil.readSharedSetting(this, getString(R.string.new_visitor_pref), "true"));
        currentAudiogramId = Integer.parseInt(SharedPrefUtil.readSharedSetting(getBaseContext(), "currentAudiogram", "0"));
        System.out.println("Current: "+currentAudiogramId);
        System.out.println(newVisitor);
        if (newVisitor || currentAudiogramId == 0){
            Intent intent = new Intent(this, Onboarding.class);
            startActivity(intent);
            Objects.requireNonNull(this).finish();
        }
        else if (!isHearableConnected()){
            Intent intent = new Intent(this, BluetoothPairing.class);
            startActivity(intent);
            Objects.requireNonNull(this).finish();
        }
    };

}
