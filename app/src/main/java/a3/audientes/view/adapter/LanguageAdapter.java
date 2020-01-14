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
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.List;
import java.util.Locale;
import a3.audientes.R;

public class LanguageAdapter extends Adapter<LanguageAdapter.MyViewHolder> {

    private List<Locale> localeList;
    private final View.OnClickListener onClick;
    private Context mcontext;
    private Activity mactivity;
    private int currentProgramId;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView flag;

        public MyViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.Title);
            flag = view.findViewById(R.id.imgViewFlag);
        }
    }

    public LanguageAdapter(@NonNull List<Locale> localeList, @Nullable View.OnClickListener onClick, Activity mactivity, int currentProgramId) {
        this.localeList = localeList;
        this.onClick = onClick;
        this.mactivity = mactivity;
        this.currentProgramId = currentProgramId;
        mcontext= mactivity.getBaseContext();
    }

    @Override @NonNull
    public LanguageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_language, parent, false) ;

        itemView.setOnClickListener(onClick);
        return new LanguageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Locale locale = localeList.get(position);
        holder.title.setText(locale.getDisplayLanguage());
        View itemView = holder.itemView;
        //((ImageView)itemView.findViewById(R.id.program_bg_id)).setImageDrawable(mcontext.getResources().getDrawable(R.drawable.xml_program_selected));
    }

    @Override
    public int getItemCount() {
        return localeList.size();
    }

}

