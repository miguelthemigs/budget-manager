package org.example.budgetmanager.repository;

import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.entity.ExpensesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpensesRepository extends JpaRepository<ExpensesEntity, Long> {

    List<ExpensesEntity> findByUserId(Long userId);
    List<ExpensesEntity> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    Collection<ExpensesEntity> findByUserIdAndCategoryAndDateBetween(Long userId, Category category, LocalDate startDate, LocalDate endDate);
    @Query("SELECT SUM(e.amount) FROM ExpensesEntity e WHERE e.userId = :userId AND e.date BETWEEN :startDate AND :endDate")
    Optional<Double> getTotalValueOfExpensesForSelectedMonth(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}
