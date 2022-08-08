package com.techelevator.dao;

import com.techelevator.model.Category;
import com.techelevator.model.Image;
import com.techelevator.model.Pothole;
import com.techelevator.model.User;

import java.util.List;

public interface PotholeDao {
    List<Pothole> getPotholes();
    List<Pothole> getPotholesByUser(User user);
    Pothole getPotholeById(int potholeId);
    Pothole getPotholeByName(String potholeName);
    List<Pothole> getPotholesByCategory(Category category);
    Pothole getPotholeByLocation(String location);
    Pothole createPothole(Pothole pothole, Image image);
    Pothole updatePothole(Pothole pothole);
    void deletePothole(int potholeId);
}
