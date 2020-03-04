package com.team2502.scout2020.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.team2502.scout2020.ExportUtils;
import com.team2502.scout2020.R;

public class AutoActivity extends AppCompatActivity {
    String timd_in_progress;
    String team;
    String driver_station;
    String orientation;
    int alliance_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_field_left);
        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2020.timd");

        team = intent.getStringExtra("com.team2502.scout2020.team");
        driver_station = intent.getStringExtra("com.team2502.scout2020.driver_station");
        orientation = intent.getStringExtra("com.team2502.scout2020.orientation");

        if(driver_station.contains("Red")){
            if(orientation.equals("Right")){
                setContentView(R.layout.activity_auto_field_right);
                ImageButton target_zone = findViewById(R.id.targetZoneButton);
                target_zone.setImageResource(R.drawable.redtargetzoneright);
            }
            else {
                setContentView(R.layout.activity_auto_field_left);
                ImageButton target_zone = findViewById(R.id.targetZoneButton);
                target_zone.setImageResource(R.drawable.redtargetzoneleft);
            }
            alliance_color = Color.parseColor("#FF9AA2");
            TextView team_view = findViewById(R.id.teamNumber);
            team_view.setText(team);
            team_view.setBackgroundColor(alliance_color);
            Button trench = findViewById(R.id.trenchButton);
            trench.setBackgroundColor(alliance_color);
            Button init = findViewById(R.id.initLineButton);
            init.setBackgroundColor(alliance_color);
            ImageView init_line = findViewById(R.id.initLineImage);
            init_line.setBackgroundColor(alliance_color);

        }
        else{
            if(orientation.equals("Right")){
                setContentView(R.layout.activity_auto_field_right);
                ImageButton target_zone = findViewById(R.id.targetZoneButton);
                target_zone.setImageResource(R.drawable.bluetargetzoneright);
            }
            else {
                setContentView(R.layout.activity_auto_field_left);
                ImageButton target_zone = findViewById(R.id.targetZoneButton);
                target_zone.setImageResource(R.drawable.bluetargetzoneleft);
            }

            alliance_color = Color.parseColor("#6EB5FF");
            TextView team_view = findViewById(R.id.teamNumber);
            team_view.setText(team);
            team_view.setBackgroundColor(alliance_color);
            Button trench = findViewById(R.id.trenchButton);
            trench.setBackgroundColor(alliance_color);
            Button init = findViewById(R.id.initLineButton);
            init.setBackgroundColor(alliance_color);
            ImageView init_line = findViewById(R.id.initLineImage);
            init_line.setColorFilter(alliance_color);

        }

        findViewById(R.id.undoButton).setEnabled(false);

    }

    public void goToTeleop(View view){
        if(timd_in_progress.indexOf('|') == -1){
            timd_in_progress += "UfWf|";
        }
        Intent intent = new Intent(this, MatchActivity.class);
        intent.putExtra("com.team2502.scout2020.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2020.team", team);
        intent.putExtra("com.team2502.scout2020.driver_station", driver_station);
        intent.putExtra("com.team2502.scout2020.orientation", orientation);
        startActivity(intent);
    }

    public void shoot(View view){
        if(timd_in_progress.indexOf('|') == -1){
            timd_in_progress += "UfWt|";
        }
        Log.e("timdAutoShoot", timd_in_progress);
        Intent intent = new Intent(this, ShootActivity.class);
        intent.putExtra("com.team2502.scout2020.time", "auto");
        intent.putExtra("com.team2502.scout2020.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2020.place", view.getContentDescription().toString());
        startActivityForResult(intent, 4);
    }

    public void undo(View view){
        //Only one action
        if(timd_in_progress.substring(0, timd_in_progress.length() - 1).lastIndexOf(",") == -1){
            timd_in_progress = timd_in_progress.substring(0, timd_in_progress.lastIndexOf("|") + 1);
            Log.e("timdUndo", timd_in_progress);
        }
        //Multiple actions
        else{
            timd_in_progress = timd_in_progress.substring(0, timd_in_progress.length() - 1);
            timd_in_progress = timd_in_progress.substring(0, timd_in_progress.lastIndexOf(",") + 1);
            Log.e("timdUndo", timd_in_progress);
        }

        findViewById(R.id.undoButton).setEnabled(false);
    }

    public void noShow(View view) {
        if(timd_in_progress.indexOf('|') == -1){
            timd_in_progress += "Ut|";
        }
        else{
            timd_in_progress = timd_in_progress.substring(0, timd_in_progress.lastIndexOf("F") + 2) +  "Ut|";
        }

        Intent intent = new Intent(this, QRDisplayActivity.class);
        intent.putExtra("com.team2502.scout2020.timd", timd_in_progress);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        findViewById(R.id.undoButton).setEnabled(true);
        // 4 ---- SHOOT
        if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                timd_in_progress = data.getData().toString();
                Log.e("timdShoot", timd_in_progress);
            } else if (resultCode == RESULT_CANCELED) {
                Log.e("timdShoot", "Action Canceled");
            }
        }
    }
}
