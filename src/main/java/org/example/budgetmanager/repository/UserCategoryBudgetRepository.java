package org.example.budgetmanager.repository;

import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.UserCategoryBudget;
import org.example.budgetmanager.repository.entity.UserCategoryBudgetEntity;
import org.example.budgetmanager.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserCategoryBudgetRepository extends JpaRepository<UserCategoryBudgetEntity, Long> {
    List<UserCategoryBudgetEntity> findByUserId(Long user_id);
    List<UserCategoryBudgetEntity> findByUserIdAndCategory(Long userId, Category category);
}
