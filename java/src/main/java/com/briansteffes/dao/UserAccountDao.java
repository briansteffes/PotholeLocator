package com.briansteffes.dao;

import com.briansteffes.model.UserAccount;

import java.util.List;

public interface UserAccountDao {

    UserAccount getUserAccountByAccountId(int accountId);
    UserAccount getUserAccountByUserId(int userId);
    List<UserAccount> getAllUserAccounts();
    UserAccount createUserAccount(UserAccount userAccount);
    UserAccount updateUserAccount(UserAccount userAccount);
    void deleteUserAccount(int userAccountId);

}
