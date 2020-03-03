package com.team2502.scout2020.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.team2502.scout2020.ExportUtils;
import com.team2502.scout2020.R;

public class ShootActivity extends AppCompatActivity {

    public String timd_in_progress;
    public String place;
    public String time;

    public int miss = 0;
    public int lower = 0;
    public int outer = 0;
    public int inner = 0;
    public int total = 0;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoot);
        this.setFinishOnTouchOutside(false);

        Intent intent = getIntent();
        time = intent.getStringExtra("com.team2502.scout2020.time");
        timd_in_progress = intent.getStringExtra("com.team2502.scout2020.timd");
        place = intent.getStringExtra("com.team2502.scout2020.place");
    }

    public void miss(View view){
        if(total == 5){
            return;
        }
        miss += 1;
        total += 1;
        TextView misses = findViewById(R.id.missesCount);
        misses.setText(Integer.toString(miss));
    }

    public void cancelMiss(View view){
        if(miss == 0){
            return;
        }
        miss -= 1;
        total -= 1;
        TextView misses = findViewById(R.id.missesCount);
        misses.setText(Integer.toString(miss));
    }

    public void lower(View view){
        if(total == 5){
            return;
        }
        lower += 1;
        total += 1;
        TextView misses = findViewById(R.id.lowerPortCount);
        misses.setText(Integer.toString(lower));
    }

    public void cancelLower(View view){
        if(lower == 0){
            return;
        }
        lower -= 1;
        total -= 1;
        TextView misses = findViewById(R.id.lowerPortCount);
        misses.setText(Integer.toString(lower));
    }

    public void outer(View view){
        if(total == 5){
            return;
        }
        outer += 1;
        total += 1;
        TextView misses = findViewById(R.id.outerPortCount);
        misses.setText(Integer.toString(outer));
    }

    public void cancelOuter(View view){
        if(outer == 0){
            return;
        }
        outer -= 1;
        total -= 1;
        TextView misses = findViewById(R.id.outerPortCount);
        misses.setText(Integer.toString(outer));
    }
    public void inner(View view){
        if(total == 5){
            return;
        }
        inner += 1;
        total += 1;
        TextView misses = findViewById(R.id.innerPortCount);
        misses.setText(Integer.toString(inner));
    }

    public void cancelInner(View view){
        if(inner == 0){
            return;
        }
        inner -= 1;
        total -= 1;
        TextView misses = findViewById(R.id.innerPortCount);
        misses.setText(Integer.toString(inner));
    }

    public void cancel(View view){
        setResult(RESULT_CANCELED);
        finish();
    }

    public void ok(View view){
        if(total == 0){
            setResult(RESULT_CANCELED);
            finish();
        }
        timd_in_progress = ExportUtils.createShootAction(timd_in_progress, place, miss, lower, outer, inner, inner+outer+lower, time);
        Intent data = new Intent();
        data.setData(Uri.parse(timd_in_progress));
        setResult(RESULT_OK, data);
        finish();
    }
}
