package org.example.budgetmanager.controller;

import jakarta.validation.Valid;
import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController // Handles incoming requests, calls the Service
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
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
    public ResponseEntity<?> getExpensesTotalForSelectedMonth(@RequestParam("userId") Long userId, @RequestParam("month") String month) {
        if (userId == null || month == null) {
            return ResponseEntity.badRequest().build();
        }

        if (!month.matches("\\d{4}-\\d{2}")) {
            return ResponseEntity.badRequest().body("Invalid month format. Please use yyyy-MM.");
        }

        Optional<Double> expenses = expenseService.getTotalValueOfExpensesForSelectedMonth(userId, month);
        return ResponseEntity.ok(expenses.orElse(0.0)); // Return 0.0 if no expenses found
    }

    @GetMapping("/perMonth")
    // Ex: http://localhost:8090/expenses/perMonth?userId=1&month=10&year=2024
    public ResponseEntity<List<Expense>> getExpensesForUserAndMonth(@RequestParam("userId") Long userId, @RequestParam("month") int month, @RequestParam("year") int year) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Expense> expenses = expenseService.getExpensesForUserAndMonth(userId, month, year);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/categoryBudget")
    // ex: http://localhost:8090/expenses/categoryBudget?userId=1&category=SUBSCRIPTIONS&year=2024&month=11
    public ResponseEntity<Double> getTotalSpentForCategoryBudget(
            @RequestParam("userId") Long userId,
            @RequestParam("category") String category,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        if (userId == null || category == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // Construct the YearMonth object from year and month
            YearMonth yearMonth = YearMonth.of(year, month);

            // Call the service method
            double totalSpent = expenseService.getTotalSpentForCategoryBudget(userId, category, yearMonth);
            return ResponseEntity.ok(totalSpent);
        } catch (IllegalArgumentException ex) {
            // Handle invalid category or invalid YearMonth
            return ResponseEntity.badRequest().body(-1.0); // Optional: Return a specific error value
        } catch (Exception ex) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
