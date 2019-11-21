package utils;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import a3.audientes.MyBounceInterpolator;
import a3.audientes.R;

public class AnimBtnUtil {


    public static void bounce(Button btn, FragmentActivity activity) {
        final Animation myAnim = AnimationUtils.loadAnimation(activity, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        btn.startAnimation(myAnim);
    }
}
