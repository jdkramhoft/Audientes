package a3.audientes.view.activities;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import a3.audientes.R;
import a3.audientes.view.adapter.BluetoothDeviceListAdapter;
import a3.audientes.utils.SharedPrefUtil;

public class BluetoothPairing extends AppCompatActivity implements OnClickListener {

    public static final int DISCOVER_TIME = 10000;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>();
    private Button searchConnectButton;
    private ImageView loadingbar;
    private TextView textView2;
    private boolean hasSearched = false;
    private BluetoothDeviceListAdapter adapter;
    private ProgressDialog dialog;

    private final Handler handler = new Handler();


    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action == null)
                return;

            if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                final String tag = "Bluetooth on/off change";

                if(state == BluetoothAdapter.STATE_TURNING_ON)
                    Log.i(tag, "turning on");
                if(state == BluetoothAdapter.STATE_ON)
                    Log.i(tag, "turned on");
                if(state == BluetoothAdapter.STATE_TURNING_OFF)
                    Log.i(tag, "turning off");
                if(state == BluetoothAdapter.STATE_OFF)
                    Log.i(tag, "turned off");
            }

            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if(device == null || device.getName() == null){
                    return;
                }

                bluetoothDevices.add(device);
                Log.i("New device found", device.getName() + " " + device.getAddress());
                adapter.notifyItemInserted(bluetoothDevices.size()-1);
            }

            if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)){
                startDialog();
                searchConnectButton.setClickable(false);
            }

            if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                stopDialog();
                searchConnectButton.setClickable(true);
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_pairing);

        loadingbar = findViewById(R.id.loadingbar);
        textView2 = findViewById(R.id.textView2);
        RecyclerView lvNewDevices = findViewById(R.id.lvNewDevices);
        bluetoothDevices = new ArrayList<>();

        adapter = new BluetoothDeviceListAdapter(bluetoothDevices, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        lvNewDevices.setLayoutManager(mLayoutManager);
        lvNewDevices.setItemAnimator(new DefaultItemAnimator());
        lvNewDevices.setAdapter(adapter);

        searchConnectButton = findViewById(R.id.connectToDevice);
        searchConnectButton.setOnClickListener(this);

        ImageView anim = this.findViewById(R.id.loadingbar);
        loadingbar.setBackgroundResource(R.drawable.hearable_animation);
        AnimationDrawable animAnimation = (AnimationDrawable) anim.getBackground();
        animAnimation.start();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver, filter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        enableBluetooth();
    }

    public void discover() {
        if(bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
            handler.removeCallbacks(bluetoothAdapter::cancelDiscovery);
        }

        bluetoothAdapter.startDiscovery();
        handler.postDelayed(bluetoothAdapter::cancelDiscovery, DISCOVER_TIME);
    }

    private void enableBluetooth() {
        if(bluetoothAdapter == null){
            System.out.println("Bluetooth not supported");
        } else {
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == searchConnectButton){
            loadingbar.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);

            if(!hasSearched){
                search();
                hasSearched = true;
                ((Button)v).setText(R.string.cntnue);
            }
            else{
                navigate();
            }
        }
        else{
            bluetoothDevices.get(adapter.getPosition(v)).createBond();

            //StackOverflow code snippet
            final Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            final ComponentName cn = new ComponentName("com.android.settings",
                    "com.android.settings.bluetooth.BluetoothSettings");
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    private void search() {
        if(bluetoothAdapter == null)
            return;
        discover();
    }

    private void startDialog(){
        dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.searchdevice));
        dialog.setCancelable(false);
        dialog.show();
    }

    private void stopDialog(){
        dialog.dismiss();
    }


    private void navigate() {
        String currentSetting = SharedPrefUtil.readSetting(getBaseContext(), "currentAudiogram");
        if (currentSetting == null){
            startActivity(new Intent(this, StartHearingTest.class));
        } else {
            startActivity(new Intent(this, HearingProfile.class));
        }
        finish();

    }

}