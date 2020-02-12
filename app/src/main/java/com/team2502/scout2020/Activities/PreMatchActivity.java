package com.team2502.scout2020.Activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import com.team2502.scout2020.R;

public class PreMatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_match);
    }

    public void backButton(View view){
        Toast toast = Toast.makeText(getApplicationContext(), "Back Button Pressed", Toast.LENGTH_SHORT);
        toast.show();
    }
    public void frontButton(View view){
        Toast toast = Toast.makeText(getApplicationContext(), "Front Button Pressed", Toast.LENGTH_SHORT);
        toast.show();
    }

}
