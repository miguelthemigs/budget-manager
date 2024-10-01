package org.example.budgetmanager.service.impl;

import jakarta.validation.*;
import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.Currency;
import org.example.budgetmanager.model.Expense;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.ExpensesRepository;
import org.example.budgetmanager.repository.UserRepository;
import org.example.budgetmanager.service.exeptions.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

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

    @Mock
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    // Using "when" is necessary to simulate the behavior of repository methods in unit tests.
    // This ensures we are not calling the actual repository but a mock instead.
    // It isolates the service logic from external dependencies, like databases.
    // In unit tests, we focus on testing only the service, not the repository or database logic.
    // Without "when", the mock repository returns null or default values, causing the test to fail.
    // "when" defines what the mock should return when a specific method is called.
    // This allows us to control and verify the service behavior under test conditions.
    @Test
    void addExpense() {
        Expense expense = new Expense(1L, Category.RESTAURANTS, "Lunch", 15.50, "2024-09-24", 1L);

        expenseService.addExpense(expense);

        verify(expensesRepository, times(1)).addExpense(expense);
    }

    @Test
    void getExpensesForUser_UserExistsWithExpenses() {
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john@example.com", "password", 500.0, Currency.BRL, 1000.0, null);
        Expense expense1 = new Expense(1L, Category.RESTAURANTS, "Lunch", 15.50, "2024-09-24", userId);
        Expense expense2 = new Expense(2L, Category.TRANSPORTATION, "Bus Ticket", 2.50, "2024-09-24", userId);
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
        User user = new User(userId, "Jane Doe", "jane@example.com", "password", 1000.0, Currency.CHF, 2000.0, null);

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
        Expense expense = new Expense(expenseId, Category.RESTAURANTS, "Lunch", 15.50, "2024-09-24", 1L);

        when(expensesRepository.findExpenseById(expenseId)).thenReturn(Optional.of(expense));

        Expense result = expenseService.getExpenseById(expenseId);

        assertNotNull(result);
        assertEquals(expenseId, result.getId());
        verify(expensesRepository, times(1)).findExpenseById(expenseId);
    }

    @Test
    void getExpenseById_ExpenseDoesNotExist() {
        Long expenseId = 2L;

        when(expensesRepository.findExpenseById(expenseId)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.getExpenseById(expenseId);
        });
        assertEquals("Expense with ID " + expenseId + " not found", thrown.getMessage());
        verify(expensesRepository, times(1)).findExpenseById(expenseId);
    }

    @Test
    void addExpense_ValidExpense() {
        Expense expense = new Expense(1L, Category.RESTAURANTS, "Lunch", 15.50, "2024-09-24", 1L);
        expenseService.addExpense(expense);
        verify(expensesRepository, times(1)).addExpense(expense);
        assertEquals(1, expense.getUserId());
        assertEquals(1L, expense.getId());
    }


    @Test
    void addInvalidExpense() { // I need to check if the validators and exceptions are working as expected
        Expense invalidExpense = new Expense(null, null, null, -2.45, null, null); // All fields invalid

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Expense>> violations = validator.validate(invalidExpense);

        // Mock the response from the GlobalExceptionHandler
        Map<String, String> mockErrors = new HashMap<>();
        mockErrors.put("category", "Category cannot be null");
        mockErrors.put("amount", "Amount must be positive");
        mockErrors.put("userId", "User ID cannot be null");

        when(globalExceptionHandler.handleValidationExceptions(any(MethodArgumentNotValidException.class)))
                .thenReturn(ResponseEntity.badRequest().body(mockErrors));

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("expense", "category", "Category cannot be null"),
                new FieldError("expense", "amount", "Amount must be positive"),
                new FieldError("expense", "userId", "User ID cannot be null")
        ));
        when(bindingResult.getGlobalErrors()).thenReturn(Collections.emptyList());

        MethodArgumentNotValidException thrown = assertThrows(MethodArgumentNotValidException.class, () -> {
            if (!violations.isEmpty()) {
                throw new MethodArgumentNotValidException(null, bindingResult);
            }
            expenseService.addExpense(invalidExpense);
        });

        // Use the mocked GlobalExceptionHandler to handle the exception
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(thrown);
        Map<String, String> errors = response.getBody();

        assert errors != null;
        assertTrue(errors.containsKey("category"));
        assertTrue(errors.containsKey("amount"));
        assertTrue(errors.containsKey("userId"));
    }

    @Test
    void deleteExpense() {
        Long expenseId = 1L;
        expenseService.deleteExpense(expenseId);
        verify(expensesRepository, times(1)).deleteExpense(expenseId);
    }

    @Test
    void updateExpense_ValidExpense() {
        Expense expense = new Expense(1L, Category.RESTAURANTS, "Lunch", 15.50, "2024-09-24", 1L);
        expenseService.updateExpense(expense);
        verify(expensesRepository, times(1)).updateExpense(expense);
    }

    @Test
    void updateExpense_NullExpense() {
        Expense nullExpense = null;

        assertThrows(IllegalArgumentException.class, () -> {
            expenseService.updateExpense(nullExpense);
        });

        // No repository interaction should happen
        verify(expensesRepository, never()).updateExpense(nullExpense);
    }

    @Test
    void updateExpense_MissingExpenseId() {
        Expense expenseWithoutId = new Expense(null, Category.RESTAURANTS, "Lunch", 15.50, "2024-09-24", 1L);
        assertThrows(IllegalArgumentException.class, () -> {
            expenseService.updateExpense(expenseWithoutId);
        });
        verify(expensesRepository, never()).updateExpense(expenseWithoutId);
    }

    @Test
    void updateExpense_MissingExpenseFields() {
        Expense invalidExpense = new Expense(1L, null, null, 0.1, "2024-09-24", 1L);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            expenseService.updateExpense(invalidExpense);
        });
        assertEquals("Expense category cannot be null", exception.getMessage());
        verify(expensesRepository, never()).updateExpense(any());
    }

}



