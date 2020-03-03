package com.team2502.scout2020.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team2502.scout2020.R;

public class IncapActivity extends AppCompatActivity {
    public String timd_in_progress;
    public String cause;

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
        cause = b.getText().toString();

        if(cause.equals("Cancel")){
            setResult(RESULT_CANCELED);
            finish();
        }

        Intent intent = new Intent(this, IncapWaitActivity.class);
        startActivityForResult(intent, 4);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 4 ---- Success?
        if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent();
                intent.putExtra("cause", cause);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
