package com.techelevator.model;

import java.time.LocalDateTime;

public class Pothole {

    private int potholeId;

    private String potholeLocation;

    private String potholeName;
    private int accountId;
    private int statusId;
    private int imageId;
    private int categoryId;
    private boolean active;
    private LocalDateTime uploadTime;

    public int getPotholeId() {
        return potholeId;
    }

    public void setPotholeId(int potholeId) {
        this.potholeId = potholeId;
    }


    public String getPotholeLocation() {
        return potholeLocation;
    }

    public void setPotholeLocation(String potholeLocation) {
        this.potholeLocation = potholeLocation;
    }

    public String getPotholeName() {
        return potholeName;
    }

    public void setPotholeName(String potholeName) {
        this.potholeName = potholeName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }
}
