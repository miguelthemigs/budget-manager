package org.example.budgetmanager.service.impl;

import lombok.AllArgsConstructor;
import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.repository.ExpensesRepository;
import org.example.budgetmanager.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // contains the business logic, interacts with the Repository.
public class ExpenseServiceImpl implements ExpenseService {


    private final ExpensesRepository expensesRepository;

    @Autowired // doing dependency injection
        public ExpenseServiceImpl(ExpensesRepository expensesRepository) {
            this.expensesRepository = expensesRepository;
        }


    @Override
    public void addExpense(Expense expense) {
        expensesRepository.addExpense(expense);
    }

    @Override
    public List<Expense> getExpensesForUser(Long userId) {
        List<Expense> expenses = expensesRepository.getExpensesForUser(userId);

        if (expenses.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
        return expenses;
    }
}
