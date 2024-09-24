package org.example.budgetmanager.controller;

import jakarta.validation.Valid;
import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.service.impl.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController // handles incoming requests, calls the Service
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseServiceImpl expenseService;

    @Autowired
    public ExpenseController(ExpenseServiceImpl expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("")
    // ex: http://localhost:8080/expenses?userId=1
    public ResponseEntity<List<Expense>> getExpensesForUser(@RequestParam("userId") Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().body(null); // Return 400 Bad Request if userId is null
        }
        // Retrieve expenses for the user
        Optional<List<Expense>> expenses = expenseService.getExpensesForUser(userId);
        return ResponseEntity.ok(expenses.orElse(Collections.emptyList()));
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable("expenseId") Long expenseId) {
        Expense expense = expenseService.getExpenseById(expenseId);
        if (expenseId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(expense);
    }

    @PostMapping("")
    public ResponseEntity<Expense> addExpense(@Valid @RequestBody Expense expense) {
        Expense expenseBuild = Expense.builder()
                .id(expense.getId())
                .category(expense.getCategory())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .userId(expense.getUserId())
                .build();

        expenseService.addExpense(expenseBuild);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
