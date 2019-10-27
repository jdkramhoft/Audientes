package a3.audientes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ConnectBluetooth extends Activity implements View.OnClickListener {


    private List<Device> deviceList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DeviceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetoothconnect);

        recyclerView = (RecyclerView) findViewById(R.id.devicerecycler);

        mAdapter = new DeviceAdapter(deviceList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareDevices();

    }

    @Override
    public void onClick(View v) {

    }

    public void prepareDevices(){

        Device devide1 = new Device("F4:B5:36:CA:29:E8");
        deviceList.add(devide1);

        Device device2 = new Device("CA:29:E8:F4:B5:36");
        deviceList.add(device2);
        mAdapter.notifyDataSetChanged();
        System.out.println(mAdapter.getItemCount());
    }
}
