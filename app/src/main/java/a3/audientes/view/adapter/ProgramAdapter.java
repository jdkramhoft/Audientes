package a3.audientes.view.adapter;

import android.app.Activity;
import android.content.Context;
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
import a3.audientes.model.Program;
import a3.audientes.model.ProgramManager;
import utils.SharedPrefUtil;


public class ProgramAdapter extends RecyclerView.Adapter<a3.audientes.view.adapter.ProgramAdapter.MyViewHolder> {

    private final List<Program> programList;
    private final View.OnClickListener onClick;
    private final View.OnLongClickListener onLongClick;
    private Context mcontext;
    private Activity mactivity;
    int counter = 0;
    private int currentProgramId;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title, hiddenId;
        private int id;
        public MyViewHolder(@NonNull View view) {
            super(view);
            hiddenId = view.findViewById(R.id.hiddenId);
            title = view.findViewById(R.id.programName);
            id = view.getId();
        }
    }

    public ProgramAdapter(@NonNull List<Program> programList, @Nullable View.OnClickListener onClick, @Nullable View.OnLongClickListener onLongClick, Activity mactivity, int currentProgramId) {
        this.programList = programList;
        this.onClick = onClick;
        this.onLongClick = onLongClick;
        this.mactivity = mactivity;
        this.currentProgramId = currentProgramId;
        mcontext= mactivity.getBaseContext();
        ProgramManager.getInstance().setAdapter(this);
    }

    @Override @NonNull
    public a3.audientes.view.adapter.ProgramAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        boolean btnIsNotDeleteable = viewType == 2;
        View itemView;
         if(!btnIsNotDeleteable){
             itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_program, parent, false) ;
             itemView.findViewById(R.id.canceltext).setOnClickListener(onClick);
             itemView.findViewById(R.id.canceltext).setOnLongClickListener(onLongClick);
             itemView.setOnClickListener(onClick);
             itemView.setOnLongClickListener(onLongClick);
        } else{
             itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_program, parent, false) ;
             itemView.findViewById(R.id.canceltext).setVisibility(View.GONE);
             itemView.setOnClickListener(onClick);
             itemView.setOnLongClickListener(onLongClick);
        }

       return new a3.audientes.view.adapter.ProgramAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return programList.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Program program = programList.get(position);
        holder.title.setText(program.getName());
        holder.hiddenId.setText(String.valueOf(program.getId()));
        holder.id = program.getId();
        currentProgramId = Integer.parseInt(SharedPrefUtil.readSharedSetting(mactivity, "currentProgram", "1"));

        if(currentProgramId == holder.id ){
            View itemView = holder.itemView;
            ((TextView)itemView.findViewById(R.id.programName)).setTextColor(mcontext.getResources().getColor(R.color.textColor));
            ((ImageView)itemView.findViewById(R.id.program_bg_id)).setImageDrawable(mcontext.getResources().getDrawable(R.drawable.xml_program_selected));
        }
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    View setLayout(ViewGroup parent){
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_program, parent, false) ;
        itemView.findViewById(R.id.canceltext).setOnClickListener(onClick);
        itemView.findViewById(R.id.canceltext).setOnLongClickListener(onLongClick);
        itemView.setOnClickListener(onClick);
        itemView.setOnLongClickListener(onLongClick);
        return itemView;
    }
    public ProgramAdapter get(){
        return this;
    }
}

