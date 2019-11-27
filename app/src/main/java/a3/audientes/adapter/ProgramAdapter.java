package a3.audientes.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import a3.audientes.R;
import a3.audientes.models.Program;


public class ProgramAdapter extends RecyclerView.Adapter<a3.audientes.adapter.ProgramAdapter.MyViewHolder> {

    private final List<Program> programList;
    private final View.OnClickListener onClick;
    private final View.OnLongClickListener onLongClick;
    private Context mcontext;
    private Activity mactivity;
    int counter = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private int id;
        public MyViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.programName);
            id = view.getId();
        }
    }

    public ProgramAdapter(@NonNull List<Program> programList, @Nullable View.OnClickListener onClick, @Nullable View.OnLongClickListener onLongClick, Activity mactivity) {
        this.programList = programList;
        this.onClick = onClick;
        this.onLongClick = onLongClick;
        this.mactivity = mactivity;
        mcontext= mactivity.getBaseContext();
    }

    @Override @NonNull
    public a3.audientes.adapter.ProgramAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        boolean btnIsCreateProgram = viewType == 3, btnIsNotDeleteable = viewType != 2;
        View itemView = setLayout(parent);
       return new a3.audientes.adapter.ProgramAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return programList.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Program program = programList.get(position);
        holder.title.setText(program.getName());
        holder.id = program.getId();
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    View setLayout(ViewGroup parent){
        View itemView;
        if(counter == 0){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_program_upleft, parent, false) ;

        }
        else if(counter == 1){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_program_upright, parent, false) ;

        }
        else if(counter == programList.size()-1){
            if(programList.size()%2 == 0){
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_program_botright, parent, false) ;

            }
            else{
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_program_botleft, parent, false) ;
            }
        }
        else{
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_program, parent, false) ;
            itemView.findViewById(R.id.canceltext).setOnTouchListener(onTouch);

        }
        counter++;



        itemView.setOnTouchListener(onTouch);
        itemView.setOnLongClickListener(v -> true);
        return itemView;
    }
}

