package a3.audientes.utils;

import android.content.Context;

public class SharedPrefUtil {


    public static final String CURRENT_PROGRAM = "currentProgram";
    public static final String CURRENT_AUDIOGRAM = "currentAudiogram";

    private static final String PREFERENCES_FILE = "prefs";

    public static String readSetting(Context ctx, String settingName) {
        return readSetting(ctx, settingName, null);
    }

    public static String readSetting(Context ctx, String settingName, String defaultValue) {
        return ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
                .getString(settingName, defaultValue);
    }

    public static void saveSetting(Context ctx, String settingName, String settingValue) {
        ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
                .edit()
                .putString(settingName, settingValue)
                .apply();
    }
}
