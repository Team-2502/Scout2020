package com.team2502.scout2020;

import android.util.Log;

public class ExportUtils {
    public static String createTIMDHeader(String match, String team, String mode, String driver_station, String scout_name, String is_replay){
        String rawTIMD = "A" + Integer.parseInt(match.substring(3)) + "B" + team + "C" + Constants.TIMD_COMPRESSION_KEYS.get(mode)
                + "D" + Constants.SCOUT_NAME_TO_KEY.get(scout_name) + "E" + Constants.TIMD_COMPRESSION_KEYS.get(driver_station)
                + "F" + Constants.TIMD_COMPRESSION_KEYS.get(is_replay);
        Log.e("timdHead", rawTIMD);
        return rawTIMD;
    }

    public static String createIncapAction(String timd_in_progress, String cause){
        return timd_in_progress + "G" + Constants.TIMD_COMPRESSION_KEYS.get("Incap") + "H" + Constants.TIMD_COMPRESSION_KEYS.get(cause) + ",";
    }

    public static String createShootAction(String timd_in_progress, String place, int miss, int lower, int outer, int inner, int total){
        return timd_in_progress + "G" + Constants.TIMD_COMPRESSION_KEYS.get("Shoot") + "I" + miss + "J" + lower + "K" + outer + "L" + inner + "M" + total + "N" + Constants.TIMD_COMPRESSION_KEYS.get(place);
    }
}
