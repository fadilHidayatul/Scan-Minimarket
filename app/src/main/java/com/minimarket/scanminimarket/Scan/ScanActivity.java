package com.minimarket.scanminimarket.Scan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.minimarket.scanminimarket.Result.ResultActivity;
import com.minimarket.scanminimarket.databinding.ActivityScanBinding;

public class ScanActivity extends AppCompatActivity {
    private ActivityScanBinding binding;
    private CodeScanner codeScanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        codeScanner = new CodeScanner(this,binding.scanner);
        codeScanner.setDecodeCallback(result -> {
            runOnUiThread(() -> {
                String message = result.getText();
//                Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
                scanResult(message);
            });
        });

        codeScanner.setTouchFocusEnabled(true);
        codeScanner.setAutoFocusEnabled(true);

    }

    private void scanResult(String message) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("idbarang", message);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        codeScanner.releaseResources();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCameraPermission();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void checkCameraPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        codeScanner.startPreview();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }
}
