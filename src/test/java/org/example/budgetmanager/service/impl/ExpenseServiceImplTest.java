package org.example.budgetmanager.service.impl;


import org.example.budgetmanager.model.*;
import org.example.budgetmanager.repository.ExpensesRepository;
import org.example.budgetmanager.repository.UserRepository;
import org.example.budgetmanager.repository.entity.ExpensesEntity;
import org.example.budgetmanager.repository.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.time.YearMonth;
import java.util.List;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class ExpenseServiceImplTest {

    @Mock
    private ExpensesRepository expensesRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private Expense expense;
    private ExpensesEntity expensesEntity;
    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample Expense model for testing
        expense = Expense.builder()
                .id(1L)
                .description("Grocery shopping")
                .amount(100.0)
                .userId(1L)
                .date(LocalDate.of(2021, 10, 1))
                .build();

        // Sample ExpensesEntity for testing
        expensesEntity = ExpensesEntity.builder()
                .id(1L)
                .description("Grocery shopping")
                .amount(100.0)
                .userId(1L)
                .date(LocalDate.of(2021, 10, 1))
                .build();

        // Sample User model for testing
        user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .password("password123")
                .balance(500.0)
                .preferredCurrency(Currency.AUD)
                .monthlyBudget(1000.0)
                .role(Role.USER)
                .build();

        // Sample UserEntity for testing
        userEntity = UserEntity.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .password("password123")
                .balance(500.0)
                .preferredCurrency(Currency.AUD)
                .monthlyBudget(1000.0)
                .role(Role.USER)
                .build();
    }

    @Test
    void addExpense() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        expenseService.addExpense(expense);

        // Verifying that the save method was called on the expensesRepository.
        verify(expensesRepository, times(1)).save(any(ExpensesEntity.class));
    }

    @Test
    void deleteExpense() {
        when(expensesRepository.existsById(1L)).thenReturn(true);
        expenseService.deleteExpense(1L);
        verify(expensesRepository, times(1)).deleteById(1L);
    }

    @Test
    void findExpenseById() {
        when(expensesRepository.findById(1L)).thenReturn(Optional.of(expensesEntity));
        Expense foundExpense = expenseService.getExpenseById(1L);
        assertNotNull(foundExpense);
        verify(expensesRepository, times(1)).findById(1L);
    }

    @Test
    void testEditExpenseSuccess() {
        expense.setId(1L);
        when(expensesRepository.existsById(1L)).thenReturn(true);
        expenseService.updateExpense(expense);
        verify(expensesRepository).save(any(ExpensesEntity.class));
    }

    @Test
    void testEditExpenseNullExpense() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.updateExpense(null);
        });
        assertEquals("Expense and ID cannot be null", exception.getMessage());
    }

    @Test
    void testEditExpenseNullExpenseId() {
        Expense expenseWithoutId = Expense.builder().build(); // No ID

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.updateExpense(expenseWithoutId);
        });
        assertEquals("Expense and ID cannot be null", exception.getMessage());
    }

    @Test
    void testGetExpensesForUserSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity)); // Mock user retrieval
        when(expensesRepository.findByUserId(1L)).thenReturn(List.of(expensesEntity)); // Mock expense retrieval

        Optional<List<Expense>> expenses = expenseService.getExpensesForUser(user.getId());

        assertTrue(expenses.isPresent());
        assertEquals(expenses, Optional.of(List.of(expense)));
    }


    @Test
    void testGetExpensesForUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.getExpensesForUser(1L);
        });
        assertEquals("User with ID 1 not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(expensesRepository, never()).findByUserId(anyLong());
    }

    @Test
    void getTotalValueOfExpensesForSelectedMonthSuccess() {
        Long userId = 1L;
        String month = "2021-10";
        YearMonth yearMonth = YearMonth.parse(month);
        LocalDate startDate = yearMonth.atDay(1);  // First day of the month
        LocalDate endDate = yearMonth.atEndOfMonth(); // Last day of the month

        // Mock the repository to return a specific total amount
        when(expensesRepository.getTotalValueOfExpensesForSelectedMonth(eq(userId), eq(startDate), eq(endDate)))
                .thenReturn(Optional.of(100.0));

        Optional<Double> totalValue = expenseService.getTotalValueOfExpensesForSelectedMonth(userId, month);

        assertTrue(totalValue.isPresent());
        assertEquals(100.0, totalValue.get());
        verify(expensesRepository, times(1)).getTotalValueOfExpensesForSelectedMonth(eq(userId), eq(startDate), eq(endDate));
    }


    @Test
    void getTotalValueOfExpensesForSelectedMonthNoExpenses() {
        Long userId = 1L;
        String month = "2021-10"; // October 2021
        YearMonth yearMonth = YearMonth.parse(month);
        LocalDate startDate = yearMonth.atDay(1);  // First day of the month
        LocalDate endDate = yearMonth.atEndOfMonth(); // Last day of the month

        // Mock the repository to return Optional.empty() for no expenses
        when(expensesRepository.getTotalValueOfExpensesForSelectedMonth(eq(userId), eq(startDate), eq(endDate)))
                .thenReturn(Optional.empty());

        Optional<Double> totalValue = expenseService.getTotalValueOfExpensesForSelectedMonth(userId, month);

        assertTrue(totalValue.isPresent());
        assertEquals(0.0, totalValue.get());
        verify(expensesRepository, times(1)).getTotalValueOfExpensesForSelectedMonth(eq(userId), eq(startDate), eq(endDate));
    }


    @Test
    void testGetExpensesForUserAndMonth_Success() {
        Long userId = 1L;
        int month = 10; // October
        int year = 2021;
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        when(expensesRepository.findByUserIdAndDateBetween(userId, startDate, endDate))
                .thenReturn(List.of(expensesEntity));

        List<Expense> expenses = expenseService.getExpensesForUserAndMonth(userId, month, year);

        assertNotNull(expenses);
        assertEquals(1, expenses.size());
        assertEquals(expense.getDescription(), expenses.get(0).getDescription());
        assertEquals(expense.getAmount(), expenses.get(0).getAmount());
        verify(expensesRepository, times(1)).findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    @Test
    void testGetTotalSpentForCategoryBudget_Success() {
        Long userId = 1L;
        String category = "RESTAURANTS";
        YearMonth yearMonth = YearMonth.of(2021, 10); // October 2021
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        when(expensesRepository.findByUserIdAndCategoryAndDateBetween(eq(userId), eq(Category.RESTAURANTS), eq(startDate), eq(endDate)))
                .thenReturn(List.of(expensesEntity));

        double totalSpent = expenseService.getTotalSpentForCategoryBudget(userId, category, yearMonth);

        assertEquals(100.0, totalSpent);
        verify(expensesRepository, times(1)).findByUserIdAndCategoryAndDateBetween(eq(userId), eq(Category.RESTAURANTS), eq(startDate), eq(endDate));
    }




}
