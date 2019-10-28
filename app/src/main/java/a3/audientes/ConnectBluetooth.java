package a3.audientes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ConnectBluetooth extends Activity implements View.OnClickListener {


    private List<Device> deviceList;
    private DeviceAdapter mAdapter;

    //TODO Maybe Singleton?

    public ConnectBluetooth() {
        deviceList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetoothconnect);

        RecyclerView recyclerView = findViewById(R.id.devicerecycler);
        mAdapter = new DeviceAdapter(deviceList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareDevices();

    }

    @Override
    public void onClick(View v) {
        //TODO open popup with password to device
    }

    public void prepareDevices(){
        Device device = new Device("CA:29:E8:F4:B5:36");
        deviceList.add(device);

        device = new Device("F4:B5:36:CA:29:E8");
        deviceList.add(device);

        device = new Device("CA:29:E8:F4:B5:36");
        deviceList.add(device);
        mAdapter.notifyDataSetChanged();
     }

     public void setDeviceList(List<Device> devicelist){
        this.deviceList = deviceList;
     }
}
