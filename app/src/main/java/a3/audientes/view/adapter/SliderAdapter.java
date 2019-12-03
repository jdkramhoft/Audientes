package a3.audientes.view.adapter;

import android.content.Context;
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

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private int[] slide_images = {
            R.drawable.boarding_image_handshake,
            R.drawable.boarding_image_speaker,
            R.drawable.boarding_image_liftoff
    };

    private String[] slide_headings = {
            "Welcome to Audientes",
            "Your surroundings",
            "Let's get started",
    };

    private String[] slide_descs = {
            "Your hearable will work optimally when you perform a hearing test using the built-in Pure Tone Audiometry, just like when visiting a hearing specialist. ",
            "Before starting the hearing test, we check the surrounding noise level, if it is greater than expected value, we would suggest that the user find another quiet place.",
            "You will be asked to wear the hearable and sit in a quiet area free for disturbing sounds and other factors. A series of sounds will be broadcast through the earphones. It will measure your ability to hear sounds at various pitches and volumes. "
    };

    public SliderAdapter(Context context){
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
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboarding_slider, container, false);

        ImageView slideImageView = view.findViewById(R.id.slide_image);
        TextView slideHeading = view.findViewById(R.id.slide_heading);
        TextView slideDescription = view.findViewById(R.id.slide_desc);

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
