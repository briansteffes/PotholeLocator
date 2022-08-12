package com.techelevator.controller;

import com.techelevator.dao.PotholeDao;
import com.techelevator.dao.UserAccountDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Category;
import com.techelevator.model.Image;
import com.techelevator.model.Pothole;
import com.techelevator.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/pothole")
public class PotholeController {

    private final PotholeDao potholeDao;
    private final UserAccountDao userAccountDao;
//    private final UserDao userDao;
//    private Pothole pothole;

    public PotholeController(PotholeDao potholeDao, UserAccountDao userAccountDao) {
        this.potholeDao = potholeDao;
        this.userAccountDao = userAccountDao;
//        this.userDao = userDao;
    }

    @GetMapping("")
    public List<Pothole> getAllPotholes() {
        return potholeDao.getPotholes();
    }

    // @Todo: how can we restrict this to the user that is logged in? Or would that be something on the  front end?
    @GetMapping("/user")
    public List<Pothole> getPotholeByUser(Principal principal) {
        return potholeDao.getPotholesByUsername(principal.getName());
    }

    @GetMapping("/{potholeId}")
    public Pothole getPotholeById(@PathVariable int potholeId) {
        return potholeDao.getPotholeById(potholeId);
    }

    @GetMapping("/filter/{potholeName}")
    public Pothole getPotholeById(@PathVariable String potholeName) {
        return potholeDao.getPotholeByName(potholeName);
    }

    @GetMapping("/category/{category}")
    public List<Pothole> getPotholesByCategory(@PathVariable Category category) {
        return potholeDao.getPotholesByCategory(category);
    }

    @GetMapping("/location/{location}")
    public Pothole getPotholesByLocation(@PathVariable String location) {
        return potholeDao.getPotholeByLocation(location);
    }

    @PostMapping("/report")
    public void addReport(@RequestBody Pothole pothole) {
        Pothole newPothole = potholeDao.createPothole(pothole);
    }

    @PutMapping("/update")
    public Pothole updateReport(@RequestBody Pothole pothole) {
        return potholeDao.updatePothole(pothole);
    }

    @PutMapping("/review/delete/{potholeId}")
    public boolean markForDelete(@PathVariable int potholeId) {
        // Review this.
        return false;
    }
}
