package com.team2502.scout2020.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.team2502.scout2020.ApplicationInstance;
import com.team2502.scout2020.R;

import static com.team2502.scout2020.ImportUtils.writeFileToStorage;

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
        if(replay_view.isChecked()){
            ApplicationInstance.setSp("isReplay", "true");
        }
        else{
            ApplicationInstance.setSp("isReplay", "false");
        }


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

    public void onGetAssignments(View view){
        if(isNetworkAvailable()){
            FirebaseDatabase firebase = FirebaseDatabase.getInstance();
            DatabaseReference assignments = firebase.getReference().child("config").child("scoutAssignments");

            assignments.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Gson gson = new Gson();
                    String assignmentsJSON = gson.toJson(dataSnapshot.getValue());
                    writeFileToStorage("assignments.txt", "/", assignmentsJSON);

                    Toast toast = Toast.makeText(getApplicationContext(), "Assignments Downloading", Toast.LENGTH_LONG);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(() -> goBackToHome(), 3500);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Assignments Failed to Download!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Not Connected to Wifi!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void goBackToHome(){
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
}
