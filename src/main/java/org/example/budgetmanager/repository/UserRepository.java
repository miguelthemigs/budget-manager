package org.example.budgetmanager.repository;

import org.example.budgetmanager.model.User;

public interface UserRepository {

    User findById(Long id);
    void addUser(User user);
    void deleteUser(Long id);
}
