package org.example.budgetmanager.controller;

import org.example.budgetmanager.service.impl.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller // handles incoming requests, calls the Service
public class BudgetController {

    private final ExpenseServiceImpl expenseService;

    @Autowired
    public BudgetController(ExpenseServiceImpl expenseService) {
        this.expenseService = expenseService;
    }
}
