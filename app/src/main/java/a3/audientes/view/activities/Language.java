package a3.audientes.view.activities;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import a3.audientes.R;
import a3.audientes.view.adapter.LanguageAdapter;

public class Language extends AppCompatActivity implements View.OnClickListener {
    LanguageAdapter adapter;
    ArrayList<Locale> localcountries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_selectlanguage);
        Locale[] locales = Locale.getAvailableLocales();

        localcountries= new ArrayList<>();
        for(Locale l:locales) {
            if(localcountries.size()>0){
                if(!l.getDisplayLanguage().equals(localcountries.get(localcountries.size()-1).getDisplayLanguage())){
                    if(!l.getDisplayLanguage().equals(Locale.getDefault().getDisplayLanguage())){
                        localcountries.add(l);
                        System.out.println(l.getDisplayLanguage().toString());
                    }
                    else{

                    }
                }
            }
            else{
                localcountries.add(l);
                System.out.println(l.getDisplayLanguage().toString());
            }

        }
        System.out.println(Locale.getDefault().getDisplayLanguage());
        setupRecyclerView(findViewById(R.id.languageRecView))   ;
    }

    public void setupRecyclerView(RecyclerView rod){

        adapter = new LanguageAdapter(localcountries, this,this,0);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rod.setLayoutManager(mLayoutManager);
        rod.setItemAnimator(new DefaultItemAnimator());
        rod.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
