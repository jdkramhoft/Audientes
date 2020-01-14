package a3.audientes.view.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import a3.audientes.bluetooth.logic.Device;
import a3.audientes.R;

public class ConnectDevice extends Fragment implements View.OnClickListener {
    @Override
    public void onClick(View v) {

    }

    /*

    private List<Device> deviceList;
    private DeviceAdapter mAdapter;

    //TODO Maybe Singleton?
    private final static int NUM_OF_DEVICES = 7;
    private final String[] TEMP_NAMES = {"HX-168", "JBL Everest 100", "LE_WH-1000XM3", "Logi MX Sound", "Logitech Z337", "Parrot MKi9200", "WH-1000XM3"};

    public ConnectDevice() {
        deviceList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View rod = i.inflate(R.layout.bluetooth_connect_device, container, false);

     */
/*
        RecyclerView recyclerView = rod.findViewById(R.id.devicerecycler);
        mAdapter = new DeviceAdapter(deviceList, this);

        Context context = Objects.requireNonNull(getContext()); //Current implementation will not work with null host context
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareDevices(NUM_OF_DEVICES);

 */
/*
        return rod;
    }

    @Override
    public void onClick(View v) {
        launchHearingTestScreen();
    }
    private void launchHearingTestScreen(){


 */
        /* TODO: change X in .replace( , X)
        if (getActivity()==null) return;
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction()
                .replace(R.id.emptyFrame, new BeginHearingTestActivity() )
                .addToBackStack(null)
                .commit();

         */
/*
    }

    public void prepareDevices(int n){

        for (int i = 0; i < n; i++) {
            deviceList.add(new Device(TEMP_NAMES[i]));
        }
        mAdapter.notifyDataSetChanged();
     }

     public void setDeviceList(List<Device> deviceList){
        this.deviceList = deviceList;
     }

 */
}


