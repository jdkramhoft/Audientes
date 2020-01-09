package a3.audientes.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import a3.audientes.R;

public class BluetoothDeviceListAdapter extends RecyclerView.Adapter<a3.audientes.bluetooth.BluetoothDeviceListAdapter.MyViewHolder> {

    private final List<BluetoothDevice> devicelist;
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
        this.devicelist = programList;
        this.onClick = onClick;

    }

    @Override @NonNull
    public a3.audientes.bluetooth.BluetoothDeviceListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_bt, parent, false) ;
        itemView.setOnClickListener(onClick);

        return new a3.audientes.bluetooth.BluetoothDeviceListAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return devicelist.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull a3.audientes.bluetooth.BluetoothDeviceListAdapter.MyViewHolder holder, int position) {

        BluetoothDevice program = devicelist.get(position);
        holder.title.setText(program.getName());
    }

    @Override
    public int getItemCount() {
        return devicelist.size();
    }

    public a3.audientes.bluetooth.BluetoothDeviceListAdapter get(){
        return this;
    }
    public int getPosition(View v){
        String deviceName = ((TextView)v.findViewById(R.id.Title)).getText().toString();
        for (int i = 0; i < devicelist.size(); i++) {
            if(devicelist.get(i).getName().equals(deviceName)){
                return i;
            }
        }
        return -1;
    }
}

