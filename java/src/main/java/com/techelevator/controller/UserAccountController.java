package com.techelevator.controller;

import com.techelevator.dao.UserAccountDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/account")
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

}
