package com.example.micemanagement.model;

public class Mice {
    private String miceId;
    private String qrCode;
    private String brand;
    private String model;
    private String type;
    private String purchaseDate;
    private String status;
    private String condition;
    private String lastUser;
    private String lastIssueDate;
    private String lastReturnDate;
    private String notes;
    private String imageUrl;
    private boolean isActive;
    private String createdAt;

   
    public Mice() {
        // Default constructor for Firebase
    }

    public Mice(String miceId, String brand, String model, String status) {
        this.miceId = miceId;
        this.brand = brand;
        this.model = model;
        this.status = status;
        this.isActive = true;
    }

    
    public String getMiceId() {
        return miceId != null ? miceId : "Unknown";
    }

    public String getTitle() {
        return (brand != null ? brand : "Unknown") + " " + (model != null ? model : "Mouse");
    }

    public String getBrand() {
        return brand != null ? brand : "Unknown";
    }

    public String getModel() {
        return model != null ? model : "Unknown";
    }

    public String getType() {
        return type != null ? type : "Not Specified";
    }

    public String getStatus() {
        return status != null ? status : "Unknown";
    }

    public String getCondition() {
        return condition != null ? condition : "Good";
    }

    public String getLastUser() {
        return lastUser != null ? lastUser : "Not issued yet";
    }

    public String getQrCode() {
        return qrCode != null ? qrCode : "MICE_" + getMiceId();
    }

    public String getPurchaseDate() {
        return purchaseDate != null ? purchaseDate : "Not available";
    }

    public String getLastIssueDate() {
        return lastIssueDate != null ? lastIssueDate : "Never issued";
    }

    public String getLastReturnDate() {
        return lastReturnDate != null ? lastReturnDate : "Not returned yet";
    }

    public String getNotes() {
        return notes != null ? notes : "No notes available";
    }

    public String getImageUrl() {
        return imageUrl != null ? imageUrl : "";
    }

    public boolean isActive() {
        return isActive;
    }

    public String getCreatedAt() {
        return createdAt != null ? createdAt : "Unknown";
    }

    
    public void setMiceId(String miceId) {
        this.miceId = miceId;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setLastIssueDate(String lastIssueDate) {
        this.lastIssueDate = lastIssueDate;
    }

    public void setLastReturnDate(String lastReturnDate) {
        this.lastReturnDate = lastReturnDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // HELPER METHODS
    public boolean isAvailable() {
        return "Available".equalsIgnoreCase(status);
    }

    public boolean isIssued() {
        return "Issued".equalsIgnoreCase(status);
    }

    public boolean needsMaintenance() {
        return "Maintenance".equalsIgnoreCase(status) || "Poor".equalsIgnoreCase(condition);
    }

    public String getFullDetails() {
        return getTitle() + " (" + getMiceId() + ") - " + getStatus();
    }

    public String getMouseId() {
    }
}