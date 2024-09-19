package org.example.budgetmanager.service;

import org.example.budgetmanager.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    User findById(Long id);
    void addUser(User user);
    void deleteUser(Long id);
    ResponseEntity<Void> editUser(User user);
}
