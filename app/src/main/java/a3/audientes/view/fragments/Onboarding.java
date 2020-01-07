package a3.audientes.view.fragments;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import a3.audientes.R;
import a3.audientes.bluetooth.BluetoothPairingActivity;
import a3.audientes.view.activities.HearingProfile;
import a3.audientes.view.adapter.SliderAdapter;
import utils.SharedPrefUtil;

import static java.lang.Thread.sleep;

public class Onboarding extends Fragment implements View.OnClickListener {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
    private Button mNextBtn, mSkipBtn;
    private int mCurrentPage;
    private final int NUM_OF_DOTS = 3;
    private boolean newVisitor;

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {

        View rod = i.inflate(R.layout.onboarding, container, false);


        mSlideViewPager = rod.findViewById(R.id.slideViewPager);
        mDotLayout = rod.findViewById(R.id.dotsLayout);

        mNextBtn = rod.findViewById(R.id.nextbtn);
        mSkipBtn = rod.findViewById(R.id.skipbtn);

        mNextBtn.setOnClickListener(this);
        mSkipBtn.setOnClickListener(this);

        sliderAdapter = new SliderAdapter(getContext());
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);


        newVisitor = Boolean.valueOf(SharedPrefUtil.readSharedSetting(getActivity(), getString(R.string.new_visitor_pref), "true"));

        return rod;
    }


    public void addDotsIndicator(int position){
        mDots = new TextView[NUM_OF_DOTS];
        mDotLayout.removeAllViews();

        for (int i = 0; i < NUM_OF_DOTS; i++){
            mDots[i] = new TextView(getContext());
            mDots[i].setText(Html.fromHtml("&#8226;"));


            mDots[i].setTextSize(50);
            mDots[i].setTextColor(getResources().getColor(R.color.transparentWhite));

            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.darkerOrange));
        }
    }


    @Override
    public void onClick(View v) {
        boolean lastPage = mCurrentPage == mDots.length-1;

        if (v == mNextBtn){
            if (lastPage){
                launchAcitivity();
            }
            else {
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        }
        else if (v == mSkipBtn){
            launchAcitivity();
        }
    }

    private void launchAcitivity(){
        Intent intent;
        if (!isHearableConnected())
            intent = new Intent(getContext(), BluetoothPairingActivity.class);
        else if (newVisitor){
            SharedPrefUtil.saveSharedSetting(Objects.requireNonNull(getActivity()), getString(R.string.new_visitor_pref), "false");
            intent = new Intent(getActivity(), HearingTest.class);
        }
        else{
            SharedPrefUtil.saveSharedSetting(Objects.requireNonNull(getActivity()), getString(R.string.new_visitor_pref), "false");
            intent = new Intent(getActivity(), HearingProfile.class);
        }

        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int i) {
            boolean firstPage = i == 0, lastPage = i == mDots.length-1;

            addDotsIndicator(i);
            mCurrentPage = i;

            if (firstPage){
                mNextBtn.setEnabled(true);
                mNextBtn.setText("Next");
            }
            else if (lastPage){
                mNextBtn.setEnabled(true);
                mNextBtn.setText("Start");
            }
            else {
                mNextBtn.setEnabled(true);
                mNextBtn.setText("Next");
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };

    private boolean isHearableConnected() {
        // TODO: somehow check if the correct device is already connected
        return false;
    }
}