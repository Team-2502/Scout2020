package com.team2502.scout2020;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;

import java.io.File;

public class ApplicationInstance extends Application {
    public static ApplicationInstance INSTANCE;

    // Set basic app settings when it is opened
    public void onCreate() {
        super.onCreate();
        INSTANCE = ApplicationInstance.this;
        setSp("scoutSerialNumber", Build.SERIAL);
        setSp("lastMatch", 0);
        setSp("isReplay", "false");
        setSp("isOverridden", 0);
        setSp("currentScoutName", "Other");
        setSp("fieldOrientation", "Left");

        // Create the scouting directory on the tablets filesystem if it does not exist yet
        File file = new File(Constants.SCOUTING_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    // Gets any value saved into shared preferences.
    public static SharedPreferences getSp() {
        return INSTANCE.getSharedPreferences(Constants.SHARED_PREF_KEY, Activity.MODE_PRIVATE);
    }

    // Gets the shared preference for string values
    public static String getSp(String key, String defaultValue) {
        return ApplicationInstance.getSp().getString(key, defaultValue);
    }

    // Gets the shared preference for integer values
    public static int getSp(String key, int defaultValue) {
        return ApplicationInstance.getSp().getInt(key, defaultValue);
    }

    // Sets shared preferences for string values
    public static void setSp(String key, String value) {
        ApplicationInstance.getSp().edit().putString(key, value).apply();
    }

    // Sets shared preferences for integer values
    public static void setSp(String key, int value) {
        ApplicationInstance.getSp().edit().putInt(key, value).apply();
    }
}
