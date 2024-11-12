package org.example.budgetmanager.service;
import org.example.budgetmanager.model.User;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);
    void addUser(User user);
    void deleteUser(Long id);
    void editUser(User user);
    void defineMonthlyBudget(Long id, double budget);
    void partialUpdateUser(Long id, User user);
}
