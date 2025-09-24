package com.example.micemanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.micemanagement.databinding.ActivityLoginBinding;
import com.google.android.material.chip.Chip;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private boolean isFacultySelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupViews();
        setupAnimations();
        setupListeners();
        setupTextWatchers();
    }

    private void setupViews() {

        binding.chipFaculty.setChecked(true);
        updateChipAppearance(binding.chipFaculty, true);
        updateChipAppearance(binding.chipTnp, false);
    }

    private void setupAnimations() {

        new Handler().postDelayed(() -> {
            if (binding.loginCard != null) {
                binding.loginCard.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
            }
        }, 300);
    }

    private void setupTextWatchers() {
        binding.etUsername.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateForm();
            }
        });

        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateForm();
            }
        });
    }

    private void setupListeners() {

        binding.btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });


        binding.chipFaculty.setOnClickListener(v -> {
            isFacultySelected = true;
            updateChipAppearance(binding.chipFaculty, true);
            updateChipAppearance(binding.chipTnp, false);
        });

        binding.chipTnp.setOnClickListener(v -> {
            isFacultySelected = false;
            updateChipAppearance(binding.chipTnp, true);
            updateChipAppearance(binding.chipFaculty, false);
        });


        binding.btnLogin.setOnClickListener(v -> performLogin());


        binding.tvForgotPassword.setOnClickListener(v -> showForgotPasswordDialog());
    }

    private void updateChipAppearance(Chip chip, boolean selected) {
        if (selected) {
            chip.setChipBackgroundColorResource(R.color.primary);
            chip.setTextColor(Color.WHITE);
        } else {
            chip.setChipBackgroundColorResource(R.color.primary_light);
            chip.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
        }
    }

    private void validateForm() {
        String username = binding.etUsername.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        boolean isValid = !username.isEmpty() && !password.isEmpty();

        binding.btnLogin.setEnabled(isValid);
        binding.btnLogin.setAlpha(isValid ? 1.0f : 0.6f);
    }

    private void performLogin() {
        String username = binding.etUsername.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showShakeAnimation();
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        showLoading(true);


        new Handler().postDelayed(() -> {
            if (authenticateUser(username, password)) {
                showSuccessAnimation();
                navigateToDashboard(username);
            } else {
                showLoading(false);
                showErrorAnimation();
            }
        }, 2000);
    }

    private boolean authenticateUser(String username, String password) {
        return (username.equals("faculty") && password.equals("123")) ||
                (username.equals("tnp") && password.equals("123"));
    }

    private void showLoading(boolean show) {
        if (show) {
            binding.btnLogin.setText("Logging in...");
            binding.btnLogin.setEnabled(false);
        } else {
            binding.btnLogin.setText("Login");
            binding.btnLogin.setEnabled(true);
        }
    }

    private void showShakeAnimation() {
        binding.etUsername.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        binding.etPassword.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }

    private void showSuccessAnimation() {
        Toast.makeText(this, "Login successful! 🎉", Toast.LENGTH_SHORT).show();
    }

    private void showErrorAnimation() {
        binding.etPassword.setText("");
        binding.etPassword.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        Toast.makeText(this, "Invalid credentials! ❌", Toast.LENGTH_SHORT).show();
    }

    private void navigateToDashboard(String username) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("role", isFacultySelected ? "Faculty" : "TNP Staff");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    private void showForgotPasswordDialog() {
        Toast.makeText(this, "Password reset feature coming soon! 🔒", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}