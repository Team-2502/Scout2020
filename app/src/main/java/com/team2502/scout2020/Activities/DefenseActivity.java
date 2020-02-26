package com.team2502.scout2020.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.team2502.scout2020.R;

public class DefenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defense);
    }

    public void goBack(View view){
        setResult(RESULT_OK);
        finish();
    }
}
