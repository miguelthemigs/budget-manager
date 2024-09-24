package org.example.budgetmanager.controller;

import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.service.impl.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // handles incoming requests, calls the Service
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseServiceImpl expenseService;

    @Autowired
    public ExpenseController(ExpenseServiceImpl expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Expense>> getExpensesForUser(@PathVariable("id") Long userId) {
        List<Expense> expenses = expenseService.getExpensesForUser(userId);
        if (userId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(expenses);
    }

}
