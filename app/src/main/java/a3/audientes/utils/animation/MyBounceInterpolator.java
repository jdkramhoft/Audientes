package a3.audientes.utils.animation;

public class MyBounceInterpolator implements android.view.animation.Interpolator {
    private final double mAmplitude;
    private final double mFrequency;

    public MyBounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    // Inspired by the net
    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }
}
