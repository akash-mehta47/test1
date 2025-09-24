package com.example.micemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.micemanagement.adapter.RecentActivityAdapter;
import com.example.micemanagement.databinding.ActivityDashboardBinding;
import com.example.micemanagement.model.Mice;
import com.example.micemanagement.model.RecentActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private String username, role;
    private Map<String, Mice> miceMap;
    private List<RecentActivity> recentActivities;
    private RecentActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentData();
        setupUI();
        setupRecyclerView();
        loadDashboardData();
        setupClickListeners();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        role = intent.getStringExtra("role");

        binding.tvWelcome.setText("Welcome, " + (username != null ? username : "User") + "!");
        binding.tvRole.setText("Role: " + (role != null ? role : "Faculty"));
    }

    private void setupUI() {
        binding.toolbar.setTitle("Mice Management");
    }

    private void setupRecyclerView() {
        recentActivities = new ArrayList<>();
        adapter = new RecentActivityAdapter(recentActivities);

        binding.rvRecentActivity.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRecentActivity.setAdapter(adapter);
    }

    private void loadDashboardData() {
        // In a real app, you would fetch this data from a database or API
        miceMap = createSampleMiceData();
        recentActivities = createSampleActivities();

        updateStats();
        updateRecentActivities();
    }

    private Map<String, Mice> createSampleMiceData() {
        Map<String, Mice> map = new HashMap<>();
        Mice mouse1 = new Mice("M001", "Logitech", "MX Master 3", "Available");
        Mice mouse2 = new Mice("M002", "Dell", "Wireless Mouse", "Issued");
        Mice mouse3 = new Mice("M003", "HP", "X3000", "Maintenance");
        Mice mouse4 = new Mice("M004", "Microsoft", "Sculpt Comfort", "Available");

        map.put(mouse1.getMiceId(), mouse1);
        map.put(mouse2.getMiceId(), mouse2);
        map.put(mouse3.getMiceId(), mouse3);
        map.put(mouse4.getMiceId(), mouse4);
        return map;
    }

    private List<RecentActivity> createSampleActivities() {
        List<RecentActivity> list = new ArrayList<>();
        Mice mouse1 = miceMap.get("M001");
        Mice mouse2 = miceMap.get("M002");

        if (mouse1 != null) {
            list.add(new RecentActivity(
                    mouse1.getMiceId(),
                    "Mouse Issued - " + mouse1.getTitle(),
                    mouse1.getTitle() + " issued to Dr. Sharma (CS Dept)",
                    "10:30 AM",
                    "#10B981",
                    R.drawable.ic_mice
            ));
        }

        if (mouse2 != null) {
            list.add(new RecentActivity(
                    mouse2.getMiceId(),
                    "Mouse Returned - " + mouse2.getTitle(),
                    mouse2.getTitle() + " returned by Prof. Gupta",
                    "09:15 AM",
                    "#F59E0B",
                    R.drawable.ic_mice
            ));
        }

        return list;
    }

    private void updateStats() {
        if (miceMap == null) return;

        int availableCount = 0;
        int issuedCount = 0;

        for (Mice mouse : miceMap.values()) {
            if (mouse.isAvailable()) {
                availableCount++;
            } else if (mouse.isIssued()) {
                issuedCount++;
            }
        }

        binding.tvAvailableCount.setText(String.valueOf(availableCount));
        binding.tvIssuedCount.setText(String.valueOf(issuedCount));
    }

    private void updateRecentActivities() {
        if (adapter != null) {
            adapter.updateData(recentActivities);
            binding.rvRecentActivity.setVisibility(recentActivities.isEmpty() ? View.GONE : View.VISIBLE);
        }
    }

    private void setupClickListeners() {
        binding.cardScan.setOnClickListener(v -> startScanActivity());
        binding.fabScan.setOnClickListener(v -> showQuickActionsMenu());
    }

    private void startScanActivity() {
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("role", role);
        startActivity(intent);
    }

    private void showQuickActionsMenu() {
        // You can implement a more sophisticated menu here
        Toast.makeText(this, "Quick Actions Menu", Toast.LENGTH_SHORT).show();
    }

    private void displayMouseDetails(String mouseId) {
        Mice mouse = miceMap.get(mouseId);
        if (mouse != null) {
            String details = "ID: " + mouse.getMiceId() + "\n" +
                    "Title: " + mouse.getTitle() + "\n" +
                    "Status: " + mouse.getStatus();
            Toast.makeText(this, details, Toast.LENGTH_LONG).show();
        }
    }

    private void refreshDashboard() {
        Toast.makeText(this, "Refreshing Dashboard", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(this::loadDashboardData, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDashboard();
    }
}