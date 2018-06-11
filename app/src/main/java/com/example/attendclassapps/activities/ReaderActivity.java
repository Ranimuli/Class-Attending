package com.example.attendclassapps.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendclassapps.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ReaderActivity extends Activity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "Log_Attend_Class";
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        int currentApiVersion = Build.VERSION.SDK_INT;
        if(currentApiVersion >=  Build.VERSION_CODES.LOLLIPOP)
        {
            if(checkPermission())
            {
                mScannerView = new ZXingScannerView(this);
                setContentView(mScannerView);
            }
            else
            {
                requestPermission();
            }
        }
        Log.i(TAG, "ReaderActivity_onCreate");
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera(); // Stop camera on pause
        Log.i(TAG, "ReaderActivity_onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();

        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.KITKAT) {
            if (checkPermission()) {
                if (mScannerView == null) {
                    mScannerView = new ZXingScannerView(this);
                    setContentView(mScannerView);
                }
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            } else {
                requestPermission();
            }
        }
        //mScannerView.resumeCameraPreview(this);
        Log.i(TAG, "ReaderActivity_onResume");
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        Dialog resultDialog = new Dialog(ReaderActivity.this);
        resultDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        resultDialog.setCanceledOnTouchOutside(false);
        resultDialog.setContentView(R.layout.result_dialog);
        TextView showResult = resultDialog.findViewById(R.id.showResult);

        final String myResult = rawResult.getText();
        Log.e("getQRCode", rawResult.getText());
        Log.e("getQRCode", rawResult.getBarcodeFormat().toString());
        showResult.setText(myResult);

        Button button_checked = resultDialog.findViewById(R.id.button_checked);
        button_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SQLite query
                startActivity(new Intent(getApplicationContext() , StudentSendDataToFirebaseActivity.class));
                Toast.makeText(getApplicationContext(), "Checked !", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        resultDialog.show();
        Log.i(TAG, "ReaderActivity_LoginDialog");
    }
        /*Log.i("1121", rawResult.getText()); // Prints scan results
        Log.i("1121", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)*/
        // show the scanner result into dialog box.
}

