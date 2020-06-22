package com.team2502.scout2020.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import com.team2502.scout2020.ApplicationInstance;
import com.team2502.scout2020.R;
import static com.team2502.scout2020.ImportUtils.writeFileToStorage;

import java.util.HashMap;
import java.util.Map;

// Mostly taken from https://github.com/frc1678/scout-2019
// TODO Potentially move to https://github.com/SumiMakito/AwesomeQRCode if needed
public class QRDisplayActivity extends AppCompatActivity {

    ImageView qrView;
    String timd_in_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrdisplay);

        qrView = findViewById(R.id.QRCode_Display);

        Intent intent = getIntent();
        timd_in_progress = intent.getStringExtra("com.team2502.scout2020.timd");
        boolean rescan = intent.getBooleanExtra("com.team2502.scout2020.rescan", false);

        displayQRCode(timd_in_progress);

        // If the scout is not rescanning a TIMD, update the current match
        if(!rescan){
            writeFileToStorage(getTIMDName(timd_in_progress), "/rawTIMDs", timd_in_progress);

            int new_last_match = (ApplicationInstance.getSp("lastMatch", 0)) + 1;
            ApplicationInstance.setSp("lastMatch", new_last_match);
        }
    }

    // Display a QR Code encoded with the given string
    public void displayQRCode(String qrCode) {
        try {
            // Determine QR Code size based on the size of the tablet window
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallestDimension = width < height ? width : height;

            // Set QR Code parameters
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            createQRCode(qrCode, charset, hintMap, smallestDimension, smallestDimension);
        }
        catch (Exception e) {
            Log.e("QR Code", e.toString());
        }
    }

    // Creates a QR Code from dimensions and data
    public void createQRCode(String qrCodeData, String charset, Map hintMap, int qrCodeHeight, int qrCodeWidth) {

        try {
            // Generate QR Code as BitMatrix
            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset), BarcodeFormat.QR_CODE, qrCodeWidth, qrCodeHeight, hintMap);

            // Convert BitMatrix to bitmap
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];

            // All pixels are 0, or black, by default
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = matrix.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            // Set ImageView to the bitmap
            qrView.setImageBitmap(null);
            qrView.setImageBitmap(bitmap);
        }
        catch (Exception e) {
            Log.e("QR Code", e.toString());
        }
    }

    // Takes user back to HomeScreenActivity
    public void onOKClick(View view) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }

    // If the tablet is connected to wifi, upload the raw TIMD to Firebase and return to HomeScreen
    public void onWifiClick(View view){
        if(isWifiAvailable()){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            myRef.child("rawTIMDs").child(getTIMDName(timd_in_progress)).setValue(timd_in_progress);

            Toast toast = Toast.makeText(getApplicationContext(), "Upload Successful", Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(this, HomeScreenActivity.class);
            startActivity(intent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Not Connected to Wifi!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // Checks if the tablet is connected to the internet
    private boolean isWifiAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Returns the name of the TIMD based on the rawTIMD
    public String getTIMDName(String scannedTIMD){
        String TIMDName = "QM" + scannedTIMD.substring(1, scannedTIMD.indexOf('B')) + "-";
        TIMDName += scannedTIMD.substring(scannedTIMD.indexOf('B') + 1, scannedTIMD.indexOf('C')) + "-";
        TIMDName += scannedTIMD.substring(scannedTIMD.indexOf('D') + 1, scannedTIMD.indexOf('E'));
        return TIMDName;
    }
}
