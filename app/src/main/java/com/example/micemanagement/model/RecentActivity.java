package com.example.micemanagement.model;

public class RecentActivity {
    private String activityId;
    private String miceId;
    private String title;
    private String description;
    private String type;
    private String time;
    private String color;
    private int iconRes;
    private String userId;
    private String userName;

    // Constructors
    public RecentActivity() {
        // Default constructor
    }

    public RecentActivity(String miceId, String title, String description, String time, String color, int iconRes) {
        this.miceId = miceId;
        this.title = title;
        this.description = description;
        this.time = time;
        this.color = color;
        this.iconRes = iconRes;
    }

    // GETTER METHODS
    public String getActivityId() {
        return activityId != null ? activityId : "Unknown";
    }

    public String getMiceId() {
        return miceId != null ? miceId : "Unknown";
    }

    public String getTitle() {
        return title != null ? title : "Activity";
    }

    public String getDescription() {
        return description != null ? description : "No description";
    }

    public String getType() {
        return type != null ? type : "General";
    }

    public String getTime() {
        return time != null ? time : "Recently";
    }

    public String getColor() {
        return color != null ? color : "#6366F1";
    }

    public int getIconRes() {
        return iconRes != 0 ? iconRes : android.R.drawable.ic_dialog_info;
    }

    public String getUserId() {
        return userId != null ? userId : "Unknown";
    }

    public String getUserName() {
        return userName != null ? userName : "Unknown User";
    }

    // SETTER METHODS
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public void setMiceId(String miceId) {
        this.miceId = miceId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // HELPER METHODS
    public boolean isIssueActivity() {
        return "issue".equalsIgnoreCase(type) || getTitle().toLowerCase().contains("issue");
    }

    public boolean isReturnActivity() {
        return "return".equalsIgnoreCase(type) || getTitle().toLowerCase().contains("return");
    }

    public String getShortDescription() {
        if (description != null && description.length() > 50) {
            return description.substring(0, 47) + "...";
        }
        return getDescription();
    }

    public String getMouseId() {
    }
}