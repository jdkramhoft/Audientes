package a3.audientes.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import a3.audientes.R;
import a3.audientes.view.fragments.Audiogram;

public class AudiogramAdapter extends RecyclerView.Adapter<a3.audientes.view.adapter.AudiogramAdapter.MyViewHolder> {

    private List<Audiogram> audiogramList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.audiogram_card, parent, false);

        // TODO: retrieve all audiograms from DB


        return new a3.audientes.view.adapter.AudiogramAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View view) {
            super(view);
        }
    }
}
