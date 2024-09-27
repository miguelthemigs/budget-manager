package org.example.budgetmanager.service.impl;

import jakarta.validation.Valid;
import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.impl.UserRepositoryImpl;
import org.example.budgetmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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

    public void editUser(@Valid User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
       else if (user.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        userRepositoryImpl.editUser(user);
}


    public void defineMonthlyBudget(Long id, double budget) {
        userRepositoryImpl.defineMonthlyBudget(id, budget);
    }

    public void setCategoryBudget(Long id, double budget, Category category) {
        userRepositoryImpl.setCategoryBudget(id, budget, category);
    }
}
