package org.example.budgetmanager.service;
import java.util.List;
import java.util.Optional;
import org.example.budgetmanager.model.Expense;

public interface ExpenseService {
    void addExpense(Expense expense);
    Optional<List<Expense>> getExpensesForUser(Long userId);
    Expense getExpenseById(Long expenseId);
    void deleteExpense(Long expenseId);
    void updateExpense(Expense expense);
}
