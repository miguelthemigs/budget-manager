package org.example.budgetmanager.service.impl;

import lombok.AllArgsConstructor;
import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.ExpensesRepository;
import org.example.budgetmanager.repository.UserRepository;
import org.example.budgetmanager.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // contains the business logic, interacts with the Repository.
public class ExpenseServiceImpl implements ExpenseService {


    private final ExpensesRepository expensesRepository;
    private final UserRepository userRepository;

        @Autowired // doing dependency injection
        public ExpenseServiceImpl(ExpensesRepository expensesRepository, UserRepository userRepository) {
            this.expensesRepository = expensesRepository;
            this.userRepository = userRepository;
        }

    public void addExpense(Expense expense) {
        expensesRepository.addExpense(expense);
    }

    public Optional<List<Expense>> getExpensesForUser(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist");
        }
        return Optional.ofNullable(expensesRepository.getExpensesForUser(userId));
    }

    public Expense getExpenseById(Long expenseId) {
        return expensesRepository.findExpenseById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense with ID " + expenseId + " not found"));
    }

    public void deleteExpense(Long expenseId) {
        expensesRepository.deleteExpense(expenseId);
    }

    public void updateExpense(Expense expense) {
        if (expense == null) {
            throw new IllegalArgumentException("Expense cannot be null");
        }
        else if (expense.getId() == null) {
            throw new IllegalArgumentException("Expense ID cannot be null");
        }
        if (expense.getCategory() == null || expense.getCategory().isEmpty()) {
            throw new IllegalArgumentException("Expense category cannot be null or empty");
        }
        if (expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Expense amount must be greater than zero");
        }
        expensesRepository.updateExpense(expense);
    }
}
