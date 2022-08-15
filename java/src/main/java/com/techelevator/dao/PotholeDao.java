package com.techelevator.dao;

import com.techelevator.model.Category;
import com.techelevator.model.Image;
import com.techelevator.model.Pothole;
import com.techelevator.model.User;

import java.util.List;

public interface PotholeDao {
    List<Pothole> getPotholes();

    List<Pothole> getPotholesPublic();

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
