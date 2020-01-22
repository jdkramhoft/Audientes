package a3.audientes.view.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import a3.audientes.R;
import a3.audientes.view.fragments.Audiogram;
import a3.audientes.view.fragments.Tab1;
import a3.audientes.view.fragments.Tab2;


public class HearingProfileAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;
    private Context context;

    public HearingProfileAdapter(FragmentManager fm, int NumberOfTabs, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        this.numOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return Tab1.newInstance();
            case 1:
                Audiogram audiogram = Audiogram.newInstance(context);
                return Tab2.newInstance(audiogram);
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return context.getString(R.string.programs);
        if (position == 1)
            return context.getString(R.string.hearing_test);
        throw new IllegalArgumentException();
    }
}