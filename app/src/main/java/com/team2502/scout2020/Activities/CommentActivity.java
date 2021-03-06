package com.team2502.scout2020.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.team2502.scout2020.R;

public class CommentActivity extends AppCompatActivity {

    String timd_in_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2020.timd");
        timd_in_progress = timd_in_progress.substring(0, timd_in_progress.length()-1);
    }

    public void submit(View view){
        EditText textBox = findViewById(R.id.editText);
        timd_in_progress += "|" + textBox.getText().toString().replaceAll("/n", "");
        Log.e("timdComment", timd_in_progress);

        Intent intent = new Intent(this, QRDisplayActivity.class);
        intent.putExtra("com.team2502.scout2020.timd", timd_in_progress);
        startActivity(intent);
    }
}
