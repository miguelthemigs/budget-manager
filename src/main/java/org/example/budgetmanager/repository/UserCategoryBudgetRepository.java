package org.example.budgetmanager.repository;

import org.example.budgetmanager.repository.entity.UserCategoryBudgetEntity;
import org.example.budgetmanager.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryBudgetRepository extends JpaRepository<UserCategoryBudgetEntity, Long> {
    List<UserCategoryBudgetEntity> findByUser(UserEntity user);
}
