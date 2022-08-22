package com.briansteffes.controller;

import com.briansteffes.dao.UserAccountDao;
import com.briansteffes.dao.UserDao;
import com.briansteffes.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/account")
public class UserAccountController {
    private final UserAccountDao userAccountDao;
    private final UserDao userDao;

    @Autowired
    public UserAccountController(UserAccountDao userAccountDao, UserDao userDao) {
        this.userAccountDao = userAccountDao;
        this.userDao = userDao;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUserAccount(@RequestBody UserAccount userAccount) {
        userAccountDao.createUserAccount(userAccount);
    }

    @GetMapping("/access/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserAccount getUserAccountFromUserId(@PathVariable int id) {
        return userAccountDao.getUserAccountByUserId(id);
    }

}
