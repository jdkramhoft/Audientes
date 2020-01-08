package a3.audientes.view.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import a3.audientes.R;
import a3.audientes.bluetooth.BluetoothPairingActivity;
import a3.audientes.view.adapter.HearingProfileAdapter;
import a3.audientes.view.fragments.Audiogram;
import a3.audientes.view.fragments.Tab1;
import a3.audientes.view.fragments.Tab2;
import a3.audientes.model.PopupManager;

public class HearingProfile extends AppCompatActivity implements Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener, Audiogram.OnFragmentInteractionListener, View.OnClickListener {

    private final String TAB_1_TITLE = "Programs";
    private final String TAB_2_TITLE = "Hearing Test";
    private BottomSheetBehavior bottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setStatusBarTrans();
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText(TAB_1_TITLE));
        tabLayout.addTab(tabLayout.newTab().setText(TAB_2_TITLE));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new HearingProfileAdapter(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        View v = findViewById(R.id.sheet_volume);
        v.setOnClickListener(this);

        bottomSheetBehavior = BottomSheetBehavior.from(v);
        // set callback for changes
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // Called every time when the bottom sheet changes its state.
                System.out.println(newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //findViewById(R.id.bg_image).setAlpha(slideOffset);
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
        Toast.makeText(HearingProfile.this, "New corner radius is " + String.valueOf(val), Toast.LENGTH_SHORT).show();
    }
    public void setMax(View v) {
        int val = Integer.valueOf(v.getTag().toString());
        //bv.setMax(val);
        Toast.makeText(HearingProfile.this, "New max value is " + String.valueOf(val), Toast.LENGTH_SHORT).show();
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



