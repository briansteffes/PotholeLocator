package com.techelevator.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Pothole {

    private int potholeId;

    private BigDecimal potholeLat;
    private BigDecimal potholeLong;
    private String potholeName;
    private int accountId;
    private int imageId;
    private int categoryId;
    private int statusId;
    private boolean active;
    private Timestamp uploadTime;

    public Pothole(int potholeId, BigDecimal potholeLat, BigDecimal potholeLong, String potholeName, int accountId, int imageId, int categoryId,
                   int statusId, boolean active, Timestamp uploadTime) {
        this.potholeId = potholeId;
        this.potholeLat = potholeLat;
        this.potholeLong = potholeLong;
        this.potholeName = potholeName;
        this.accountId = accountId;
        this.imageId = imageId;
        this.categoryId = categoryId;
        this.active = active;
        this.uploadTime = uploadTime;
    }

    public Pothole() {}

    public int getPotholeId() {
        return potholeId;
    }

    public void setPotholeId(int potholeId) {
        this.potholeId = potholeId;
    }

    public BigDecimal getPotholeLat() {
        return potholeLat;
    }

    public void setPotholeLat(BigDecimal potholeLat) {
        this.potholeLat = potholeLat;
    }

    public BigDecimal getPotholeLong() {
        return potholeLong;
    }

    public void setPotholeLong(BigDecimal potholeLong) {
        this.potholeLong = potholeLong;
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

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }
}
