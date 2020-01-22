package a3.audientes.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.Objects;

import a3.audientes.R;
import a3.audientes.view.adapter.OnboardingSliderAdapter;
import a3.audientes.utils.SharedPrefUtil;

import static java.lang.Thread.sleep;

public class Onboarding extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private Button mNextBtn, mSkipBtn;
    private int mCurrentPage;
    private static final int NUM_OF_DOTS = 6;
    private boolean newVisitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);

        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.dotsLayout);

        mNextBtn = findViewById(R.id.nextbtn);
        mSkipBtn = findViewById(R.id.skipbtn);

        mNextBtn.setOnClickListener(this);
        mSkipBtn.setOnClickListener(this);

        OnboardingSliderAdapter sliderAdapter = new OnboardingSliderAdapter(getBaseContext());
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);
        newVisitor = Boolean.valueOf(SharedPrefUtil.readSetting(this, getString(R.string.new_visitor_pref), "true"));

    }


    public void addDotsIndicator(int position){
        mDots = new TextView[NUM_OF_DOTS];
        mDotLayout.removeAllViews();

        for (int i = 0; i < NUM_OF_DOTS; i++){
            mDots[i] = new TextView(getBaseContext());
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(50);
            mDots[i].setTextColor(getResources().getColor(R.color.transparentWhite, null));
            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.darkerOrange, null));
        }
    }


    @Override
    public void onClick(View v) {
        boolean lastPage = mCurrentPage == mDots.length-1;
        if (v == mNextBtn){
            if (lastPage){
                launchActivity();
            }
            else {
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        }
        else if (v == mSkipBtn){
            launchActivity();
        }
    }

    private void launchActivity(){
        Intent intent;
        int currentAudiogramId = Integer.parseInt(SharedPrefUtil.readSetting(getBaseContext(), "currentAudiogram", "0"));
        if (!isHearableConnected())
            intent = new Intent(getBaseContext(), BluetoothPairing.class);
        else if (newVisitor || currentAudiogramId == 0){
            SharedPrefUtil.saveSetting(Objects.requireNonNull(this), getString(R.string.new_visitor_pref), "false");
            intent = new Intent(this, HearingTest.class);
        }
        else{
            SharedPrefUtil.saveSetting(Objects.requireNonNull(this), getString(R.string.new_visitor_pref), "false");
            intent = new Intent(this, HearingProfile.class);
        }
        startActivity(intent);
        Objects.requireNonNull(this).finish();
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int i) {
            boolean firstPage = i == 0, lastPage = i == mDots.length-1;
            addDotsIndicator(i);
            mCurrentPage = i;
            if (firstPage){
                mNextBtn.setEnabled(true);
                mNextBtn.setText(R.string.next);
            }
            else if (lastPage){
                mNextBtn.setEnabled(true);
                mNextBtn.setText(R.string.start);
            }
            else {
                mNextBtn.setEnabled(true);
                mNextBtn.setText(R.string.next);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };

    private boolean isHearableConnected() {
        BluetoothManager manager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);

        BluetoothAdapter a = manager.getAdapter();

        if (a != null){
            for (BluetoothDevice e: a.getBondedDevices()) {
                if(isConnected(e)){
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isConnected(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("isConnected", (Class[]) null);
            return (boolean) m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}