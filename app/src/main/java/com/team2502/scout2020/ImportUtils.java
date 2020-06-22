package com.team2502.scout2020;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

// Mostly taken from https://github.com/frc1678/scout-2019
public class ImportUtils {

    // Save data to internal storage as a file in the scouting directory
    public static void writeFileToStorage(String fileName, String pathInDir, String data) {
        // Create directory if it does not exist
        File directory = new File(Constants.SCOUTING_DIR + pathInDir);
        if (!directory.exists()) {
            directory.mkdir();
        }
        // Write file and save in directory specified
        try {
            File file = new File(directory, fileName);
            FileWriter writer = new FileWriter(file);
            writer.append(data);
            writer.flush();
            writer.close();
        }
        catch (Exception e) {
            Log.e("File", e.toString());
        }
    }

    // Returns the data in a file at a specified location, if the file does not exist returns null
    public static String retrieveFile(String fileName, String pathInDir){
        final File[] files = new File(Constants.SCOUTING_DIR + pathInDir).listFiles();

        try{
            if(!(files == null)){
                for(File file: files){
                    if(file.getName().equals(fileName)){
                        return readFile(file.getPath());
                    }
                }
            }
        }
        catch(Exception e){
            Log.e("File", e.toString());
        }

        return null;
    }

    // Reads a file and returns its contents
    public static String readFile(String pathName) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pathName))));
        }
        catch (IOException e) {
            Log.e("File", e.toString());
            return null;
        }
        String dataOfFile = "";
        String buffer;
        try {
            while ((buffer = reader.readLine()) != null) {
                dataOfFile = dataOfFile.concat(buffer + "\n");
            }
        }
        catch (IOException e) {
            Log.e("File", e.toString());
            return null;
        }
        return dataOfFile;
    }

    // Set the data for the next match from the assignments file in Shared Preferences if it is not overridden
    public static void getMatchData(String scoutNumber, String matchNumber) {
        // Check if scout has overridden the assignment file
        if(ApplicationInstance.getSp("isOverridden", 0) == 1){
            ApplicationInstance.setSp("isOverridden", 0);
            ApplicationInstance.setSp("assignmentMode", "override");
        }
        else{
            try {
                // Interpret the file as JSON
                JSONObject backupData = new JSONObject(Objects.requireNonNull(retrieveFile("assignments.txt", "/")));

                backupData = backupData.getJSONObject(matchNumber).getJSONObject(scoutNumber);

                String allianceColor = backupData.getString("alliance");
                int teamNum = backupData.getInt("team");

                ApplicationInstance.setSp("alliance", allianceColor);
                ApplicationInstance.setSp("team", teamNum);
                ApplicationInstance.setSp("assignmentMode", "file");

            }
            catch (JSONException e) {
                Log.e("File", e.toString());
            }
        }
    }
}
