package com.example.micemanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.micemanagement.model.RecentActivity;
import com.example.micemanagement.R;
import java.util.List;

public class RecentActivityAdapter extends RecyclerView.Adapter<RecentActivityAdapter.ViewHolder> {

    private List<RecentActivity> activityList;

    public RecentActivityAdapter(List<RecentActivity> activityList) {
        this.activityList = activityList;
    }

    public void updateData(List<RecentActivity> newList) {
        this.activityList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemrecentactivity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentActivity activity = activityList.get(position);


        holder.tvTitle.setText(activity.getTitle());
        holder.tvDescription.setText(activity.getDescription());
        holder.tvTime.setText(activity.getTime());
        holder.tvMouseId.setText("Mouse ID: " + activity.getMouseId());


        try {
            holder.colorIndicator.setBackgroundColor(android.graphics.Color.parseColor(activity.getColor()));
        } catch (Exception e) {
            holder.colorIndicator.setBackgroundColor(0xFF6366F1); // Default color
        }
    }

    @Override
    public int getItemCount() {
        return activityList != null ? activityList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View colorIndicator;
        public TextView tvTitle, tvDescription, tvTime, tvMouseId;

        public ViewHolder(View view) {
            super(view);
            colorIndicator = view.findViewById(R.id.viewColorIndicator);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDescription = view.findViewById(R.id.tvDescription);
            tvTime = view.findViewById(R.id.tvTime);
            tvMouseId = view.findViewById(R.id.tvMouseId);
        }
    }
}