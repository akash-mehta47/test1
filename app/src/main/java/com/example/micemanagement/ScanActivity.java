package com.example.micemanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.micemanagement.databinding.ActivityScanBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.Manifest;

public class ScanActivity extends AppCompatActivity {

    private ActivityScanBinding binding;
    private static final int CAMERA_PERMISSION_REQUEST = 101;
    private String username, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentData();
        setupUI();
        setupClickListeners();
        checkCameraPermission();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        role = intent.getStringExtra("role");
    }

    private void setupUI() {
        binding.toolbar.setTitle("Scan Mice");
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Set user info
        binding.tvUserInfo.setText("Scanning as: " + username + " (" + role + ")");
    }

    private void setupClickListeners() {
        binding.btnStartScan.setOnClickListener(v -> startQRScan());

        binding.btnManualEntry.setOnClickListener(v -> openManualEntry());

        binding.btnFlashlight.setOnClickListener(v -> toggleFlashlight());

        binding.fabQuickScan.setOnClickListener(v -> startQuickScan());
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        } else {
            setupScanner();
        }
    }

    private void setupScanner() {
        binding.scannerView.setVisibility(View.VISIBLE);
        binding.tvPermissionHint.setVisibility(View.GONE);
    }

    private void startQRScan() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setPrompt("Scan a Mice QR Code");
            integrator.setBeepEnabled(true);
            integrator.setOrientationLocked(true);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.setCameraId(0); // Use back camera
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();

        } else {
            Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show();
            checkCameraPermission();
        }
    }

    private void startQuickScan() {
        binding.fabQuickScan.animate()
                .scaleX(0.8f).scaleY(0.8f)
                .setDuration(100)
                .withEndAction(() -> binding.fabQuickScan.animate()
                        .scaleX(1.0f).scaleY(1.0f)
                        .setDuration(100).start())
                .start();

        startQRScan();
    }

    private void openManualEntry() {
        Intent intent = new Intent(this, ManualEntryActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("role", role);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
    }

    private void toggleFlashlight() {

        Toast.makeText(this, "Flashlight feature coming soon", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null) {
            String scannedData = result.getContents();
            handleScannedData(scannedData);
        } else {
            Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleScannedData(String scannedData) {

        binding.ivScanSuccess.setVisibility(View.VISIBLE);
        binding.ivScanSuccess.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));


        if (scannedData.startsWith("MICE_")) {
            String miceId = scannedData.replace("MICE_", "");
            showMiceDetails(miceId);
        } else {

            showMiceDetails(scannedData);
        }
    }

    private void showMiceDetails(String miceId) {
        Toast.makeText(this, "Scanned Mice ID: " + miceId, Toast.LENGTH_LONG).show();


        new android.os.Handler().postDelayed(() -> {
            Intent intent = new Intent(this, MiceDetailActivity.class);
            intent.putExtra("miceId", miceId);
            intent.putExtra("username", username);
            intent.putExtra("role", role);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }, 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupScanner();
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                binding.tvPermissionHint.setVisibility(View.VISIBLE);
            }
        }
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}