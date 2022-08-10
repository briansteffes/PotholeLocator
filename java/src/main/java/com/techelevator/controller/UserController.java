package com.techelevator.controller;

import com.techelevator.dao.PotholeDao;
import com.techelevator.dao.UserDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@PreAuthorize("isAuthorized")



public class UserController {

    private final PotholeDao potholeDao;
    private final UserDao userDao;

    public UserController(UserDao userDao, PotholeDao potholeDao) {
        this.userDao = userDao;
        this.potholeDao = potholeDao;
    }

    // /user/
    public User getAllUsers() {
        // this is a different function name than the select all for potholes, we should make them the same
        // for consistency. (findAll v getAllPotholes)
        return userDao.findAll()
    }
    // /user/id
    // user by type


}
