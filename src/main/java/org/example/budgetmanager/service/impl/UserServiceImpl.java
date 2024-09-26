package org.example.budgetmanager.service.impl;

import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.impl.UserRepositoryImpl;
import org.example.budgetmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl userRepositoryImpl;

    @Autowired
    public UserServiceImpl(UserRepositoryImpl userRepositoryImpl) {
        this.userRepositoryImpl = userRepositoryImpl;
    }

    public User findById(Long id) {
        return userRepositoryImpl.findById(id);
    }

    public void addUser(User user) {
        userRepositoryImpl.addUser(user);
    }

    public void deleteUser(Long id) {
        userRepositoryImpl.deleteUser(id);
    }

    public ResponseEntity<Void> editUser(User user) {
        try {
            userRepositoryImpl.editUser(user);
            return ResponseEntity.ok().build(); // Successful update
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public void defineMonthlyBudget(Long id, double budget) {
        userRepositoryImpl.defineMonthlyBudget(id, budget);
    }

    public void setCategoryBudget(Long id, double budget, Category category) {
        userRepositoryImpl.setCategoryBudget(id, budget, category);
    }
}
