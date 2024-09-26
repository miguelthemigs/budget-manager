package org.example.budgetmanager.repository.impl;

import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.Currency;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private Map<Long, User> usersDatabase = new HashMap<>(); // fake database for now

    // Constructor to add some sample users to the simulated database
    public UserRepositoryImpl() {
        Map<Category, Double> budgets = new HashMap<>();
        User user1 = new User(1L, "John Doe", "test@example.com", "password123", 500.0, Currency.BRL, 1000.0, budgets);
        User user2 = new User(2L, "Jane Doe", "jane@example.com", "password456", 1000.0, Currency.USD, 2000.0, budgets);
        budgets.put(Category.RESTAURANTS, 100.0);
        budgets.put(Category.TRANSPORTATION, 200.0);
        usersDatabase.put(user1.getId(), user1);
        usersDatabase.put(user2.getId(), user2);
    }

    // Method to add sample data for testing
    private void populateSampleData() {
        Map<Category, Double> budgets = new HashMap<>();
        budgets.put(Category.RESTAURANTS, 100.0);
        budgets.put(Category.TRANSPORTATION, 200.0);
        User user1 = new User(1L, "John Doe", "test@example.com", "password123", 500.0, Currency.BRL, 1000.0, budgets);
        User user2 = new User(2L, "Jane Doe", "jane@example.com", "password456", 1000.0, Currency.USD, 2000.0, budgets);
        usersDatabase.put(user1.getId(), user1);
        usersDatabase.put(user2.getId(), user2);
    }
    public void clearData() {
        usersDatabase.clear();
    }
    public void addSampleData() {
        populateSampleData();
    }

    public User findById(Long id) {
        return usersDatabase.get(id);  // Finding user by ID
    }

    public void addUser(User user) {
        usersDatabase.put(user.getId(), user);
    }

    public void deleteUser(Long id) {
        usersDatabase.remove(id);
    }

    public void editUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        Long userId = user.getId();
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        // Ensure that the user exists in the database before attempting to update
        if (!usersDatabase.containsKey(userId)) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist");
        }

        // Validate that no other attributes of the user are null (if applicable)
        if (user.getName() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("User name and email cannot be null");
        }

        usersDatabase.put(userId, user);
    }

    public void defineMonthlyBudget(Long id, double amount) {
        User user = findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        user.setMonthlyBudget(amount);
    }

    public void setCategoryBudget(Long id, double budget, Category category) {
        User user = findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        user.getCategoryBudgets().put(category, budget);
    }
}
