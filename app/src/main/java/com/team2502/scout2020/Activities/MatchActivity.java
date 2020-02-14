package com.team2502.scout2020.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.team2502.scout2020.R;

public class MatchActivity extends AppCompatActivity {

    String timd_in_progress;
    int alliance_color;


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

    }

    public void defense(View view){

    }

    public void undo(View view){

    }

    public void wheel(View view){

    }

    public void climb(View view){

    }
}
