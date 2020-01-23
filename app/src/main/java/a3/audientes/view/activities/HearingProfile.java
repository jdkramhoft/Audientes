package a3.audientes.view.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import a3.audientes.R;
import a3.audientes.view.adapter.HearingProfileAdapter;
import a3.audientes.view.custom.VolumeSlider;
import a3.audientes.view.fragments.Audiogram;
import a3.audientes.view.fragments.Tab1;
import a3.audientes.view.fragments.Tab2;

public class HearingProfile extends AppCompatActivity implements Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener, Audiogram.OnFragmentInteractionListener, View.OnClickListener {
    private int newRange, oldRange, max, min;
    private int state;
    private BottomSheetBehavior bottomSheetBehavior;
    private View speaker;
    private VolumeSlider middleSlider;
    private final Handler handler = new Handler();
    private Runnable volumeChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setStatusBarTrans();
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.programs)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.hearing_test)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new HearingProfileAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), getBaseContext()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        int page = getIntent().getIntExtra("ARG_PAGE", 0);
        viewPager.setCurrentItem(page);

        View view = findViewById(R.id.sheet_volume);
        view.setOnClickListener(this);
        middleSlider = findViewById(R.id.boxedM);

        max = middleSlider.getMax();
        min = middleSlider.getMin();
        newRange = max - min;
        oldRange = middleSlider.getSoundMax() - middleSlider.getMin();

        volumeChangeListener = this::setOnVolumeChangeListener;

        bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // Called every time when the bottom sheet changes its state.
                state = newState;
                if (BottomSheetBehavior.STATE_SETTLING == newState){
                    handler.post(volumeChangeListener);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                speaker = bottomSheet.findViewById(R.id.speakerIcon);
                speaker.setRotation(slideOffset*180);
            }
        });



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

    private void setOnVolumeChangeListener() {

        if (BottomSheetBehavior.STATE_COLLAPSED == state) {
            handler.removeCallbacksAndMessages(null);
        }
        else if (!middleSlider.isBeingTouched()){
            AudioManager audioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
            int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int soundValue = (((streamVolume - min) * newRange) / oldRange);
            int xx = soundValue > max ? max/2 : soundValue;
            middleSlider.setValue(xx);
        }
        handler.postDelayed(volumeChangeListener, 100);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Return");
    }

    @Override
    public void onTab2ParentInteraction(Uri uri) {
        Log.i("TAG", "received communication from parent fragment");
    }

    @Override
    public void onTab2ChildInteraction(Uri uri) {
        Log.i("TAG", "received communication from child fragment");
    }


    @Override
    public void onTab1Interaction(Uri uri) {
        Log.i("TAG", "received communication from tab 1 fragment");
    }


    @Override
    public void onClick(View v) {
        // if collapsed
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        }
        else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }


    }

    /**
     * Inspired by StackOverflow:
     * https://stackoverflow.com/questions/53250987/flag-layout-no-limits-for-customizing-status-bar-makes-navigation-bar-overlappin
     */
    public void setStatusBarTrans(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}



