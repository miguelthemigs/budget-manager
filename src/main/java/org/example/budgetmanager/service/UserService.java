package org.example.budgetmanager.service;

import org.example.budgetmanager.model.User;

public interface UserService {

    User findById(Long id);
    void addUser(User user);
    void deleteUser(Long id);
}
