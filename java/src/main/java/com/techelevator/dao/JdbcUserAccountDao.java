package com.techelevator.dao;

import com.techelevator.model.User;
import com.techelevator.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcUserAccountDao implements UserAccountDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserAccount getUserAccountByAccountId(int accountId) {
        UserAccount userAccount = null;
        String sql = "SELECT * FROM user_accounts WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if (results.next()) {
            userAccount = mapRowToUserAccount(results);
        }
        return userAccount;
    }

    @Override
    public UserAccount getUserAccountByUserId(int userId) {
        UserAccount userAccount = null;
        String sql = "SELECT * FROM user_accounts WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            userAccount = mapRowToUserAccount(results);
        }
        return userAccount;
    }

    @Override
    public List<UserAccount> getAllUserAccounts() {
        List<UserAccount> userAccounts = new ArrayList<>();
        String sql = "SELECT * FROM user_accounts;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            userAccounts.add(mapRowToUserAccount(results));
        }
        return userAccounts;
    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        String sql = "INSERT INTO user_accounts(user_id, f_name, l_name, email, phone) " +
                "VALUES(?, ?, ?, ?, ?) RETURNING account_id;";
        Integer accountId = jdbcTemplate.queryForObject(sql, Integer.class, userAccount.getUserId(),
                userAccount.getFirstName(), userAccount.getLastName(), userAccount.getEmail(), userAccount.getPhone());

        return getUserAccountByAccountId(accountId);
    }

    @Override
    public UserAccount updateUserAccount(UserAccount userAccount) {
        String sql = "UPDATE user_accounts " +
                "SET user_id = ?, f_name = ?, l_name = ?, email = ?, phone = ?, flagged = ? " +
                "WHERE account_id = ?;";
        jdbcTemplate.update(sql, userAccount.getUserId(), userAccount.getFirstName(), userAccount.getLastName(),
                userAccount.getEmail(), userAccount.getPhone(), userAccount.isFlagged(), userAccount.getAccountId());

        return getUserAccountByAccountId(userAccount.getAccountId());
    }

    @Override
    public void deleteUserAccount(int userAccountId) {
        String sql = "DELETE FROM user_accounts WHERE account_id = ?;";
        jdbcTemplate.update(sql, userAccountId);
    }

    private UserAccount mapRowToUserAccount(SqlRowSet rowSet) {
        UserAccount userAccount = new UserAccount();
        userAccount.setAccountId(rowSet.getInt("account_id"));
        userAccount.setUserId(rowSet.getInt("user_id"));
        userAccount.setFirstName(rowSet.getString("f_name"));
        userAccount.setLastName(rowSet.getString("l_name"));
        userAccount.setEmail(rowSet.getString("email"));
        userAccount.setPhone(BigInteger.valueOf(rowSet.getLong("phone")));
        userAccount.setFlagged(rowSet.getBoolean("flagged"));
        return userAccount;
    }
}
