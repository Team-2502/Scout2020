package com.team2502.scout2020.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team2502.scout2020.Dialogs.LevelHangDialog;
import com.team2502.scout2020.ExportUtils;
import com.team2502.scout2020.R;

public class ClimbActivity extends AppCompatActivity implements LevelHangDialog.LevelHangDialogListener{

    String timd_in_progress;
    String level;
    String side;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climb);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2020.timd");
    }

    public void hang(View view){
        Button button = (Button)view;
        side = button.getText().toString();

        DialogFragment levelHangFragment = new LevelHangDialog();
        levelHangFragment.show(getSupportFragmentManager(), "LevelHangDialog");
    }

    public void park(View view){
        Button button = (Button)view;
        String location = button.getText().toString();
        timd_in_progress += ExportUtils.createParkAction(timd_in_progress, location);
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("com.team2502.scout2020.timd", timd_in_progress);
        startActivity(intent);
    }

    public void finishHang(){
        timd_in_progress += ExportUtils.createHangAction(timd_in_progress, level, "Hanging", side);
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("com.team2502.scout2020.timd", timd_in_progress);
        startActivity(intent);
    }

    @Override
    public void onDialogLevelHangClick(DialogFragment dialog) {
        level = "True";
        finishHang();
    }

    @Override
    public void onDialogNotLevelHangClick(DialogFragment dialog) {
        level = "False";
        finishHang();
    }
}
