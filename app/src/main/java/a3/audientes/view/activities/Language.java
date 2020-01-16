package a3.audientes.view.activities;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        setContentView(R.layout.language_selection);
        Locale[] locales = Locale.getAvailableLocales();

        localcountries= new ArrayList<>();
        for(Locale l:locales) {
            if(localcountries.size()>0){
                if(!l.getDisplayLanguage().equals(localcountries.get(localcountries.size()-1).getDisplayLanguage())){
                    if(!l.getDisplayLanguage().equals(Locale.getDefault().getDisplayLanguage())){
                        localcountries.add(l);

                    }
                }
            }
            else{
                localcountries.add(l);

            }

        }
        localcountries.add(0, Locale.ENGLISH);

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
        switchLocal(this.getBaseContext(),((TextView)v.findViewById(R.id.Title)).getText().toString(),this);
    }
    public void switchLocal(Context context, String lcode, Activity activity) {
        if (lcode.equalsIgnoreCase(""))
            return;
        Resources resources = context.getResources();
        Locale locale =  adapter.getlocale(lcode);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new
                android.content.res.Configuration();
        config.locale = locale;
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        //restart base activity
        Locale.setDefault(locale);
        activity.finish();
        activity.startActivity(activity.getIntent());
    }
}
