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

public class MatchActivity extends AppCompatActivity {

    String timd_in_progress;
    int alliance_color;

    long time_action_started = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2020.timd");
        String team = intent.getStringExtra("com.team2502.scout2020.team");
        String driver_station = intent.getStringExtra("com.team2502.scout2020.driver_station");
        String orientation = intent.getStringExtra("com.team2502.scout2020.orientation");

        if(driver_station.contains("Red")){
            if(orientation.equals("Right")){
                setContentView(R.layout.activity_match_field_right);
                ImageButton target_zone = findViewById(R.id.targetZoneButton);
                target_zone.setImageResource(R.drawable.redtargetzoneright);
            }
            else {
                setContentView(R.layout.activity_match_field_left);
                ImageButton target_zone = findViewById(R.id.targetZoneButton);
                target_zone.setImageResource(R.drawable.redtargetzoneleft);
            }
            alliance_color = Color.parseColor("#FF9AA2");
            TextView team_view = findViewById(R.id.teamNumber);
            team_view.setText(team);
            team_view.setBackgroundColor(alliance_color);
            Button trench = findViewById(R.id.trenchButton);
            trench.setBackgroundColor(alliance_color);
            ImageView init_line = findViewById(R.id.initLineImage);
            init_line.setBackgroundColor(alliance_color);

        }
        else{
            if(orientation.equals("Right")){
                setContentView(R.layout.activity_match_field_right);
                ImageButton target_zone = findViewById(R.id.targetZoneButton);
                target_zone.setImageResource(R.drawable.bluetargetzoneright);
            }
            else {
                setContentView(R.layout.activity_match_field_left);
                ImageButton target_zone = findViewById(R.id.targetZoneButton);
                target_zone.setImageResource(R.drawable.bluetargetzoneleft);
            }

            alliance_color = Color.parseColor("#6EB5FF");
            TextView team_view = findViewById(R.id.teamNumber);
            team_view.setText(team);
            team_view.setBackgroundColor(alliance_color);
            Button trench = findViewById(R.id.trenchButton);
            trench.setBackgroundColor(alliance_color);
            ImageView init_line = findViewById(R.id.initLineImage);
            init_line.setColorFilter(alliance_color);

        }

    }

    public void shoot(View view){
        Intent intent = new Intent(this, ShootActivity.class);
        intent.putExtra("com.team2502.scout2020.timd", timd_in_progress);
        intent.putExtra("com.team2502.scout2020.place", view.getContentDescription().toString());
        startActivityForResult(intent, 4);
    }

    public void incap(View view){
        time_action_started = System.currentTimeMillis();
        Intent intent = new Intent(this, IncapActivity.class);
        intent.putExtra("com.team2502.scout2020.timd", timd_in_progress);
        startActivityForResult(intent, 6);
    }

    public void defense(View view){
        time_action_started = System.currentTimeMillis();
        Intent intent = new Intent(this, DefenseActivity.class);
        startActivityForResult(intent, 7);
    }

    public void undo(View view){

    }

    public void wheel(View view){
        Intent intent = new Intent(this, WheelActivity.class);
        intent.putExtra("com.team2502.scout2020.timd", timd_in_progress);
        startActivityForResult(intent, 5);
    }

    public void climb(View view){
        Intent intent = new Intent(this, ClimbActivity.class);
        intent.putExtra("com.team2502.scout2020.timd", timd_in_progress);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 4 ---- SHOOT
        if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                timd_in_progress = data.getData().toString();
                Log.e("timdAction", timd_in_progress);
            }
            else if(resultCode == RESULT_CANCELED) {
                Log.e("timdAction", "Action Canceled");
            }
        }
        // 5 ---- WHEEL
        if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                timd_in_progress = data.getData().toString();
                Log.e("timdAction", timd_in_progress);
            }
            else if(resultCode == RESULT_CANCELED) {
                Log.e("timdAction", "Action Canceled");
            }
        }
        // 6 ---- INCAP
        if (requestCode == 6) {
            if (resultCode == RESULT_OK) {
                timd_in_progress = data.getData().toString();
                Log.e("timdAction", timd_in_progress);
            }
            else if(resultCode == RESULT_CANCELED) {
                Log.e("timdAction", "Action Canceled");
            }
        }
        // 7 ---- DEFENSE
        if (requestCode == 7) {
            if (resultCode == RESULT_OK) {
                long current_time = System.currentTimeMillis();
                int time = (int)(current_time - time_action_started) / 1000 ;
                timd_in_progress = ExportUtils.createDefenseAction(timd_in_progress, time);
            }
            else if(resultCode == RESULT_CANCELED) {
                Log.e("timdAction", "Action Canceled");
            }
        }

    }
}
