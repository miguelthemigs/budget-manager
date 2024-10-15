package org.example.budgetmanager.controller;

import jakarta.validation.Valid;
import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.service.impl.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController // Handles incoming requests, calls the Service
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseServiceImpl expenseService;

    @Autowired
    public ExpenseController(ExpenseServiceImpl expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("")
    // Ex: http://localhost:8080/expenses?userId=1
    public ResponseEntity<List<Expense>> getExpensesForUser(@RequestParam("userId") Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if userId is null
        }

        // Retrieve expenses for the user
        Optional<List<Expense>> expenses = expenseService.getExpensesForUser(userId);
        return ResponseEntity.ok(expenses.orElse(Collections.emptyList()));
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable("expenseId") Long expenseId) {
        if (expenseId == null) {
            return ResponseEntity.badRequest().build(); // Return 400 if expenseId is null
        }

        Expense expense = expenseService.getExpenseById(expenseId);
        return ResponseEntity.ok(expense);
    }

    @PostMapping("")
    public ResponseEntity<Expense> addExpense(@Valid @RequestBody Expense expense) {
        // Use the builder pattern to create an Expense instance
        Expense expenseBuild = Expense.builder()
                .id(expense.getId()) // Should be null for a new expense
                .category(expense.getCategory())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .userId(expense.getUserId())
                .build();

        expenseService.addExpense(expenseBuild);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable("expenseId") Long expenseId) {
        if (expenseId == null) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

        expenseService.deleteExpense(expenseId);
        return ResponseEntity.status(HttpStatus.OK).build(); // 200 OK
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<Void> updateExpense(@PathVariable("expenseId") Long expenseId, @Valid @RequestBody Expense expense) {
        if (expenseId == null || expense.getId() == null) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request if either ID is null
        }

        // Update the expense using the builder pattern
        Expense updatedExpense = Expense.builder()
                .id(expenseId) // Set the ID from the path
                .category(expense.getCategory())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .userId(expense.getUserId())
                .build();

        expenseService.updateExpense(updatedExpense);
        return ResponseEntity.status(HttpStatus.OK).build(); // 200 OK
    }

    @GetMapping("/monthly")
    // Ex: http://localhost:8090/expenses/monthly?userId=1&month=2024-10
    public ResponseEntity<?> getExpensesForSelectedMonth(@RequestParam("userId") Long userId, @RequestParam("month") String month) {
        if (userId == null || month == null) {
            return ResponseEntity.badRequest().build();
        }

        if (!month.matches("\\d{4}-\\d{2}")) {
            return ResponseEntity.badRequest().body("Invalid month format. Please use yyyy-MM.");
        }

        Optional<List<Expense>> expenses = expenseService.getAllExpensesForSelectedMonth(userId, month);
        return ResponseEntity.ok(expenses.orElse(Collections.emptyList()));
    }
}
