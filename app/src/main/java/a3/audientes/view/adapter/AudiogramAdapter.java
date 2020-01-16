package a3.audientes.view.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import a3.audientes.R;
import a3.audientes.dto.Audiogram;
import a3.audientes.dao.AudiogramDAO;
import a3.audientes.dto.Program;
import a3.audientes.dao.ProgramDAO;
import a3.audientes.viewmodel.ProgramViewModel;
import a3.audientes.utils.SharedPrefUtil;

public class AudiogramAdapter extends RecyclerView.Adapter<a3.audientes.view.adapter.AudiogramAdapter.MyViewHolder> {

    private final AudiogramDAO audiogramDAO;
    private List<a3.audientes.dto.Audiogram> audiogramList;
    private FragmentManager fragmentManager;
    private ProgramDAO programDAO;
    private ProgramViewModel programViewModel;
    Context context;
    public AudiogramAdapter(@NonNull List<Audiogram> audiogramList, FragmentManager fragmentManager, AudiogramDAO audiogramDAO, ProgramViewModel programViewModel, ProgramDAO programDAO, Context context) {
        this.audiogramList = audiogramList;
        this.fragmentManager = fragmentManager;
        this.audiogramDAO = audiogramDAO;
        this.programDAO = programDAO;
        this.programViewModel = programViewModel;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView date, title, apply;
        private LottieAnimationView anim;
        private View chart;

        public MyViewHolder(@NonNull View view) {
            super(view);
            date = view.findViewById(R.id.subtitle_text);
            title = view.findViewById(R.id.title_text);
            anim = view.findViewById(R.id.action_button_1);
            apply = view.findViewById(R.id.textView_1);
            chart = view.findViewById(R.id.chart);
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
            audiogramDAO.setCurrentAudiogram(audiogramList.get(position));
            SharedPrefUtil.saveSharedSetting(holder.itemView.getContext(),"currentAudiogram", Integer.toString(audiogramList.get(position).getId()));
            updateDefaultPrograms(audiogram);
        });

        holder.anim.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                holder.anim.setVisibility(View.INVISIBLE);
                holder.anim.setProgress(0);
                notifyDataSetChanged();
            }
        });

        if (audiogram.equals(audiogramDAO.getCurrentAudiogram()))
            updateLayout(true, holder);
        else
            updateLayout(false, holder);

        holder.date.setText(audiogram.getDateString());

        a3.audientes.view.fragments.Audiogram fragment = a3.audientes.view.fragments.Audiogram.newInstance(context);
        fragment.drawAudiogram(audiogram.getGraph(), audiogram.getGraph(), holder.chart);
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

    private void updateDefaultPrograms(Audiogram audiogram){
        for(int i = 1; i <= 4; i++){
            Program program = programDAO.getProgram(i);
            System.out.println(program.getId());
            ArrayList<Integer> y = audiogram.getY();
            program.setLow(programDAO.defaultLevel(y.get(0),i));
            program.setLow_plus(programDAO.defaultLevel(y.get(1),i));
            program.setMiddle(programDAO.defaultLevel(y.get(2),i));
            program.setHigh(programDAO.defaultLevel(y.get(3),i));
            program.setHigh_plus(programDAO.defaultLevel(y.get(4),i));
            programDAO.updateDefault(program);
            programViewModel.Update(program);
        }
    }




}
