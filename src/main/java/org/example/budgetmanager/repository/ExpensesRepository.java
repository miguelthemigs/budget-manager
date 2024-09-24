package org.example.budgetmanager.repository;

import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.model.User;

import java.util.List;
import java.util.Optional;

public interface ExpensesRepository  {
    // extends JpaRepository<Expense, Long>
    void addExpense(Expense expense);
    List<Expense> getExpensesForUser(Long userId);
    Optional<Expense> findExpenseById(Long expenseId);
    void deleteExpense(Long expenseId);
    void updateExpense(Expense expense);
}
