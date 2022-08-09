package com.techelevator.dto;

import com.techelevator.model.Pothole;

import java.util.List;

public class PotholesDTO {
    List<PotholeDTO> potholes;

    public PotholesDTO(List<PotholeDTO> potholes) {
        this.potholes = potholes;
    }

    public List<PotholeDTO> getPotholes() {
        return potholes;
    }

    public void setPotholes(List<PotholeDTO> potholes) {
        this.potholes = potholes;
    }
}
