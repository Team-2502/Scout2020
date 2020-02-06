package com.team2502.scout2020.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.team2502.scout2020.ApplicationInstance;
import com.team2502.scout2020.R;

public class OverrideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_override);
    }

    public void onOKClick(View view) {
        EditText match_num = findViewById(R.id.match_num);
        EditText team_num = findViewById(R.id.team_num);

        try{
            int this_match = Integer.parseInt(match_num.getText().toString());
            ApplicationInstance.setSp("lastMatch", this_match -1);
            int this_team = Integer.parseInt(team_num.getText().toString());
            ApplicationInstance.setSp("team", this_team);
        }
        catch(NumberFormatException e){
            Toast toast = Toast.makeText(getApplicationContext(), "Fill out all the fields!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Switch replay_view = findViewById(R.id.replay);
        Boolean is_replay = replay_view.isChecked();
        ApplicationInstance.setSp("isReplay", "true");

        Spinner drivers_station = findViewById(R.id.drivers_station);
        ApplicationInstance.setSp("alliance", drivers_station.getSelectedItem().toString());

        ApplicationInstance.setSp("isOverridden", 1);

        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    public void onUpdateMatchNumber(View view){
        EditText match_num = findViewById(R.id.match_num);
        try{
            int this_match = Integer.parseInt(match_num.getText().toString());
            ApplicationInstance.setSp("lastMatch", this_match -1);
        }catch(NumberFormatException e){
            Toast toast = Toast.makeText(getApplicationContext(), "Fill out all the fields!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
}
