package com.techelevator.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PotholeDTO {

    private BigDecimal potholeLat;
    private BigDecimal potholeLong;
    private String potholeName;
    private String username;
    private byte[] imageData;
    private String category;
    private String status;
    private boolean active;
    private Timestamp uploadTime;

    public PotholeDTO(BigDecimal potholeLat, BigDecimal potholeLong, String potholeName, String username, byte[] imageData,
                      String category, String status, boolean active, Timestamp uploadTime) {
        this.potholeLat = potholeLat;
        this.potholeLong = potholeLong;
        this.potholeName = potholeName;
        this.username = username;
        this.imageData = imageData;
        this.category = category;
        this.status = status;
        this.active = active;
        this.uploadTime = uploadTime;
    }

    public PotholeDTO() {}

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String accountId) {
        this.username = username;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String Category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
