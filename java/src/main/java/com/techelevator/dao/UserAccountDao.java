package com.techelevator.dao;

import com.techelevator.model.UserAccount;

import java.util.List;

public interface UserAccountDao {

    UserAccount getUserAccountByAccountId(int accountId);
    List<UserAccount> getAllUserAccounts();
    UserAccount updateUserAccount(UserAccount userAccount);
    boolean deleteUserAccount(int userAccountId);

}
