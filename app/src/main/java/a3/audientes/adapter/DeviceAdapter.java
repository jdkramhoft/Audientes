package a3.audientes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import a3.audientes.logic.Device;
import a3.audientes.R;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyViewHolder> {

    private final List<Device> deviceList;
    private final OnClickListener onClick;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView title;

        public MyViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.devicetitel);
        }

    }

    public DeviceAdapter(@NonNull List<Device> deviceList, @Nullable View.OnClickListener onClick) {
        this.deviceList = deviceList;
        this.onClick = onClick;
    }

    @Override @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_row, parent, false);
        itemView.setOnClickListener(onClick);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Device device = deviceList.get(position);
        holder.title.setText(device.getName());
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

}
