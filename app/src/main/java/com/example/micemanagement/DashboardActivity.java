package com.example.micemanagement;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.micemanagement.databinding.ActivityDashboardBinding;
import com.example.micemanagement.model.Mice;
import com.example.micemanagement.model.RecentActivity;
import com.example.micemanagement.adapter.RecentActivityAdapter;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private String username, role;
    private List<Mice> miceList;
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
        binding.toolbar.setTitle("Dashboard - " + getDashboardTitle());
    }

    private String getDashboardTitle() {
        return "Mice Management System";
    }

    private void setupRecyclerView() {
        recentActivities = new ArrayList<>();
        adapter = new RecentActivityAdapter(recentActivities);

        binding.rvRecentActivity.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRecentActivity.setAdapter(adapter);
    }

    private void loadDashboardData() {

        miceList = createSampleMiceData();
        recentActivities = createSampleActivities();

        updateStats();
        updateRecentActivities();
    }

    private List<Mice> createSampleMiceData() {
        List<Mice> list = new ArrayList<>();


        Mice mouse1 = new Mice("M001", "Logitech", "MX Master 3", "Available");
        Mice mouse2 = new Mice("M002", "Dell", "Wireless Mouse", "Issued");
        Mice mouse3 = new Mice("M003", "HP", "X3000", "Maintenance");
        Mice mouse4 = new Mice("M004", "Microsoft", "Sculpt Comfort", "Available");

        list.add(mouse1);
        list.add(mouse2);
        list.add(mouse3);
        list.add(mouse4);

        return list;
    }

    private List<RecentActivity> createSampleActivities() {
        List<RecentActivity> list = new ArrayList<>();


        Mice mouse1 = new Mice("M001", "Logitech", "MX Master 3", "Available");
        Mice mouse2 = new Mice("M002", "Dell", "Wireless Mouse", "Issued");

        list.add(new RecentActivity(
                mouse1.getMiceId(),
                "Mouse Issued - " + mouse1.getTitle(),
                mouse1.getTitle() + " issued to Dr. Sharma (CS Dept)",
                "10:30 AM",
                "#10B981",
                R.drawable.ic_mice
        ));

        list.add(new RecentActivity(
                mouse2.getMiceId(),
                "Mouse Returned - " + mouse2.getTitle(),
                mouse2.getTitle() + " returned by Prof. Gupta",
                "09:15 AM",
                "#F59E0B",
                R.drawable.ic_mice
        ));

        return list;
    }

    private void updateStats() {
        if (miceList == null) return;

        int availableCount = 0;
        int issuedCount = 0;
        int maintenanceCount = 0;


        for (Mice mouse : miceList) {
            System.out.println("Processing Mouse: " + mouse.getMouseId() + " - " + mouse.getTitle());

            switch (mouse.getStatus().toLowerCase()) {
                case "available":
                    availableCount++;
                    break;
                case "issued":
                    issuedCount++;
                    break;
                case "maintenance":
                    maintenanceCount++;
                    break;
            }
        }


        binding.tvAvailableCount.setText(String.valueOf(availableCount));
        binding.tvIssuedCount.setText(String.valueOf(issuedCount));


        binding.tvStatsTitle.setText("Mice Statistics (" + miceList.size() + " total)");
    }

    private void updateRecentActivities() {
        if (adapter != null) {
            adapter.updateData(recentActivities);

            // Show message if no activities
            if (recentActivities.isEmpty()) {
                binding.tvNoActivities.setVisibility(View.VISIBLE);
                binding.rvRecentActivity.setVisibility(View.GONE);
            } else {
                binding.tvNoActivities.setVisibility(View.GONE);
                binding.rvRecentActivity.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setupClickListeners() {
        // Scan button - getTitle() usage in toast
        binding.cardScan.setOnClickListener(v -> {
            String scanTitle = "Scan New Mouse";
            Toast.makeText(this, "Starting: " + scanTitle, Toast.LENGTH_SHORT).show();
            startScanActivity();
        });

        // Quick actions using getMouseId()
        binding.fabQuickAction.setOnClickListener(v -> {
            showQuickActionsMenu();
        });
    }

    private void startScanActivity() {
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("role", role);
        startActivity(intent);
    }

    private void showQuickActionsMenu() {
        // Demonstrate getMouseId() and getTitle() in quick actions
        if (miceList != null && !miceList.isEmpty()) {
            Mice recentMouse = miceList.get(0);
            String message = "Quick action for: " + recentMouse.getTitle() + " (" + recentMouse.getMouseId() + ")";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }


    private Mice getMouseById(String mouseId) {
        if (miceList == null) return null;

        for (Mice mouse : miceList) {
            if (mouse.getMouseId().equals(mouseId)) {
                return mouse;
            }
        }
        return null;
    }


    private List<Mice> searchMiceByTitle(String searchQuery) {
        List<Mice> results = new ArrayList<>();

        if (miceList != null) {
            for (Mice mouse : miceList) {
                if (mouse.getTitle().toLowerCase().contains(searchQuery.toLowerCase())) {
                    results.add(mouse);
                }
            }
        }

        return results;
    }


    private void displayMouseDetails(String mouseId) {
        Mice mouse = getMouseById(mouseId);
        if (mouse != null) {
            String details = "ID: " + mouse.getMouseId() + "\n" +
                    "Title: " + mouse.getTitle() + "\n" +
                    "Brand: " + mouse.getBrand() + "\n" +
                    "Model: " + mouse.getModel() + "\n" +
                    "Status: " + mouse.getStatus() + "\n" +
                    "Condition: " + mouse.getCondition();

            Toast.makeText(this, details, Toast.LENGTH_LONG).show();
        }
    }


    private void refreshDashboard() {
        Toast.makeText(this, "Refreshing " + getDashboardTitle(), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> {
            loadDashboardData();
            Toast.makeText(this, "Dashboard updated successfully!", Toast.LENGTH_SHORT).show();
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when returning to dashboard
        refreshDashboard();
    }
}