package org.example.budgetmanager.service.impl;

import lombok.AllArgsConstructor;
import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.repository.ExpensesRepository;
import org.example.budgetmanager.repository.UserRepository;
import org.example.budgetmanager.repository.entity.ExpensesEntity;
import org.example.budgetmanager.repository.entity.UserEntity;
import org.example.budgetmanager.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpensesRepository expensesRepository;
    private final UserRepository userRepository;

    public void addExpense(Expense expense) {
        ExpensesEntity expenseEntity = toEntity(expense);
        expensesRepository.save(expenseEntity);  // Persist the expense entity
    }

    public Optional<List<Expense>> getExpensesForUser(Long userId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if (userEntityOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }

        // Retrieve expenses for the user and map them to the Expense model
        return Optional.of(
                expensesRepository.findByUserId(userId).stream()
                        .map(this::toModel)
                        .collect(Collectors.toList())
        );
    }

    public Expense getExpenseById(Long expenseId) {
        // Find the expense by ID and convert it to the model
        return expensesRepository.findById(expenseId)
                .map(this::toModel)
                .orElseThrow(() -> new IllegalArgumentException("Expense with ID " + expenseId + " not found"));
    }

    public void deleteExpense(Long expenseId) {
        if (!expensesRepository.existsById(expenseId)) {
            throw new IllegalArgumentException("Expense with ID " + expenseId + " does not exist");
        }
        expensesRepository.deleteById(expenseId);
    }

    public void updateExpense(Expense expense) {
        if (expense == null || expense.getId() == null) {
            throw new IllegalArgumentException("Expense and ID cannot be null");
        }

        if (!expensesRepository.existsById(expense.getId())) {
            throw new IllegalArgumentException("Expense with ID " + expense.getId() + " does not exist");
        }

        ExpensesEntity expenseEntity = toEntity(expense);
        expensesRepository.save(expenseEntity);
    }

    public Optional<Double> getTotalValueOfExpensesForSelectedMonth(Long userId, String month) {
        YearMonth yearMonth = YearMonth.parse(month);
        LocalDate startDate = yearMonth.atDay(1); // Get the first day of the month
        LocalDate endDate = yearMonth.atEndOfMonth(); // Get the last day of the month

        // Retrieve expenses for the user within the date range and sum their amounts
        double totalAmount = expensesRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
                .stream()
                .mapToDouble(ExpensesEntity::getAmount) // Assuming getAmount() returns the expense amount
                .sum();

        return Optional.of(totalAmount); // Wrap the total amount in an Optional
    }


    private ExpensesEntity toEntity(Expense expense) {
        return ExpensesEntity.builder()
                .id(expense.getId())
                .category(expense.getCategory())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .userId(expense.getUserId())
                .build();
    }

    private Expense toModel(ExpensesEntity expensesEntity) {
        return Expense.builder()
                .id(expensesEntity.getId())
                .category(expensesEntity.getCategory())
                .description(expensesEntity.getDescription())
                .amount(expensesEntity.getAmount())
                .date(expensesEntity.getDate())
                .userId(expensesEntity.getUserId())
                .build();
    }

}
