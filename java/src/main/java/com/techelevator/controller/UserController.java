package com.techelevator.controller;

import com.techelevator.dao.PotholeDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserDao userDao;

    @Autowired
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
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    public User getUserById(@PathVariable int userId) {
        return userDao.getUserById(userId);
    }


    // user by type
    @GetMapping("/filter")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    public List<User> filterByRole(@RequestParam String role){
        return userDao.filterUsers(role);
    }

}
