package com.team2502.scout2020.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team2502.scout2020.ExportUtils;
import com.team2502.scout2020.R;

public class IncapActivity extends AppCompatActivity {
    public String timd_in_progress;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incap);
        this.setFinishOnTouchOutside(false);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2020.timd");
    }

    public void buttonPress(View view){
        Button b = (Button)view;
        String cause = b.getText().toString();

        if(cause.equals("Cancel")){
            setResult(RESULT_CANCELED);
            finish();
        }
        timd_in_progress = ExportUtils.createIncapAction(timd_in_progress, cause);
        Intent data = new Intent();
        data.setData(Uri.parse(timd_in_progress));
        setResult(RESULT_OK, data);
        finish();
    }
}
