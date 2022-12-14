package com.briansteffes.dao;

import com.briansteffes.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    List<User> filterUsers(String filter);

    User getUserById(int userId);

    User getUserByUsername(String username);

    int getUserIdByUsername(String username);

    boolean create(String username, String password, String role);
}
