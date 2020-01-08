package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.utils.Utils;

/**
 * https://www.sundanesepeople.com/creating-onboarding-screen-android-studio/
 */

public class SharedPrefUtil {

    private static final String PREFERENCES_FILE = "myPref";
    private static final int WIDTH_INDEX = 0;
    private static final int HEIGHT_INDEX = 0;

    private Context mContext;

    public SharedPrefUtil(Context context) {
        this.mContext = context;
    }

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }
    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blur(AppCompatActivity activity) {
        Bitmap bitmap = takeScreenShot(activity);

        RenderScript renderScript = RenderScript.create(activity);

        // This will blur the bitmapOriginal with a radius of 16 and save it in bitmapOriginal
        final Allocation input = Allocation.createFromBitmap(renderScript, bitmap); // Use this constructor for best performance, because it uses USAGE_SHARED mode which reuses memory
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        script.setRadius(16f);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmap);

        return bitmap;
    }

    public static Bitmap takeScreenShot(AppCompatActivity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int[] widthHeight = getScreenSize(activity.getParent());
        Bitmap bitmapResult = Bitmap.createBitmap(bitmap, 0, statusBarHeight, widthHeight[0], widthHeight[1]  - statusBarHeight);
        view.destroyDrawingCache();
        return bitmapResult;
    }
    public static int[] getScreenSize(Context context) {
        int[] widthHeight = new int[2];
        widthHeight[WIDTH_INDEX] = 0;
        widthHeight[HEIGHT_INDEX] = 0;

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        widthHeight[WIDTH_INDEX] = size.x;
        widthHeight[HEIGHT_INDEX] = size.y;

        if (!isScreenSizeRetrieved(widthHeight)) {
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            widthHeight[0] = metrics.widthPixels;
            widthHeight[1] = metrics.heightPixels;
        }

        if (!isScreenSizeRetrieved(widthHeight)) {
            widthHeight[0] = display.getWidth(); // deprecated
            widthHeight[1] = display.getHeight(); // deprecated
        }

        return widthHeight;
    }

    private static boolean isScreenSizeRetrieved(int[] widthHeight) {
        return widthHeight[WIDTH_INDEX] != 0 && widthHeight[HEIGHT_INDEX] != 0;
    }
}
