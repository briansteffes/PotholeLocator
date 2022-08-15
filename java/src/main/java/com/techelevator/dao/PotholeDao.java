package com.techelevator.dao;

import com.techelevator.model.*;

import java.util.List;

public interface PotholeDao {
    List<Pothole> getPotholes();

//    List<Pothole> getPotholesPublic();

    List<PotholeDTO> getPotholeDTOs();

    List<Pothole> getPotholesByUsername(String username);

    Pothole getPotholeById(int potholeId);

    Pothole getPotholeByName(String potholeName);

    List<Pothole> getPotholesByCategory(Category category);

    Pothole getPotholeByLocation(String location);

    // TODO: Add in image parameter
    Pothole createPothole(Pothole pothole);

    void updatePothole(Pothole pothole);

    Pothole markForDelete(Pothole pothole);

    void deletePothole(int potholeId);
}
