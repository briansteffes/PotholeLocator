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

import java.util.List;

@RestController
@RequestMapping("/pothole")
//@PreAuthorize("isAuthenticated()"
public class PotholeController {

    private final PotholeDao potholeDao;
//    private final UserAccountDao userAccountDao;
//    private final UserDao userDao;
//    private Pothole pothole;

    public PotholeController(PotholeDao potholeDao, UserAccountDao userAccountDao,
                             UserDao userDao) {
        this.potholeDao = potholeDao;
//        this.userAccountDao = userAccountDao;
//        this.userDao = userDao;
    }

    @GetMapping("")
    @PreAuthorize("permitAll")
    public List<Pothole> getAllPotholes() {
        return potholeDao.getPotholes();
    }

    @GetMapping("/{user}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_EMPLOYEE')")
    public List<Pothole> getPotholeByUser(@PathVariable User user) {
        return potholeDao.getPotholesByUser(user);
    }

    @GetMapping("/{potholeId}")
    @PreAuthorize("permitAll")
    public Pothole getPotholeById(@PathVariable int potholeId) {
        return potholeDao.getPotholeById(potholeId);
    }

    @GetMapping("/{potholeName}")
    @PreAuthorize("permitAll")
    public Pothole getPotholeById(@PathVariable String potholeName) {
        return potholeDao.getPotholeByName(potholeName);
    }

    @GetMapping("/{category}")
    @PreAuthorize("permitAll")
    public List<Pothole> getPotholesByCategory(@PathVariable Category category) {
        return potholeDao.getPotholesByCategory(category);
    }

    @GetMapping("/{location}")
    @PreAuthorize("permitAll")
    public Pothole getPotholesByLocation(@PathVariable String location) {
        return potholeDao.getPotholeByLocation(location);
    }

    @PostMapping("/report")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_EMPLOYEE')")
    public void addReport(@RequestBody Pothole pothole, Image image) {
        Pothole newPothole = potholeDao.createPothole(pothole, image);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public Pothole updateReport(@PathVariable Pothole pothole) {
        return potholeDao.updatePothole(pothole);
    }

    @PutMapping("/review/delete/{potholeId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public boolean markForDelete(@PathVariable int potholeId) {
        // Review this.
        return false;
    }
}
