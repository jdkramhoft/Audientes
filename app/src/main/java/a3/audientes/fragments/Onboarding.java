package a3.audientes.fragments;

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

import a3.audientes.R;
import a3.audientes.activities.HearingProfile;
import a3.audientes.adapter.SliderAdapter;

public class Onboarding extends Fragment implements View.OnClickListener {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
    private Button mNextBtn, mSkipBtn;
    private int mCurrentPage;
    private final int NUM_OF_DOTS = 3;

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
                launchHearingTestScreen();
            }
            else {
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        }
        else if (v == mSkipBtn){
            launchHearingTestScreen();
        }
    }

    private void launchHearingTestScreen(){
        /*
        Utils.saveSharedSetting(Onboarding.this, MainMenu.PREF_USER_FIRST_TIME, "false");

        Intent hearingTestIntent = new Intent(this, BeginHearingTestActivity.class);
        hearingTestIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(hearingTestIntent);
        finish();
        */
        //int id = ((View)getView().getParent()).getId();
        /*
        if (getActivity()==null) return;
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction()
                .replace(R.id.emptyFrame, new ConnectDevice() )
                .addToBackStack(null)
                .commit();

         */
        Intent intent = new Intent(getActivity(), HearingProfile.class);
        startActivity(intent);



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
}