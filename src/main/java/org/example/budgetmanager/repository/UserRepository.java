package org.example.budgetmanager.repository;

import jdk.jfr.Registered;
import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  {

    User findById(Long id);
    void addUser(User user);
    void deleteUser(Long id);
    void editUser(User user);
    void defineMonthlyBudget(Long id, double budget);
    void setCategoryBudget(Long id, double budget, Category category);


}
