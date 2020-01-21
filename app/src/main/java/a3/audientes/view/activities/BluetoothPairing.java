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

    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>();
    private Button searchConnectButton;
    private ImageView loadingbar;
    private TextView textView2;
    private boolean newVisitor;
    private boolean hasSearched = false;
    OnClickListener clicker = this;
    BluetoothDeviceListAdapter adapter;


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

                if(device == null || device.getName() == null)
                    return;

                //Consider checking if device name is missing - we might not want those
                bluetoothDevices.add(device);
                Log.i("New device found", device.getName() + " " + device.getAddress());
                adapter.notifyItemInserted(bluetoothDevices.size()-1);
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_pairing);

        RecyclerView lvNewDevices = findViewById(R.id.lvNewDevices);
        bluetoothDevices = new ArrayList<>();

        adapter = new BluetoothDeviceListAdapter(bluetoothDevices, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        lvNewDevices.setLayoutManager(mLayoutManager);
        lvNewDevices.setItemAnimator(new DefaultItemAnimator());
        lvNewDevices.setAdapter(adapter);

        searchConnectButton = findViewById(R.id.connectToDevice);
        searchConnectButton.setOnClickListener(this);

        loadingbar = findViewById(R.id.loadingbar);
        textView2 = findViewById(R.id.textView2);

        ImageView anim = (ImageView) this.findViewById(R.id.loadingbar);
        anim.setBackgroundResource(R.drawable.hearable_animation);
        AnimationDrawable animAnimation = (AnimationDrawable) anim.getBackground();
        animAnimation.start();


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        registerReceiver(broadcastReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        registerReceiver(broadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        newVisitor = Boolean.valueOf(SharedPrefUtil.readSetting(this, getString(R.string.new_visitor_pref), "true"));
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            //noBluetoothSupport();
            enableBluetooth();
        }
        else if (!bluetoothAdapter.isEnabled())
            enableBluetooth();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void discover() {
        if(bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
        }

        /* TODO: why do we ask for location??
        int permission = PackageManager.PERMISSION_GRANTED;
        permission += checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        permission += checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permission != PackageManager.PERMISSION_GRANTED){
            String[] permissions = new String[2];
            permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
            permissions[1] = Manifest.permission.ACCESS_COARSE_LOCATION;
            requestPermissions(permissions, 0);
        }

         */

        bluetoothAdapter.startDiscovery();
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

            loadingbar = findViewById(R.id.loadingbar);
            loadingbar.setVisibility(View.INVISIBLE);
            textView2 = findViewById(R.id.textView2);
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

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getBaseContext().getString(R.string.pair));
            progressDialog.show();
            handler.postDelayed(progressDialog::dismiss, 3000);

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

    private final Handler handler = new Handler();

    private void search() {
        if(bluetoothAdapter == null)
            return;
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.searchdevice));
        dialog.show();
        discover();
        handler.postDelayed(dialog::dismiss, 5000);
    }



    private void navigate() {
        SharedPrefUtil.saveSetting(this, getString(R.string.new_visitor_pref), "false");
        // TODO: newVisitor && check DB for audiogram
        int currentAudiogramId = Integer.parseInt(SharedPrefUtil.readSetting(getBaseContext(), "currentAudiogram", "0"));
        if (newVisitor || currentAudiogramId == 0){
            Intent hearingTestIntent = new Intent(this, StartHearingTest.class);
            startActivityForResult(hearingTestIntent, HearingTest.HEARING_TEST);
        } else {
            startActivity(new Intent(this, HearingProfile.class));
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HearingTest.HEARING_TEST) {
            if (resultCode == HearingTest.TEST_OKAY) {
                startActivity(new Intent(this, HearingProfile.class));
                finish();
            }
            if (resultCode == HearingTest.TEST_NOT_COMPLETE) {
                Intent hearingTestIntent = new Intent(this, StartHearingTest.class);
                startActivityForResult(hearingTestIntent, HearingTest.HEARING_TEST);
            }
        }
    }

    /*
    private void connectToHearable() {
        AlertDialog.Builder builderHearable = new AlertDialog.Builder(this);
        View hearableView = getLayoutInflater().inflate(R.layout.custom_popup_connect_hearable, null);
        Button buttonHearable = hearableView.findViewById(R.id.button1);
        builderHearable.setView(hearableView);
        AlertDialog hearableDialog = builderHearable.create();

        buttonHearable.setOnClickListener(v1 -> {
            // TODO: if not possible to connect device via our app
            //   then redirect to pairing settings on phone
            Intent intent = new Intent(this, BluetoothPairing.class);
            startActivity(intent);
        });
        hearableDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        hearableDialog.show();


        if(!popupManager.isHearable()){
            popupManager.setHearable(true);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    hearableDialog.show();
                }
            }, 1000 );

        }
    }
     */

}