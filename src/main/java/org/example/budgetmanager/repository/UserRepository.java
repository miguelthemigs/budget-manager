package org.example.budgetmanager.repository;

import org.example.budgetmanager.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
     //void addUser(UserEntity user);

//    User findById(Long id);
//    void addUser(User user);
//    void deleteUser(Long id);
//    void editUser(User user);
//    void defineMonthlyBudget(Long id, double budget);
//    void setCategoryBudget(Long id, double budget, Category category);


}
