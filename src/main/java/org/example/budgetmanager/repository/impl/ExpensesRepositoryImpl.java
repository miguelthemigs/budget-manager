package org.example.budgetmanager.repository.impl;

import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.repository.ExpensesRepository;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository // interacts with the database, maps data to Model objects.
public class ExpensesRepositoryImpl implements ExpensesRepository {
    private final Map<Long, List<Expense>> userExpensesMap = new HashMap<>();

    public ExpensesRepositoryImpl() {
        // Create some sample expenses for user 1
        List<Expense> user1Expenses = new ArrayList<>();
        user1Expenses.add(new Expense(1L, "Food", "Lunch", 10.5, "2024-09-24", 1L));
        user1Expenses.add(new Expense(2L, "Transport", "Bus Ticket", 2.75, "2024-09-23", 1L));

        // Create some sample expenses for user 2
        List<Expense> user2Expenses = new ArrayList<>();
        user2Expenses.add(new Expense(3L, "Entertainment", "Movie", 12.0, "2024-09-22", 2L));
        user2Expenses.add(new Expense(4L, "Groceries", "Weekly groceries", 50.0, "2024-09-21", 2L));

        // Add the expenses to the userExpensesMap
        userExpensesMap.put(1L, user1Expenses);
        userExpensesMap.put(2L, user2Expenses);
    }

    // Add an expense for a specific user (by userId)
    public void addExpense(Expense expense) {
        Long userId = expense.getUserId();
        userExpensesMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(expense);
    }

    // Get all expenses for a specific user (by userId)
    public List<Expense> getExpensesForUser(Long userId) {
        return userExpensesMap.getOrDefault(userId, Collections.emptyList());
    }



}
