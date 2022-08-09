package com.techelevator.dto;

import com.techelevator.model.Pothole;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * JSON serializable DTO containing Pothole data.
 *
 */
public class PotholeDTO {
    private int potholeId;
    private double latitude;
    private double longitude;
    private String potholeName;
    private int accountId;
    private int statusId;
    private LocalDateTime uploadTime;

    public PotholeDTO() {
    }

    public PotholeDTO(int potholeId, double latitude, double longitude, String potholeName,
                      int accountId, int statusId, LocalDateTime uploadTime) {
        this.potholeId = potholeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.potholeName = potholeName;
        this.accountId = accountId;
        this.statusId = statusId;
        this.uploadTime = uploadTime;
    }

    public static PotholeDTO mapFromPotholeModel(Pothole pothole) {
        return new PotholeDTO(pothole.getPotholeId(), pothole.getLatitude(),
            pothole.getLongitude(), pothole.getPotholeName(), pothole.getAccountId(),
            pothole.getStatusId(), pothole.getUploadTime());
    }

    public static List<PotholeDTO> mapFromPotholesModel(List<Pothole> potholes) {
        return potholes.stream().map(PotholeDTO::mapFromPotholeModel).collect(Collectors.toList());
    }

    public int getPotholeId() {
        return potholeId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPotholeName() {
        return potholeName;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getStatusId() {
        return statusId;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }
}
