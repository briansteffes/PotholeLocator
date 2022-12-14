package com.briansteffes.controller;

import com.briansteffes.dao.PotholeDao;
import org.springframework.beans.factory.annotation.Autowired;
import com.briansteffes.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/pothole")
public class PotholeController {

    private final PotholeDao potholeDao;

    @Autowired
    public PotholeController(PotholeDao potholeDao) {
        this.potholeDao = potholeDao;
    }

//    @GetMapping("")
//    public List<Pothole> getAllPotholes() {
//        return potholeDao.getPotholes();
//    }

    @GetMapping("")
    public List<PotholeDTO> getAllPotholeDTOs() {
        return potholeDao.getPotholeDTOs();
    }

//    @GetMapping("/public")
//    public List<Pothole> getAllPotholesPublic() {
//        return potholeDao.getPotholesPublic();
//    }

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
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/report")
    public void addReport(@RequestBody Pothole pothole) {
        potholeDao.createPothole(pothole);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{potholeId}")
    public void updateReport(@PathVariable int potholeId, @RequestBody PotholeDTO pothole) {
        potholeDao.updatePothole(pothole);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/delete/{potholeId}")
    public Pothole markForDelete(@RequestBody Pothole pothole, @PathVariable int potholeId) {
        return potholeDao.markForDelete(pothole);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{potholeId}")
    public void deletePothole(@PathVariable int potholeId) {
        potholeDao.deletePothole(potholeId);
    }
}
