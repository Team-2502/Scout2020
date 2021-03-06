package com.team2502.scout2020.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.obsez.android.lib.filechooser.ChooserDialog;
import com.team2502.scout2020.ApplicationInstance;
import com.team2502.scout2020.Constants;
import com.team2502.scout2020.ExportUtils;
import com.team2502.scout2020.ImportUtils;
import com.team2502.scout2020.R;

public class HomeScreenActivity extends AppCompatActivity {

    public static String current_match_string;
    public static String current_team_scouting;
    public static String current_driver_station;
    public static String current_assignment_mode;
    public static String current_match_is_replay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        int this_match_number = (ApplicationInstance.getSp("lastMatch", 0)) + 1;
        current_match_string = "QM " + this_match_number;
        if(ImportUtils.retrieveFile("assignments.txt", "/") == null && ApplicationInstance.getSp("isOverridden", 0) != 1){
            Toast toast = Toast.makeText(getApplicationContext(), "No Assignment File Downloaded!", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, OverrideActivity.class);
            startActivity(intent);
        }
        else {
            ImportUtils.getMatchData(Constants.SERIAL_TO_SCOUT.get(ApplicationInstance.getSp("scoutSerialNumber", "oof")), current_match_string);
            current_team_scouting = Integer.toString(ApplicationInstance.getSp("team", 2502));
            current_driver_station = ApplicationInstance.getSp("alliance", "Blue 1");
            current_assignment_mode = ApplicationInstance.getSp("assignmentMode", "file");
            current_match_is_replay = ApplicationInstance.getSp("isReplay", "false");


            TextView team_to_scout_view = findViewById(R.id.teamToScout);
            team_to_scout_view.setText(current_team_scouting);

            TextView match_number = findViewById(R.id.matchNumber);
            match_number.setText(current_match_string);

            Spinner scout_spinner = findViewById(R.id.scout_initials);
            scout_spinner.setSelection(((ArrayAdapter<String>) scout_spinner.getAdapter()).getPosition(ApplicationInstance.getSp("currentScoutName", "Other")));

            TextView ds_view = findViewById(R.id.drivers_station);
            ImageView background = findViewById(R.id.homeBackground);
            Button override = findViewById(R.id.override_button);
            Button startMatch = findViewById(R.id.startMatchButton);
            Button rescan = findViewById(R.id.rescan);


            ds_view.setText(current_driver_station);
            String red = "#FF9AA2";
            String blue = "#6EB5FF";
            if (current_driver_station.contains("Red")) {
                ds_view.setTextColor(Color.parseColor(red));
                team_to_scout_view.setTextColor(Color.parseColor(red));
                background.setBackgroundColor(Color.parseColor(red));
                override.setBackgroundColor(Color.parseColor(red));
                startMatch.setBackgroundColor(Color.parseColor(red));
                rescan.setBackgroundColor(Color.parseColor(red));
                match_number.setTextColor(Color.parseColor(red));
            } else {
                ds_view.setTextColor(Color.parseColor(blue));
                team_to_scout_view.setTextColor(Color.parseColor(blue));
                background.setBackgroundColor(Color.parseColor(blue));
                override.setBackgroundColor(Color.parseColor(blue));
                startMatch.setBackgroundColor(Color.parseColor(blue));
                rescan.setBackgroundColor(Color.parseColor(blue));
                match_number.setTextColor(Color.parseColor(blue));
            }

            Switch orientationSwitch = findViewById(R.id.orientation);
            TextView orientationText = findViewById(R.id.orientationText);

            String pastOrientation = ApplicationInstance.getSp("fieldOrientation", "Left");

            if (pastOrientation.equals("Left")) {
                orientationSwitch.setChecked(false);
                orientationText.setText("Left");
            } else {
                orientationSwitch.setChecked(true);
                orientationText.setText("Right");
            }

            orientationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    orientationText.setText("Right");
                } else {
                    orientationText.setText("Left");
                }
            });
        }
    }

    public void startMatch(View view){
        Spinner scout_spinner = findViewById(R.id.scout_initials);
        String current_scout = scout_spinner.getSelectedItem().toString();
        TextView orientationText = findViewById(R.id.orientationText);
        String orientation = (String) orientationText.getText();

        String timd_in_progress = ExportUtils.createTIMDHeader(current_match_string, current_team_scouting, current_assignment_mode, current_driver_station, current_scout, current_match_is_replay);
        Intent intent = new Intent(this, AutoActivity.class);
        intent.putExtra("com.team2502.scout2020.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2020.team", current_team_scouting);
        intent.putExtra("com.team2502.scout2020.driver_station", current_driver_station);
        intent.putExtra("com.team2502.scout2020.orientation", orientation);

        ApplicationInstance.setSp("currentScoutName", current_scout);
        ApplicationInstance.setSp("fieldOrientation", orientation);

        startActivity(intent);
    }

    public void openOverrideActivity(View view){
        Intent intent = new Intent(this, OverrideActivity.class);
        startActivity(intent);
    }

    public void openFilePickerDialog(View view){
        // https://github.com/hedzr/android-file-chooser
        new ChooserDialog(HomeScreenActivity.this)
                .withStartFile(Constants.SCOUTING_DIR + "/rawTIMDs")
                .withChosenListener((path, pathFile) -> {
                    String timd = ImportUtils.readFile(path);
                    Log.e("timd", timd);
                    Intent intent = new Intent(HomeScreenActivity.this, QRDisplayActivity.class);
                    intent.putExtra("com.team2502.scout2020.timd", timd);
                    intent.putExtra("com.team2502.scout2020.rescan", true);
                    startActivity(intent);
                })
                // to handle the back key pressed or clicked outside the dialog:
                .withOnCancelListener(DialogInterface::cancel)
                .build()
                .show();
    }

}
