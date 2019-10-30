package a3.audientes.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import a3.audientes.logic.Device;
import a3.audientes.R;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyViewHolder>{

    private List<Device> deviceList;
    public View.OnClickListener Onclick;

       public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.devicetitel);

        }


    }


    public DeviceAdapter(List<Device> deviceList, View.OnClickListener onclick) {
        this.deviceList = deviceList;
        this.Onclick = onclick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.devicerow, parent, false);
        itemView.setOnClickListener(Onclick);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Device device = deviceList.get(position);
        holder.title.setText(device.getName());

    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }


}
