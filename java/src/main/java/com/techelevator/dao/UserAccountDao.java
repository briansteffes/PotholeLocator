package com.techelevator.dao;

import com.techelevator.model.User;
import com.techelevator.model.UserAccount;

import java.util.List;

public interface UserAccountDao {

    UserAccount getUserAccountByAccountId(int accountId);
    UserAccount getUserAccountByUserId(int userId);
    List<UserAccount> getAllUserAccounts();
    UserAccount createUserAccount(UserAccount userAccount);
    UserAccount updateUserAccount(UserAccount userAccount);
    void deleteUserAccount(int userAccountId);

}
