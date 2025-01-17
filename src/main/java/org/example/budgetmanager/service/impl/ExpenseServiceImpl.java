package org.example.budgetmanager.service.impl;

import lombok.AllArgsConstructor;
import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.repository.ExpensesRepository;
import org.example.budgetmanager.repository.UserRepository;
import org.example.budgetmanager.repository.entity.ExpensesEntity;
import org.example.budgetmanager.repository.entity.UserEntity;
import org.example.budgetmanager.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
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

        // Retrieve the total from the repository
        Optional<Double> totalAmount = expensesRepository.getTotalValueOfExpensesForSelectedMonth(userId, startDate, endDate);

        // If no value is found, return 0.0
        return totalAmount.isPresent() ? totalAmount : Optional.of(0.0);
    }


    public List<Expense> getExpensesForUserAndMonth(Long userId, int month, int year) {
        // Calculate start and end date for the given month and year
        LocalDate startDate = LocalDate.of(year, month, 1); // First day of the month
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth()); // Last day of the month

        // Fetch expenses from the repository for the given user and date range
        return expensesRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public double getTotalSpentForCategoryBudget(Long userId, String category, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        return expensesRepository.findByUserIdAndCategoryAndDateBetween(userId, Category.valueOf(category), startDate, endDate)
                .stream()
                .mapToDouble(ExpensesEntity::getAmount)
                .sum();
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
