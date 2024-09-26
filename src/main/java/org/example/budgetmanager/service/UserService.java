package org.example.budgetmanager.service;

import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    User findById(Long id);
    void addUser(User user);
    void deleteUser(Long id);
    ResponseEntity<Void> editUser(User user);
    void defineMonthlyBudget(Long id, double budget);
    void setCategoryBudget(Long id, double budget, Category category);
}
