package org.example.budgetmanager.repository.impl;

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
        User user1 = new User(1L, "John Doe", "test@example.com", "password123");
        User user2 = new User(2L, "Jane Doe", "jane@example.com", "password456");

        usersDatabase.put(user1.getId(), user1);
        usersDatabase.put(user2.getId(), user2);
    }

    // Method to add sample data for testing
    private void populateSampleData() {
        User user1 = new User(1L, "John Doe", "test@example.com", "password123");
        User user2 = new User(2L, "Jane Doe", "jane@example.com", "password456");
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
}
