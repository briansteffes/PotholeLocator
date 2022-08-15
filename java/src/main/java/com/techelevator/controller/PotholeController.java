package com.techelevator.controller;

import com.techelevator.dao.PotholeDao;
import com.techelevator.dao.UserAccountDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Category;
import com.techelevator.model.Image;
import com.techelevator.model.Pothole;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/pothole")
//@PreAuthorize("isAuthenticated()")
public class PotholeController {

    private final PotholeDao potholeDao;

    @Autowired
    public PotholeController(PotholeDao potholeDao) {
        this.potholeDao = potholeDao;
    }

    @GetMapping("")
    public List<Pothole> getAllPotholes() {
        return potholeDao.getPotholes();
    }

    @GetMapping("/public")
    public List<Pothole> getAllPotholesPublic() {
        return potholeDao.getPotholesPublic();
    }

    @GetMapping("/user/")
    public List<Pothole> getPotholeByUser(Principal principal) {
        return potholeDao.getPotholesByUsername(principal.getName());
    }


    @GetMapping("/{potholeId}")
    public Pothole getPotholeById(@PathVariable int potholeId) {
        return potholeDao.getPotholeById(potholeId);
    }

    /*
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
     */


    // TODO add in image parameter
    @PostMapping("/report")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_EMPLOYEE')")
    public void addReport(@RequestBody Pothole pothole) {
        potholeDao.createPothole(pothole);
    }

    @PutMapping("/{potholeId}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
    public Pothole updateReport(@RequestBody Pothole pothole, @PathVariable int potholeId) {
        potholeDao.updatePothole(pothole);
        return getPotholeById(potholeId);
    }

    @PutMapping("/delete/{potholeId}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
    public Pothole markForDelete(@RequestBody Pothole pothole, @PathVariable int potholeId) {
        return potholeDao.markForDelete(pothole);
    }

    @DeleteMapping("/{potholeId}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_ADMIN')")
    public void deletePothole(@PathVariable int potholeId) {
        potholeDao.deletePothole(potholeId);
    }
}
