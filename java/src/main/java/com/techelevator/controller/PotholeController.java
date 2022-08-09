package com.techelevator.controller;

import com.techelevator.dao.PotholeDao;
import com.techelevator.dao.UserAccountDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Pothole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pothole")
@PreAuthorize("isAuthorized()")
public class PotholeController {

    private PotholeDao potholeDao;
    private UserAccountDao userAccountDao;
    private UserDao userDao;

    public PotholeController(PotholeDao potholeDao, UserAccountDao userAccountDao,
                             UserDao userDao) {
        this.potholeDao = potholeDao;
        this.userAccountDao = userAccountDao;
        this.userDao = userDao;
    }

    @GetMapping("/all")
    public List<Pothole> getAllPotholes() {
        List<Pothole> potholes = getAllPotholes();
        return potholes;
    }

}
