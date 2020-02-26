package com.team2502.scout2020;

import android.util.Log;

public class ExportUtils {
    public static String createTIMDHeader(String match, String team, String mode, String driver_station, String scout_name, String is_replay){
        String rawTIMD = "A" + Integer.parseInt(match.substring(3)) + "B" + team + "C" + Constants.TIMD_COMPRESSION_KEYS.get(mode)
                + "D" + Constants.SCOUT_NAME_TO_KEY.get(scout_name) + "E" + Constants.TIMD_COMPRESSION_KEYS.get(driver_station)
                + "F" + Constants.TIMD_COMPRESSION_KEYS.get(is_replay) + ",";
        Log.e("timdHead", rawTIMD);
        return rawTIMD;
    }

    public static String createIncapAction(String timd_in_progress, String cause){
        return timd_in_progress + "G" + Constants.TIMD_COMPRESSION_KEYS.get("Incap") + "H" + Constants.TIMD_COMPRESSION_KEYS.get(cause) + ",";
    }

    public static String createShootAction(String timd_in_progress, String place, int miss, int lower, int outer, int inner, int total){
        return timd_in_progress + "G" + Constants.TIMD_COMPRESSION_KEYS.get("Shoot") + "I" + miss + "J" + lower + "K" + outer + "L" + inner + "M" + total + "N" + Constants.TIMD_COMPRESSION_KEYS.get(place) + ",";
    }

    public static String createWheelAction(String timd_in_progress, String option){
        return timd_in_progress + "G" + Constants.TIMD_COMPRESSION_KEYS.get("Wheel") + "O" + Constants.TIMD_COMPRESSION_KEYS.get(option) + ",";
    }

    public static String createDefenseAction(String timd_in_progress, int time) {
        return timd_in_progress + "G" + Constants.TIMD_COMPRESSION_KEYS.get("Defense") + "P" + time + ",";
    }

    public static String createHangAction(String timd_in_progress, String level, String location, String side){
        return timd_in_progress + "G" + Constants.TIMD_COMPRESSION_KEYS.get("Climb") + "Q" + Constants.TIMD_COMPRESSION_KEYS.get(level) + "R" + Constants.TIMD_COMPRESSION_KEYS.get(location) + "S" + Constants.TIMD_COMPRESSION_KEYS.get(side) + ",";
    }
    public static String createParkAction(String timd_in_progress, String location){
        return timd_in_progress + "G" + Constants.TIMD_COMPRESSION_KEYS.get("Climb") + "R" + Constants.TIMD_COMPRESSION_KEYS.get(location) + ",";
    }
}
