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

import java.util.Objects;

import a3.audientes.R;

import static a3.audientes.R.string.*;

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

    private String[] slide_headings;

    private String[] slide_descs;

    public OnboardingSliderAdapter(Context context){
        this.context = context;
        slide_headings = new String[]{
                context.getString(slidehead1),
                context.getString(slidehead2),
                context.getString(slidehead3),
                context.getString(slidehead4),
                context.getString(slidehead5),
                context.getString(slidehead6)
        };
        slide_descs = new String[]{
                context.getString(slidedesc1),
                context.getString(slidedesc2),
                context.getString(slidedesc3),
                context.getString(slidedesc4),
                context.getString(slidedesc5),
                context.getString(slidedesc6)
        };
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