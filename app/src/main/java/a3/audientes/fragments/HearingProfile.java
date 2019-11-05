package a3.audientes.fragments;

import android.net.Uri;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import a3.audientes.R;
import a3.audientes.adapters.HearingProfileAdapter;

public class HearingProfile extends AppCompatActivity implements Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener, Audiogram.OnFragmentInteractionListener {

    private final String TAB_1_TITLE = "Modes";
    private final String TAB_2_TITLE = "Hearing HearingProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hearing_profile);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText(TAB_1_TITLE));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_2_TITLE));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new HearingProfileAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    @Override
    public void onTab1Interaction(Uri uri) {
        Log.i("TAG", "received communication from child fragment");
    }

    @Override
    public void onTab2ParentInteraction(Uri uri) {
        Log.i("TAG", "received communication from parent fragment");
    }

    @Override
    public void onTab2ChildInteraction(Uri uri) {
        Log.i("TAG", "received communication from child fragment");
    }

}
