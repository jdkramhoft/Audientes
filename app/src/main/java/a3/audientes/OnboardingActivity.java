package a3.audientes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import utils.Utils;

public class OnboardingActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
    private Button mNextBtn, mBackBtn;
    private int mCurrentPage;
    private final int NUM_OF_DOTS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.dotsLayout);

        mNextBtn = findViewById(R.id.nextbtn);
        mBackBtn = findViewById(R.id.backbtn);

        mNextBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);

        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);
    }


    public void addDotsIndicator(int position){
        mDots = new TextView[NUM_OF_DOTS];
        mDotLayout.removeAllViews();

        for (int i = 0; i < NUM_OF_DOTS; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }

        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
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
        else if (v == mBackBtn){
            mSlideViewPager.setCurrentItem(mCurrentPage - 1);
        }
    }

    private void launchHearingTestScreen(){
        Utils.saveSharedSetting(OnboardingActivity.this, MainMenu.PREF_USER_FIRST_TIME, "false");

        Intent hearingTestIntent = new Intent(this, BeginHearingTestActivity.class);
        hearingTestIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(hearingTestIntent);
        finish();
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

                mBackBtn.setEnabled(false);
                mBackBtn.setText("");
                mBackBtn.setVisibility(View.INVISIBLE);
            }
            else if (lastPage){
                mNextBtn.setEnabled(true);
                mNextBtn.setText("Finish");

                mBackBtn.setEnabled(true);
                mBackBtn.setText("Back");
                mBackBtn.setVisibility(View.VISIBLE);

            }
            else {
                mNextBtn.setEnabled(true);
                mNextBtn.setText("Next");

                mBackBtn.setEnabled(true);
                mBackBtn.setText("Back");
                mBackBtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };

}
