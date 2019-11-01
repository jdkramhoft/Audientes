package a3.audientes.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import a3.audientes.fragments.Tab1;

/**
 * Created by Chirag on 30-Jul-17.
 */

public class TestAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public TestAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                Tab1 tab1 = Tab1.newInstance("1", "1");
                return tab1;
            case 1:
                Tab2 tab2 = Tab2.newInstance("2", "2");
                return  tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}