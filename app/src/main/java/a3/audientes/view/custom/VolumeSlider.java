package a3.audientes.view.custom;

/**
 * Source: https://github.com/alpbak/BoxedVerticalSeekBar/blob/master/BoxedVerticalSeekBarLib/src/main/java/abak/tr/com/boxedverticalseekbar/BoxedVertical.java
 * Created by alpaslanbak on 29/09/2017.
 * Modified by Nick Panagopoulos @npanagop on 12/05/2018.
 * Modified by Michael Lund Jarberg @jarberg on 01/11/2019
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import a3.audientes.R;


public class VolumeSlider extends View{
    private static final String TAG = VolumeSlider.class.getSimpleName();
    private static final int SOUND_MAX = 15;
    private static final int SOUND_MIN = 0;
    private final int LR_ID = 2131230801;

    private static final int MAX = 100;
    private static final int MIN = 0;

    /**
     * The min value of progress value.
     */
    private int mMin = MIN;

    /**
     * The Maximum value that this SeekArc can be set to
     */
    private int mMax = MAX;

    /**
     * The increment/decrement value for each movement of progress.
     */
    private int mStep = 10;

    /**
     * The corner radius of the view.
     */
    private int mCornerRadius = 10;

    /**
     * Text size in SP.
     */
    private float mTextSize = 26;

    /**
     * Text bottom padding in pixel.
     */
    private int mtextBottomPadding = 20;

    private int mPoints;

    private boolean mEnabled = true;
    /**
     * Enable or disable text .
     */
    private boolean mtextEnabled = true;

    /**
     * Enable or disable image .
     */
    private boolean mImageEnabled = false;

    /**
     * mTouchDisabled touches will not move the slider
     * only swipe motion will activate it
     */
    private boolean mTouchDisabled = true;

    private float mProgressSweep = 0;
    private Paint mProgressPaint;
    private Paint mTextPaint;
    private int scrWidth;
    private int scrHeight;
    private OnValuesChangeListener mOnValuesChangeListener;
    private int backgroundColor;
    private int mDefaultValue;
    private Bitmap mDefaultImage;
    private Bitmap mMinImage;
    private Bitmap mMaxImage;
    private Rect dRect = new Rect();
    private boolean firstRun = true;
    private AudioManager audioManager;
    private boolean beingTouched;

    public VolumeSlider(Context context) {
        super(context);
        init(context, null);
    }

    public VolumeSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        float density = getResources().getDisplayMetrics().density;

        // Defaults, may need to link this into theme settings
        int progressColor = ContextCompat.getColor(context, R.color.color_progress);
        backgroundColor = ContextCompat.getColor(context, R.color.color_background);

        int textColor = ContextCompat.getColor(context, R.color.color_text);
        mTextSize = (int) (mTextSize * density);

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int streamVolume= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int newRange = mMax - mMin;
        int oldRange = (SOUND_MAX - SOUND_MIN);
        int soundValue = (((streamVolume - mMin) * newRange) / oldRange);
        mDefaultValue = soundValue > mMax ? mMax/2 : soundValue;

        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.VolumeSlider, 0, 0);

            mPoints = a.getInteger(R.styleable.VolumeSlider_points, mPoints);
            mMax = a.getInteger(R.styleable.VolumeSlider_max, mMax);
            mMin = a.getInteger(R.styleable.VolumeSlider_min, mMin);
            mStep = a.getInteger(R.styleable.VolumeSlider_step, mStep);
            mDefaultValue = a.getInteger(R.styleable.VolumeSlider_defaultValue, mDefaultValue);
            mCornerRadius = a.getInteger(R.styleable.VolumeSlider_libCornerRadius, mCornerRadius);
            mtextBottomPadding = a.getInteger(R.styleable.VolumeSlider_textBottomPadding, mtextBottomPadding);
            //Images
            mImageEnabled = a.getBoolean(R.styleable.VolumeSlider_imageEnabled, mImageEnabled);

            if (mImageEnabled){
               //  Assert.assertNotNull("When images are enabled, defaultImage can not be null. Please assign a drawable in the layout XML file", a.getDrawable(R.styleable.BoxedVertical_defaultImage));
               // Assert.assertNotNull("When images are enabled, minImage can not be null. Please assign a drawable in the layout XML file", a.getDrawable(R.styleable.BoxedVertical_minImage));
               // Assert.assertNotNull("When images are enabled, maxImage can not be null. Please assign a drawable in the layout XML file", a.getDrawable(R.styleable.BoxedVertical_maxImage));
                mDefaultImage = ((BitmapDrawable) a.getDrawable(R.styleable.VolumeSlider_defaultImage)).getBitmap();
                mMinImage = ((BitmapDrawable) a.getDrawable(R.styleable.VolumeSlider_minImage)).getBitmap();
                mMaxImage = ((BitmapDrawable) a.getDrawable(R.styleable.VolumeSlider_maxImage)).getBitmap();
            }

            progressColor = a.getColor(R.styleable.VolumeSlider_progressColor, progressColor);
            backgroundColor = a.getColor(R.styleable.VolumeSlider_backgroundColor, backgroundColor);

            mTextSize = (int) a.getDimension(R.styleable.VolumeSlider_textSize, mTextSize);
            textColor = a.getColor(R.styleable.VolumeSlider_textColor, textColor);

            mEnabled = a.getBoolean(R.styleable.VolumeSlider_enabled, mEnabled);
            mTouchDisabled = a.getBoolean(R.styleable.VolumeSlider_touchDisabled, mTouchDisabled);
            mtextEnabled = a.getBoolean(R.styleable.VolumeSlider_textEnabled, mtextEnabled);

            mPoints = mDefaultValue;

            a.recycle();
        }

        // range check
        mPoints = (mPoints > mMax) ? mMax : mPoints;
        mPoints = (mPoints < mMin) ? mMin : mPoints;

        final int[] COLLORS = new int[]{
            Color.rgb(251,187,112),
            Color.rgb(203,64,197)
        };

        mProgressPaint = new Paint();
        mProgressPaint.setShader(new LinearGradient(0, 800, 0, getHeight(), COLLORS[1],COLLORS[0], Shader.TileMode.CLAMP));
        mProgressPaint.setColor(progressColor);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setColor(textColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mTextSize);

        scrHeight = context.getResources().getDisplayMetrics().heightPixels;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        scrWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        scrHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        mProgressPaint.setStrokeWidth(scrWidth);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();

        paint.setAlpha(255);
        canvas.translate(0, 0);
        Path mPath = new Path();
        mPath.addRoundRect(new RectF(0, 0, scrWidth, scrHeight), mCornerRadius, mCornerRadius, Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.INTERSECT);
        paint.setColor(backgroundColor);
        paint.setAntiAlias(true);
        canvas.drawRect(0, 0, scrWidth, scrHeight, paint);

        canvas.drawLine(canvas.getWidth()/2, canvas.getHeight(), canvas.getWidth()/2, mProgressSweep, mProgressPaint);

        if (mImageEnabled && mDefaultImage != null && mMinImage != null && mMaxImage != null){
            //If image is enabled, text will not be shown
            if (mPoints >= mMax-10){
                drawIcon(mMaxImage, canvas);
            }
            else if (mPoints <= mMin+10){
                drawIcon(mMinImage, canvas);
            }
            else{
                drawIcon(mDefaultImage, canvas);
            }
        }
        else{
            //If image is disabled and text is enabled show text
            if (mtextEnabled){
                String strPoint = String.valueOf(mPoints);
                drawText(canvas, mTextPaint, strPoint);
            }
        }

        if (firstRun){
            firstRun = false;
            setValue(mPoints);
        }
    }

    private void drawText(Canvas canvas, Paint paint, String text) {
        canvas.getClipBounds(dRect);
        int cWidth = dRect.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), dRect);
        float x = cWidth / 2f - dRect.width() / 2f - dRect.left;
        canvas.drawText(text, x, canvas.getHeight()-mtextBottomPadding, paint);
    }

    private void drawIcon(Bitmap bitmap, Canvas canvas){
        bitmap = getResizedBitmap(bitmap,canvas.getWidth()/2, canvas.getWidth()/2);
        RectF rectF = new RectF((canvas.getWidth()/2)-(bitmap.getWidth()/2), (canvas.getHeight()-bitmap.getHeight()-30), (canvas.getWidth()/3)+bitmap.getWidth(), (canvas.getHeight()-40));
        canvas.drawBitmap(bitmap, null, rectF, null);
    }

    private Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        //Thanks Piyush
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mEnabled) {

            this.getParent().requestDisallowInterceptTouchEvent(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mOnValuesChangeListener != null)
                        mOnValuesChangeListener.onStartTrackingTouch(this);

                    if (!mTouchDisabled)
                        updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    updateOnTouch(event);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (mOnValuesChangeListener != null){
                        mOnValuesChangeListener.onStopTrackingTouch(this);
                    }
                    beingTouched = false;
                    setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * Update the UI components on touch events.
     *
     * @param event MotionEvent
     */
    private void updateOnTouch(MotionEvent event) {
        beingTouched = true;
        setPressed(true);
        double mTouch = convertTouchEventPoint(event.getY());
        int progress = (int) Math.round(mTouch);
        updateProgress(progress);
    }

    private double convertTouchEventPoint(float yPos) {
        float wReturn;

        if (yPos > (scrHeight *2)) {
            wReturn = scrHeight *2;
            return wReturn;
        }
        else if(yPos < 0){
            wReturn = 0;
        }
        else {
            wReturn =  yPos;
        }

        return wReturn;
    }

    private void updateProgress(int progress) {
        mProgressSweep = progress;

        progress = (progress > scrHeight) ? scrHeight : progress;
        progress = (progress < 0) ? 0 : progress;

        //convert progress to min-max range
        mPoints = progress * (mMax - mMin) / scrHeight + mMin;

        if (this.getId() == R.id.boxedM){
            int oldRange = mMax - mMin;
            int newRange = (SOUND_MAX - SOUND_MIN);
            int soundValue = 15 - (((mPoints - mMin) * newRange) / oldRange);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, soundValue, 0);
        }

        //reverse value because progress is descending
        mPoints = mMax + mMin - mPoints;
        //if value is not max or min, apply step
        if (mPoints != mMax && mPoints != mMin) {
            mPoints = mPoints - (mPoints % mStep) + (mMin % mStep);
        }

        if (mOnValuesChangeListener != null) {
            mOnValuesChangeListener
                    .onPointsChanged(this, mPoints);
        }


        invalidate();
    }

    /**
     * Gets a value, converts it to progress for the seekBar and updates it.
     * @param value The value given
     */
    private void updateProgressByValue(int value) {
        mPoints = value;

        mPoints = (mPoints > mMax) ? mMax : mPoints;
        mPoints = (mPoints < mMin) ? mMin : mPoints;

        //convert min-max range to progress
        mProgressSweep = (mPoints - mMin) * scrHeight/(mMax - mMin);
        //reverse value because progress is descending
        mProgressSweep = scrHeight - mProgressSweep;

        if (mOnValuesChangeListener != null) {
            mOnValuesChangeListener
                    .onPointsChanged(this, mPoints);
        }

        invalidate();
    }

    public interface OnValuesChangeListener {
        /**
         * Notification that the point value has changed.
         *
         * @param boxedPoints The SwagPoints view whose value has changed
         * @param points     The current point value.
         */
        void onPointsChanged(VolumeSlider boxedPoints, int points);
        void onStartTrackingTouch(VolumeSlider boxedPoints);
        void onStopTrackingTouch(VolumeSlider boxedPoints);
    }

    public void setValue(int points) {
        points = points > mMax ? mMax : points;
        points = points < mMin ? mMin : points;

        updateProgressByValue(points);
    }

    public int getValue() {
        return mPoints;
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    public int getMax() {
        return mMax;
    }

    public int getMin(){
        return mMin;
    }

    public int getSoundMax(){
        return SOUND_MAX;
    }

    public int getSoundMin(){
        return SOUND_MIN;
    }

    public void setMax(int mMax) {
        if (mMax <= mMin)
            throw new IllegalArgumentException("Max should not be less than zero");
        this.mMax = mMax;
    }

    public void setCornerRadius(int mRadius) {
        this.mCornerRadius = mRadius;
        invalidate();
    }

    public int getCornerRadius() {
        return mCornerRadius;
    }

    public int getDefaultValue() {
        return mDefaultValue;
    }

    public void setDefaultValue(int mDefaultValue) {
        if (mDefaultValue > mMax)
            throw new IllegalArgumentException("Default value should not be bigger than max value.");
        this.mDefaultValue = mDefaultValue;

    }

    public int getStep() {
        return mStep;
    }

    public void setStep(int step) {
        mStep = step;
    }

    public boolean isImageEnabled() {
        return mImageEnabled;
    }

    public void setImageEnabled(boolean mImageEnabled) {
        this.mImageEnabled = mImageEnabled;
    }

    public void setOnBoxedPointsChangeListener(OnValuesChangeListener onValuesChangeListener) {
        mOnValuesChangeListener = onValuesChangeListener;
    }

    public boolean isBeingTouched() {
        return beingTouched;
    }

    public void setBeingTouched(boolean beingTouched) {
        this.beingTouched = beingTouched;
    }
}