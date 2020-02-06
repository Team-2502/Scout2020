package com.team2502.scout2020.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.obsez.android.lib.filechooser.ChooserDialog;
import com.team2502.scout2020.ApplicationInstance;
import com.team2502.scout2020.Constants;
import com.team2502.scout2020.ExportUtils;
import com.team2502.scout2020.ImportUtils;
import com.team2502.scout2020.R;

import java.io.File;

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
        // TODO Causes fluffy failures if no file loaded uwu
        ImportUtils.getMatchData(Constants.SERIAL_TO_SCOUT.get(ApplicationInstance.getSp("scoutSerialNumber", "oof")), current_match_string);
        current_team_scouting = Integer.toString(ApplicationInstance.getSp("team", 0));
        current_driver_station = ApplicationInstance.getSp("alliance", "oof");
        current_assignment_mode = ApplicationInstance.getSp("assignmentMode", "oof");
        current_match_is_replay = ApplicationInstance.getSp("isReplay", "false");

        TextView team_to_scout_view = findViewById(R.id.teamToScout);
        team_to_scout_view.setText(current_team_scouting);

        TextView match_number = findViewById(R.id.matchNumber);
        match_number.setText(current_match_string);

        Spinner scout_spinner = findViewById(R.id.scout_initials);
        scout_spinner.setSelection(((ArrayAdapter<String>)scout_spinner.getAdapter()).getPosition(ApplicationInstance.getSp("currentScoutName", "Other")));

        TextView ds_view = findViewById(R.id.drivers_station);
        ds_view.setText(current_driver_station);
        if (current_driver_station.contains("Red")) {
            ds_view.setTextColor(Color.parseColor("#FF0000"));  // Red
            team_to_scout_view.setTextColor(Color.parseColor("#FF0000"));
        }
        else{
            ds_view.setTextColor(Color.parseColor("#0000FF")); // Blue
            team_to_scout_view.setTextColor(Color.parseColor("#0000FF"));
        }

    }

    public void startMatch(View view){
        Spinner scout_spinner = findViewById(R.id.scout_initials);
        String current_scout = scout_spinner.getSelectedItem().toString();

        String timd_in_progress = ExportUtils.createTIMDHeader(current_match_string, current_team_scouting, current_assignment_mode, current_driver_station, current_scout, current_match_is_replay);
        Intent intent = new Intent(this, PreMatchActivity.class);
        intent.putExtra("com.team2502.scout2020.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2020.team", current_team_scouting);
        intent.putExtra("com.team2502.scout2020.driver_station", current_driver_station);

        ApplicationInstance.setSp("currentScoutName", current_scout);

        startActivity(intent);
    }

    public void openOverrideActivity(View view){
        Intent intent = new Intent(this, OverrideActivity.class);
        startActivity(intent);
    }

    public void openFilePickerDialog(View view){
        // https://github.com/hedzr/android-file-chooser
        new ChooserDialog(HomeScreenActivity.this)
                .withStartFile(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/Scouting/rawTIMDs")
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        String timd = ImportUtils.readFile(path);
                        Log.e("timd", timd);
                        Intent intent = new Intent(HomeScreenActivity.this, QRDisplayActivity.class);
                        intent.putExtra("com.team2502.scout2020.timd", timd);
                        intent.putExtra("com.team2502.scout2020.rescan", true);
                        startActivity(intent);
                    }
                })
                // to handle the back key pressed or clicked outside the dialog:
                .withOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        dialog.cancel();
                    }
                })
                .build()
                .show();
    }

}
