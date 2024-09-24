package org.example.budgetmanager.service;
import java.util.List;
import org.example.budgetmanager.model.Expense;

public interface ExpenseService {
    void addExpense(Expense expense);
    List<Expense> getExpensesForUser(Long userId);

}
