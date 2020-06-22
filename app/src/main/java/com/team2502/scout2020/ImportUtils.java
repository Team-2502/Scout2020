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

public class ImportUtils {
    //Saves scout data as text file in tablet internal storage
    public static void writeFileToStorage(String sFileName, String pathInDir, String sBody) {
        File file = new File(Constants.SCOUTING_DIR + pathInDir);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String retrieveFile(String pFileName, String pathInDir){
        final File[] files = new File(Constants.SCOUTING_DIR + pathInDir).listFiles();

        try{
            if(!(files == null)){
                for(File tfile: files){
                    if(tfile.getName().equals(pFileName)){
                        return readFile(tfile.getPath());
                    }
                }
            }
        }catch(NullPointerException ne){
            Log.e("NULL POINTER EXCEPTION", "getting file path");
        }

        return null;
    }

    public static String readFile(String pPathName) {
        BufferedReader bReader;
        try {
            bReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pPathName))));
        } catch (IOException ioe) {
            Log.e("File Error", "Failed To Open File");
            return null;
        }
        String dataOfFile = "";
        String buf;
        try {
            //Add the content of the file
            while ((buf = bReader.readLine()) != null) {
                dataOfFile = dataOfFile.concat(buf + "\n");
            }
        } catch (IOException ioe) {
            Log.e("File Error", "Failed To Read From File");
            return null;
        }
        Log.i("fileData", dataOfFile);
        return dataOfFile;
    }

    public static void getMatchData(String scoutNumber, String matchNumber) {
        if(ApplicationInstance.getSp("isOverridden", 0) == 1){
            ApplicationInstance.setSp("isOverridden", 0);
            ApplicationInstance.setSp("assignmentMode", "override");
        }
        else{
            try {
                JSONObject backupData = new JSONObject(Objects.requireNonNull(retrieveFile("assignments.txt", "/")));

                backupData = backupData.getJSONObject(matchNumber).getJSONObject(scoutNumber);

                String allianceColor = backupData.getString("alliance");
                int teamNum = backupData.getInt("team");

                ApplicationInstance.setSp("alliance", allianceColor);
                ApplicationInstance.setSp("team", teamNum);
                ApplicationInstance.setSp("assignmentMode", "file");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
