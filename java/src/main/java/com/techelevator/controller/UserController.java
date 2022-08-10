package com.techelevator.controller;

import com.techelevator.dao.PotholeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/")
@PreAuthorize("isAuthenticated()")



public class UserController {

    private final UserDao userDao;

    public UserController(UserDao userDao, PotholeDao potholeDao) {
        this.userDao = userDao;
    }

    // /user/
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return userDao.getUsers();
    }

    // /user/id
    // user by type


}
