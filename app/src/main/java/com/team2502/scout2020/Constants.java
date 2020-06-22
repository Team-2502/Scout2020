package com.team2502.scout2020;

import android.os.Environment;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String SHARED_PREF_KEY = "com.2502.scout2020.sp";
    public static final String SCOUTING_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Scouting2020";

    public static final Map<String, String> SERIAL_TO_SCOUT = new HashMap<String, String>() {{
        put("G000L40763270NAL", "scout1");
        put("G000L40763270V9A", "scout2");
        put("G000L40763270WH5", "scout3");
        put("G000L40763270QKL", "scout4");
        put("G000L40763270NX9", "scout5");
        put("G000L40763270WLQ", "scout6");
        put("G000L40763270R0J", "scout1");
        put("G0W0H40470330BNR", "chairmans");
    }};

    public static final Map<String, String> SCOUT_NAME_TO_KEY = new HashMap<String, String>() {{
        put("Adhi", "a");
        put("Aditya", "b");
        put("Aedin", "c");
        put("Christian", "e");
        put("Christopher", "f");
        put("Daniel", "r");
        put("Danny", "g");
        put("Drew", "h");
        put("Evan", "i");
        put("Ishan", "l");
        put("Jacob", "m");
        put("Justin", "n");
        put("Michael", "p");
        put("Nate", "t");
        put("Neel", "u");
        put("Nicole", "v");
        put("Nidhi", "w");
        put("Pearl", "x");
        put("Ravisha", "y");
        put("Rahul", "z");
        put("Riley", "aa");
        put("Ryan A", "ac");
        put("Ryan M", "ad");
        put("Serena", "ae");
        put("Varun", "af");
        put("Vedanth", "ag");
        put("Other", "ah");
    }};

    public static final Map<String, String> TIMD_COMPRESSION_KEYS = new HashMap<String, String>() {{
        put("true", "t");
        put("false", "f");
        put("file", "a");
        put("override", "b");
        put("Red 1", "c");
        put("Red 2", "d");
        put("Red 3", "e");
        put("Blue 1", "g");
        put("Blue 2", "h");
        put("Blue 3", "i");
        put("Incap", "j");
        put("Shoot", "k");
        put("trenchRun", "l");
        put("targetZone", "m");
        put("leftTopLeft", "n");
        put("leftBottomLeft", "o");
        put("leftTopRight", "p");
        put("leftBottomRight", "q");
        put("rightTopLeft", "r");
        put("rightTopRight", "s");
        put("rightBottomLeft", "u");
        put("rightBottomRight", "v");
        put("Rotation Control", "w");
        put("Position Control", "x");
        put("Stuck on Ball/Boundary", "y");
        put("Tipped Over", "z");
        put("Disabled/E-Stopped", "aa");
        put("Other", "ab");
        put("Recap", "ac");
        put("Wheel", "ad");
        put("Offense", "ae");
        put("Defense", "af");
        put("Climb", "ag");
        put("Parked", "ah");
        put("Not Parked", "ai");
        put("Left", "aj");
        put("Center", "ak");
        put("Right", "al");
        put("Hanging", "am");
        put("teleop", "an");
        put("initLine", "ao");
        put("failedClimb", "ap");
        put("auto", "aq");
    }};
}
