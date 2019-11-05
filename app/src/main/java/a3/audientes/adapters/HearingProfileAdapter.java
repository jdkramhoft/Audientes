package a3.audientes.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import a3.audientes.fragments.Audiogram;
import a3.audientes.fragments.Tab1;
import a3.audientes.fragments.Tab2;


public class HearingProfileAdapter extends FragmentStatePagerAdapter {

    private int mNoOfTabs;

    public HearingProfileAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
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
        return mNoOfTabs;
    }
}