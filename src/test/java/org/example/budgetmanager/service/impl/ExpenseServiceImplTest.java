package org.example.budgetmanager.service.impl;

import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.ExpensesRepository;
import org.example.budgetmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExpenseServiceImplTest {

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Mock
    private ExpensesRepository expensesRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addExpense() {
        // Arrange
        Expense expense = new Expense(1L, "Food", "Lunch", 15.50, "2024-09-24", 1L);

        // Act
        expenseService.addExpense(expense);

        // Assert
        verify(expensesRepository, times(1)).addExpense(expense);
    }

    @Test
    void getExpensesForUser_UserExistsWithExpenses() {
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john@example.com", "password");
        Expense expense1 = new Expense(1L, "Food", "Lunch", 15.50, "2024-09-24", userId);
        Expense expense2 = new Expense(2L, "Transport", "Bus Ticket", 2.50, "2024-09-24", userId);
        List<Expense> expenses = List.of(expense1, expense2);

        when(userRepository.findById(userId)).thenReturn(user);
        when(expensesRepository.getExpensesForUser(userId)).thenReturn(expenses);

        Optional<List<Expense>> result = expenseService.getExpensesForUser(userId);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().size());
        assertEquals(expense1.getUserId(), result.get().get(0).getUserId());
        assertEquals(expense1.getId(), result.get().get(0).getId());
        verify(userRepository, times(1)).findById(userId);
        verify(expensesRepository, times(1)).getExpensesForUser(userId);
    }

    @Test
    void getExpensesForUser_UserExistsNoExpenses() {
        Long userId = 2L;
        User user = new User(userId, "Jane Doe", "jane@example.com", "password");

        when(userRepository.findById(userId)).thenReturn(user);
        when(expensesRepository.getExpensesForUser(userId)).thenReturn(null);

        Optional<List<Expense>> result = expenseService.getExpensesForUser(userId);

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findById(userId);
        verify(expensesRepository, times(1)).getExpensesForUser(userId);
    }

    @Test
    void getExpensesForUser_UserDoesNotExist() {
        Long userId = 3L;
        when(userRepository.findById(userId)).thenReturn(null);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.getExpensesForUser(userId);
        });
        assertEquals("User with ID " + userId + " does not exist", thrown.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(expensesRepository, never()).getExpensesForUser(any());
    }

    @Test
    void getExpenseById_ExpenseExists() {
        Long expenseId = 1L;
        Expense expense = new Expense(expenseId, "Food", "Lunch", 15.50, "2024-09-24", 1L);

        when(expensesRepository.findExpenseById(expenseId)).thenReturn(Optional.of(expense));

        Expense result = expenseService.getExpenseById(expenseId);

        assertNotNull(result);
        assertEquals(expenseId, result.getId());
        verify(expensesRepository, times(1)).findExpenseById(expenseId);
    }

    @Test
    void getExpenseById_ExpenseDoesNotExist() {
        // Arrange
        Long expenseId = 2L;

        when(expensesRepository.findExpenseById(expenseId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.getExpenseById(expenseId);
        });
        assertEquals("Expense with ID " + expenseId + " not found", thrown.getMessage());
        verify(expensesRepository, times(1)).findExpenseById(expenseId);
    }
}
