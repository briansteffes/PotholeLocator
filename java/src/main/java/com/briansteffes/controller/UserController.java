package com.briansteffes.controller;

import com.briansteffes.dao.PotholeDao;
import com.briansteffes.dao.UserDao;
import com.briansteffes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
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
