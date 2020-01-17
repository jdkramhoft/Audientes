package a3.audientes.view.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
    private AudioManager audioManager;
    private String TAB_1_TITLE;
    private String TAB_2_TITLE;
    private BottomSheetBehavior bottomSheetBehavior;
    private View speaker, v;
    private ImageView layoutShader;
    private AppCompatActivity act = this;
    private boolean flag = true;
    private VolumeSlider middleMan;
    private final Handler handler = new Handler();
    private Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TAB_1_TITLE = getString(R.string.programs);
        TAB_2_TITLE = getString(R.string.hearing_test);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setStatusBarTrans();
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText(TAB_1_TITLE));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_2_TITLE));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new HearingProfileAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), getBaseContext()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        int page = getIntent().getIntExtra("ARG_PAGE", 0);
        viewPager.setCurrentItem(page);

        v = findViewById(R.id.sheet_volume);
        v.setOnClickListener(this);
        middleMan = findViewById(R.id.boxedM);

        max = middleMan.getMax();
        min = middleMan.getMin();
        newRange = max - min;
        oldRange = middleMan.getSoundMax() - middleMan.getMin();

        r = this::setOnVolumeChangeListener;

        layoutShader = findViewById(R.id.hearingProfileShader);

        bottomSheetBehavior = BottomSheetBehavior.from(v);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // Called every time when the bottom sheet changes its state.
                state = newState;
                if (BottomSheetBehavior.STATE_SETTLING == newState){
                    handler.post(r);
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
        else if (!middleMan.isBeingTouched()){
            audioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
            int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int soundValue = (((streamVolume - min) * newRange) / oldRange);
            int xx = soundValue > max ? max/2 : soundValue;
            middleMan.setValue(xx);
        }
        handler.postDelayed(r, 100);
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



    public void setBorder(View v) {
        int val = Integer.valueOf(v.getTag().toString());
        //bv.setCornerRadius(val);
        Toast.makeText(HearingProfile.this, "New corner radius is " + val, Toast.LENGTH_SHORT).show();
    }
    public void setMax(View v) {
        int val = Integer.valueOf(v.getTag().toString());
        //bv.setMax(val);
        Toast.makeText(HearingProfile.this, "New max value is " + val, Toast.LENGTH_SHORT).show();
    }
    //TODO HALP IT WONT BE TRANSPARTEN

    public void setStatusBarTrans(){
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
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
}



