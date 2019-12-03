package a3.audientes.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import a3.audientes.fragments.Audiogram;
import a3.audientes.fragments.Tab1;
import a3.audientes.fragments.Tab2;


public class HearingProfileAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    public HearingProfileAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.numOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return Tab1.newInstance();
            case 1:
                Audiogram audiogram = Audiogram.newInstance(null, null, null);
                return Tab2.newInstance(audiogram);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Programs";
            //case 1: return "Tab 2";
            default: return "Hearing Test";
        }
    }
}