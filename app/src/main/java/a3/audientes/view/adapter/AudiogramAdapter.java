package a3.audientes.view.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import a3.audientes.R;
import a3.audientes.model.Audiogram;

public class AudiogramAdapter extends RecyclerView.Adapter<a3.audientes.view.adapter.AudiogramAdapter.MyViewHolder> {

    private List<a3.audientes.model.Audiogram> audiogramList;
    FragmentManager fragmentManager;
    boolean selected;

    public AudiogramAdapter(@NonNull List<Audiogram> audiogramList, FragmentManager fragmentManager) {
        this.audiogramList = audiogramList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.audiogram_card, parent, false);

        TextView tv = itemView.findViewById(R.id.textview_1);
        LottieAnimationView action_btn = itemView.findViewById(R.id.action_button_1);
        action_btn.setAnimation(R.raw.success);

        action_btn.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                action_btn.setVisibility(View.INVISIBLE);
                action_btn.setProgress(0);
                tv.setVisibility(View.VISIBLE);
            }
        });
        tv.setOnClickListener(v -> {
            tv.setVisibility(View.INVISIBLE);
            action_btn.setVisibility(View.VISIBLE);
            action_btn.playAnimation();
        });

        return new a3.audientes.view.adapter.AudiogramAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Audiogram audiogram = audiogramList.get(position);

        // TODO: use audiogram.getDateString() instead
        holder.dateView.setText(audiogram.getDate().toString());


        a3.audientes.view.fragments.Audiogram fragment = a3.audientes.view.fragments.Audiogram.newInstance();

        //fragment.drawAudiogram(audiogram.getGraf(), audiogram.getGraf(), holder.view);


        fragmentManager.beginTransaction().replace(R.id.media_image, fragment).commit();


    }

    @Override
    public int getItemCount() {
        return audiogramList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private FrameLayout frameLayout;
        private TextView dateView;


        public MyViewHolder(@NonNull View view) {
            super(view);
            frameLayout = view.findViewById(R.id.media_image);
            dateView = view.findViewById(R.id.subtitle_text);
        }
    }


}
