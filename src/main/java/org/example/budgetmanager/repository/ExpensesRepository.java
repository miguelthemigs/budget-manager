package org.example.budgetmanager.repository;

import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.entity.ExpensesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpensesRepository extends JpaRepository<ExpensesEntity, Long> {

    List<ExpensesEntity> findByUserId(Long userId);
    List<ExpensesEntity> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

}
