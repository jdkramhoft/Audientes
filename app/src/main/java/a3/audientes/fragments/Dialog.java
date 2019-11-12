package a3.audientes.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import a3.audientes.R;


public class Dialog extends DialogFragment implements View.OnClickListener {


    public Dialog() {
    }

    public static Dialog newInstance(String title) {
        Dialog frag = new Dialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.audiogram , container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        //view.findViewById(@id/).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}