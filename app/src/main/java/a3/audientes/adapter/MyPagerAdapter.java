package a3.audientes.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import a3.audientes.fragments.EnabelBluetooth;
import a3.audientes.fragments.Programs;
import a3.audientes.fragments.Settings;
import a3.audientes.textFragments.Tab1;
import a3.audientes.textFragments.Tab2;
import a3.audientes.textFragments.Tab3;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Tab1();
            case 1: return new Tab2();
            //case 2: return new Tab3();
            //case 0: return new EnabelBluetooth();
            //case 1: return new Programs();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override    public CharSequence getPageTitle(int position) {        switch (position){
        case 0: return "Modes";
        //case 1: return "Tab 2";
        default: return "Hearing Test";
    }
    }
}
