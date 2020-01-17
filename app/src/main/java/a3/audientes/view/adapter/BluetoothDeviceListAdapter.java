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

    private final List<BluetoothDevice> devicelist;
    private final View.OnClickListener onClick;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        public MyViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.Title);
        }
    }

    public BluetoothDeviceListAdapter(@NonNull List<BluetoothDevice> programList, @Nullable View.OnClickListener onClick) {
        this.devicelist = programList;
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
        return devicelist.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull BluetoothDeviceListAdapter.MyViewHolder holder, int position) {

        BluetoothDevice program = devicelist.get(position);
        if(!(program.getName().equals("Mi Phone"))){
            ((ImageView)holder.itemView.findViewById(R.id.imagee1)).setImageResource(R.drawable.bluetooth_icon_not_connectable);
            //((TextView)holder.itemView.findViewById(R.id.Title)).setTextColor(-12303292);
        }
        holder.title.setText(program.getName());
    }

    @Override
    public int getItemCount() {
        return devicelist.size();
    }

    public BluetoothDeviceListAdapter get(){
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

