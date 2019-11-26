package a3.audientes.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import a3.audientes.R;
import a3.audientes.adapter.HearingProfileAdapter;
import a3.audientes.fragments.Audiogram;
import a3.audientes.fragments.Tab1;
import a3.audientes.fragments.Tab2;

public class HearingProfile extends AppCompatActivity implements Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener, Audiogram.OnFragmentInteractionListener {

    private final String TAB_1_TITLE = "Modes";
    private final String TAB_2_TITLE = "Hearing Test";
    //private BoxedVertical bv;


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

/*
       // bv = (BoxedVertical)findViewById(R.id.boxed_vertical);

       // bv.setOnBoxedPointsChangeListener(new BoxedVertical.OnValuesChangeListener() {
            @Override
            public void onPointsChanged(BoxedVertical boxedPoints, final int points) {
               // valueTextView.setText("Current Value is " + String.valueOf(points));
            }

            @Override
            public void onStartTrackingTouch(BoxedVertical boxedPoints) {
                Toast.makeText(HearingProfile.this, "onStartTrackingTouch", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(BoxedVertical boxedPoints) {
                Toast.makeText(HearingProfile.this, "onStopTrackingTouch", Toast.LENGTH_SHORT).show();
            }
        });

        bv.setValue(60);

        System.out.println("VALUE: " + bv.getValue());
        //valueTextView.setText("Current Valuex is " + String.valueOf(bv.getValue()));

*/      //  AlertDialog hearable
        AlertDialog.Builder builderHearable = new AlertDialog.Builder(this);
        View hearableView = getLayoutInflater().inflate(R.layout.custom_popup_connect_hearable, null);
        Button buttonHearable =  (Button)hearableView.findViewById(R.id.button1);
        builderHearable.setView(hearableView);
        AlertDialog hearableDialog = builderHearable.create();

        buttonHearable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Connect");
            }
        });
        hearableDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  AlertDialog bluetooth
        AlertDialog.Builder builderbluetooth = new AlertDialog.Builder(this);
        View bluetoothView = getLayoutInflater().inflate(R.layout.custom_popup_connect_bluetooth, null);
        Button buttonbluetooth =  (Button)bluetoothView.findViewById(R.id.button1);
        builderbluetooth.setView(bluetoothView);
        AlertDialog bluetoothDialog = builderbluetooth.create();

        buttonbluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Connect");
            }
        });
        bluetoothDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                hearableDialog.show();
            }
        }, 1000 );

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                bluetoothDialog.show();
            }
        }, 5000 );

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

}


