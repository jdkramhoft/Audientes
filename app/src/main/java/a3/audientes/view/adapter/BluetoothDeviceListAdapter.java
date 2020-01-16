package a3.audientes.view.adapter;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import a3.audientes.R;

public class BluetoothDeviceListAdapter extends RecyclerView.Adapter<BluetoothDeviceListAdapter.MyViewHolder> {

    private final List<BluetoothDevice> deviceList;
    private final View.OnClickListener onClick;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private int id;
        public MyViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.Title);
            id = view.getId();
        }
    }

    public BluetoothDeviceListAdapter(@NonNull List<BluetoothDevice> programList, @Nullable View.OnClickListener onClick) {
        this.deviceList = programList;
        this.onClick = onClick;

    }

    @Override @NonNull
    public BluetoothDeviceListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_device_selection, parent, false) ;
        itemView.setOnClickListener(onClick);

        return new BluetoothDeviceListAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return deviceList.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull BluetoothDeviceListAdapter.MyViewHolder holder, int position) {

        BluetoothDevice program = deviceList.get(position);
        if(program.getName().length()>8){
            ((ImageView)holder.itemView.findViewById(R.id.imagee1)).setImageResource(R.drawable.icon_bigfatredx);
        }
        holder.title.setText(program.getName());
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public BluetoothDeviceListAdapter get(){
        return this;
    }
    public int getPosition(View v){
        String deviceName = ((TextView)v.findViewById(R.id.Title)).getText().toString();
        for (int i = 0; i < deviceList.size(); i++) {
            if(deviceList.get(i).getName().equals(deviceName)){
                return i;
            }
        }
        return -1;
    }
}

