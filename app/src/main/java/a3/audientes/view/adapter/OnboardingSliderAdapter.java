package a3.audientes.view.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import a3.audientes.R;

/**
 * https://www.iconfinder.com/
 */

public class OnboardingSliderAdapter extends PagerAdapter {

    private Context context;

    private int[] slide_images = {
            R.drawable.boarding_image_handshake,
            R.drawable.boarding_image_speaker,
            R.drawable.boarding_image_liftoff,
            R.drawable.usynlig,
            R.drawable.usynlig,
            R.drawable.boarding_image_db,
    };

    private String[] slide_headings = {
            context.getString(R.string.slidehead1),
            context.getString(R.string.slidehead2),
            context.getString(R.string.slidehead3),
            context.getString(R.string.slidehead4),
            context.getString(R.string.slidehead5),
            context.getString(R.string.slidehead6)
    };

    private String[] slide_descs = {
            context.getString(R.string.slidedesc1),
            context.getString(R.string.slidedesc2),
            context.getString(R.string.slidedesc3),
            context.getString(R.string.slidedesc4),
            context.getString(R.string.slidedesc5),
            context.getString(R.string.slidedesc6)
    };

    public OnboardingSliderAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboarding_slider, container, false);

        ImageView slideImageView = view.findViewById(R.id.slide_image);
        TextView slideHeading = view.findViewById(R.id.slide_heading);
        TextView slideDescription = view.findViewById(R.id.slide_desc);

        if (position == 3) {
            slideImageView.setBackgroundResource(R.drawable.longclick_animation);
            AnimationDrawable animAnimation = (AnimationDrawable) slideImageView.getBackground();
            animAnimation.start();
        }
        else if (position == 4) {
            slideImageView.setBackgroundResource(R.drawable.edit_program_animation);
            AnimationDrawable animAnimation = (AnimationDrawable) slideImageView.getBackground();
            animAnimation.start();
        }
        else
            slideImageView.setImageResource(slide_images[position]);

        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}