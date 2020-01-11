package a3.audientes.view.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import a3.audientes.R;
import a3.audientes.model.Audiogram;
import a3.audientes.model.AudiogramManager;

public class AudiogramAdapter extends RecyclerView.Adapter<a3.audientes.view.adapter.AudiogramAdapter.MyViewHolder> {

    private final AudiogramManager audiogramManager;
    private List<a3.audientes.model.Audiogram> audiogramList;
    private FragmentManager fragmentManager;

    public AudiogramAdapter(@NonNull List<Audiogram> audiogramList, FragmentManager fragmentManager, AudiogramManager audiogramManager) {
        this.audiogramList = audiogramList;
        this.fragmentManager = fragmentManager;
        this.audiogramManager = audiogramManager;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView date, title, apply;
        private LottieAnimationView anim;

        public MyViewHolder(@NonNull View view) {
            super(view);
            date = view.findViewById(R.id.subtitle_text);
            title = view.findViewById(R.id.title_text);
            anim = view.findViewById(R.id.action_button_1);
            apply = view.findViewById(R.id.textview_1);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.audiogram_card, parent, false);
        return new a3.audientes.view.adapter.AudiogramAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Audiogram audiogram = audiogramList.get(position);

        holder.apply.setOnClickListener(v -> {
            holder.apply.setVisibility(View.INVISIBLE);
            holder.anim.setVisibility(View.VISIBLE);
            holder.anim.playAnimation();
            audiogramManager.setCurrentAudiogram(audiogramList.get(position));
        });

        holder.anim.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                holder.anim.setVisibility(View.INVISIBLE);
                holder.anim.setProgress(0);
                notifyDataSetChanged();
            }
        });

        if (audiogram.equals(audiogramManager.getCurrentAudiogram()))
            updateLayout(true, holder);
        else
            updateLayout(false, holder);

        // TODO: use fragment.getDateString() instead
        holder.date.setText(audiogram.getDate().toString());



        // TODO: TIME TO SHINE;
        a3.audientes.view.fragments.Audiogram fragment = a3.audientes.view.fragments.Audiogram.newInstance();
        //fragment.drawAudiogram(audiogram.getGraf(), audiogram.getGraf(), holder.view);
        fragmentManager.beginTransaction().replace(R.id.audiogram_frame_layout, fragment).commit();
    }

    @Override
    public int getItemCount() {
        return audiogramList.size();
    }

    private void updateLayout(boolean isSelected, MyViewHolder holder){
        if (isSelected){
            holder.title.setTextColor(holder.itemView.getResources().getColor(R.color.lightGreen));
            holder.apply.setEnabled(false);
            holder.apply.setClickable(false);
            holder.apply.setVisibility(View.INVISIBLE);
        }
        else {
            holder.title.setTextColor(holder.itemView.getResources().getColor(R.color.white));
            holder.apply.setEnabled(true);
            holder.apply.setClickable(true);
            holder.apply.setVisibility(View.VISIBLE);
        }

    }




}
