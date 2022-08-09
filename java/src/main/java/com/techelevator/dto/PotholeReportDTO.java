package com.techelevator.dto;

import com.techelevator.dao.UserDao;
import com.techelevator.model.Pothole;
import com.techelevator.model.User;

import javax.validation.constraints.NotNull;

public class PotholeReportDTO {
    @NotNull
    private int potholeId;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    private int accountId;

    UserDao userDao;
}
