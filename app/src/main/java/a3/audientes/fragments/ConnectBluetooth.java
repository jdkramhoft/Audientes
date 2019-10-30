package a3.audientes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import a3.audientes.logic.Device;
import a3.audientes.R;

public class ConnectBluetooth extends Fragment implements View.OnClickListener {


    private List<Device> deviceList;
    private DeviceAdapter mAdapter;

    //TODO Maybe Singleton?
    private final int NUM_OF_DEVICES = 7;
    private final String[] TEMP_NAMES = {"HX-168", "JBL Everest 100", "LE_WH-1000XM3", "Logi MX Sound", "Logitech Z337", "Parrot MKi9200", "WH-1000XM3"};

    public ConnectBluetooth() {
        deviceList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View rod = i.inflate(R.layout.bluetoothconnect, container, false);

        RecyclerView recyclerView = rod.findViewById(R.id.devicerecycler);
        mAdapter = new DeviceAdapter(deviceList, this);

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        prepareDevices(NUM_OF_DEVICES);
        return rod;
    }

    @Override
    public void onClick(View v) {
        launchHearingTestScreen();
    }
    private void launchHearingTestScreen(){
        /*
        Utils.saveSharedSetting(OnboardingActivity.this, MainMenu.PREF_USER_FIRST_TIME, "false");

        Intent hearingTestIntent = new Intent(this, BeginHearingTestActivity.class);
        hearingTestIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(hearingTestIntent);
        finish();
        */
        if (getActivity()==null) return;
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction()
                .replace(R.id.emptyFrame, new BeginHearingTestActivity() )
                .addToBackStack(null)
                .commit();

    }

    public void prepareDevices(int n){

        for (int i = 0; i < n; i++) {
            deviceList.add(new Device(TEMP_NAMES[i]));
        }
        mAdapter.notifyDataSetChanged();
     }

     public void setDeviceList(List<Device> devicelist){
        this.deviceList = deviceList;
     }
}
