package com.team2502.scout2020;

import android.os.Environment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String SHARED_PREF_KEY = "com.2502.scout2019.sp";
    public static final File SCOUTING_DIR = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Scouting2020");

    public static final Map<String, String> SERIAL_TO_SCOUT = new HashMap<String, String>() {{
        put("G000L40763270NAL", "scout1");
        put("G000L40763270V9A", "scout2");
        put("G000L40763270WH5", "scout3");
        put("G000L40763270QKL", "scout4");
        put("G000L40763270NX9", "scout5");
        put("G000L40763270WLQ", "scout6");
        put("G000L40763270R0J", "scout1");
    }};
}
