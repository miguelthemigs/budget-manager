package org.example.budgetmanager.service.impl;

import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.repository.UserCategoryBudgetRepository;
import org.example.budgetmanager.repository.entity.UserCategoryBudgetEntity;
import org.example.budgetmanager.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCategoryBudgetServiceImpl {


        private final UserCategoryBudgetRepository repository;

        @Autowired
        public UserCategoryBudgetServiceImpl(UserCategoryBudgetRepository repository) {
            this.repository = repository;
        }


    public UserCategoryBudgetEntity createBudget(UserEntity user, Category category, Double amount) {
            UserCategoryBudgetEntity budget = new UserCategoryBudgetEntity();
            budget.setUser(user);
            budget.setCategory(category);
            budget.setBudgetAmount(amount);
            return repository.save(budget);
        }

    public Optional<UserCategoryBudgetEntity> getBudgetsForUser(Long userId) {
        return repository.findById(userId);
    }

        public List<UserCategoryBudgetEntity> getBudgetsForUser(UserEntity user) {
            return repository.findByUser(user);
        }

        // Add other necessary methods for updating and deleting budgets
    }


