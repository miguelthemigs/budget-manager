package org.example.budgetmanager.service;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import org.example.budgetmanager.model.Expense;

public interface ExpenseService {
    void addExpense(Expense expense);
    Optional<List<Expense>> getExpensesForUser(Long userId);
    Expense getExpenseById(Long expenseId);
    void deleteExpense(Long expenseId);
    void updateExpense(Expense expense);
    Optional<Double> getTotalValueOfExpensesForSelectedMonth(Long userId, String month);
    List<Expense> getExpensesForUserAndMonth(Long userId, int month, int year);
    double getTotalSpentForCategoryBudget(Long userId, String category, YearMonth yearMonth);
}
