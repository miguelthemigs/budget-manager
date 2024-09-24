package org.example.budgetmanager.repository.impl;

import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.ExpensesRepository;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository // interacts with the database, maps data to Model objects.
public class ExpensesRepositoryImpl implements ExpensesRepository {
    private final Map<Long, List<Expense>> userExpensesMap = new HashMap<>();
    private final UserRepositoryImpl userRepository;
    public ExpensesRepositoryImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;

        List<Expense> user1Expenses = new ArrayList<>();
        user1Expenses.add(new Expense(1L, "Food", "Lunch", 10.5, "2024-09-24", 1L));
        user1Expenses.add(new Expense(2L, "Transport", "Bus Ticket", 2.75, "2024-09-23", 1L));

        List<Expense> user2Expenses = new ArrayList<>();
        user2Expenses.add(new Expense(3L, "Entertainment", "Movie", 12.0, "2024-09-22", 2L));
        user2Expenses.add(new Expense(4L, "Groceries", "Weekly groceries", 50.0, "2024-09-21", 2L));

        userExpensesMap.put(1L, user1Expenses);
        userExpensesMap.put(2L, user2Expenses);
    }
    // computeIfAbsent is a method in Java's Map interface that checks if a key is present in the map.
    // If the key is not found, it computes a new value using the provided function and inserts it into the map.
    // If the key exists, it returns the current value without computing anything.
    public void addExpense(Expense expense) {
        Long userId = expense.getUserId();
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        userExpensesMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(expense);
    }

    public List<Expense> getExpensesForUser(Long userId) {
        return userExpensesMap.getOrDefault(userId, Collections.emptyList());  // Return the list wrapped in Optional
    }

    // getOrDefault is a method in Java's Map interface that allows you to retrieve a value associated with a key. If the key is not found in the map,
    // it returns a default value that you specify, instead of null.
    public Optional<Expense> findExpenseById(Long expenseId) {
        return userExpensesMap.values().stream()
                .flatMap(Collection::stream)  // Flatten the lists of expenses into a single stream
                .filter(expense -> expense.getId().equals(expenseId))  // Filter by expenseId
                .findFirst();
    }



}
