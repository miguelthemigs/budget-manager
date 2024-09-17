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
    @Override
    public User findById(Long id) {
        return usersDatabase.get(id);  // Finding user by ID
    }

    public void addUser(User user) {
        usersDatabase.put(user.getId(), user);
    }
}
