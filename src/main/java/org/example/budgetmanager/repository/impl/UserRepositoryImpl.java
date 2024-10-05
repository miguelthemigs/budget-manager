package org.example.budgetmanager.repository.impl;

import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.Currency;
import org.example.budgetmanager.model.Role;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.UserRepository;
import org.example.budgetmanager.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl {
    private UserRepository userRepository;

//    public void defineMonthlyBudget(Long id, double amount) {
//        UserEntity user = userRepository.findById(id).orElse(null);
//        if (user == null) {
//            throw new IllegalArgumentException("User does not exist");
//        }
//        user.setMonthlyBudget(amount);
    }

//    public void setCategoryBudget(Long id, double budget, Category category) {
//        User user = findById(id);
//        if (user == null) {
//            throw new IllegalArgumentException("User does not exist");
//        }
//        user.getCategoryBudgets().put(category, budget);
//    }

