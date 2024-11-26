package org.example.budgetmanager.service.impl;


import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.UserCategoryBudget;
import org.example.budgetmanager.repository.UserCategoryBudgetRepository;
import org.example.budgetmanager.repository.entity.UserCategoryBudgetEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class UserCategoryBudgetServiceImplTest {

    @Mock
    private UserCategoryBudgetRepository userCategoryBudgetRepository;

    @InjectMocks
    private UserCategoryBudgetServiceImpl userCategoryBudgetService;

    private UserCategoryBudget userCategoryBudget;
    private UserCategoryBudgetEntity userCategoryBudgetEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample UserCategoryBudget model for testing
        userCategoryBudget = UserCategoryBudget.builder()
                .id(1L)
                .user_id(1L)
                .category(Category.RESTAURANTS) // Assuming Category is an enum or class
                .budget_amount(100.0)
                .build();

        // Sample UserCategoryBudgetEntity for testing
        userCategoryBudgetEntity = UserCategoryBudgetEntity.builder()
                .id(1L)
                .userId(1L)
                .category(Category.RESTAURANTS)
                .budget_amount(100.0)
                .build();
    }

    @Test
    void addUserCategoryBudget_HappyPath() {
        when(userCategoryBudgetRepository.findByUserId(1L)).thenReturn(Collections.emptyList());
        userCategoryBudgetService.addUserCategoryBudget(userCategoryBudget);
        verify(userCategoryBudgetRepository, times(1)).save(any(UserCategoryBudgetEntity.class));
    }

    @Test
    void addUserCategoryBudget_CategoryAlreadyExists() {
        when(userCategoryBudgetRepository.findByUserId(1L)).thenReturn(Collections.singletonList(userCategoryBudgetEntity));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userCategoryBudgetService.addUserCategoryBudget(userCategoryBudget);
        });

        assertEquals("Category budget for RESTAURANTS already exists", exception.getMessage());
        
        verify(userCategoryBudgetRepository, times(0)).save(any(UserCategoryBudgetEntity.class));
    }

    @Test
    void getCategoryBudgetsForUserSuccess() {
        when(userCategoryBudgetRepository.findByUserId(1L)).thenReturn(Collections.singletonList(userCategoryBudgetEntity));

        Optional<List<UserCategoryBudget>> budgets = userCategoryBudgetService.getCategoryBudgetsForUser(1L);

        assertTrue(budgets.isPresent());
        assertEquals(1, budgets.get().size());
        assertEquals(userCategoryBudget, budgets.get().get(0));
    }

    @Test
    void getCategoryBudgetByIdSuccess() {
        when(userCategoryBudgetRepository.findById(1L)).thenReturn(Optional.of(userCategoryBudgetEntity));

        UserCategoryBudget foundBudget = userCategoryBudgetService.getCategoryBudgetById(1L);

        assertNotNull(foundBudget);
        assertEquals(userCategoryBudget.getId(), foundBudget.getId());
        assertEquals(userCategoryBudget, foundBudget);
    }

    @Test
    void getCategoryBudgetByIdNotFound() {
        when(userCategoryBudgetRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userCategoryBudgetService.getCategoryBudgetById(1L);
        });
        assertEquals("Category budget with ID 1 not found", exception.getMessage());
    }

    @Test
    void updateUserCategoryBudgetSuccess() {
        userCategoryBudget.setId(1L);
        when(userCategoryBudgetRepository.existsById(1L)).thenReturn(true);

        userCategoryBudgetService.updateUserCategoryBudget(userCategoryBudget);
        verify(userCategoryBudgetRepository, times(1)).save(any(UserCategoryBudgetEntity.class));
    }

    @Test
    void updateUserCategoryBudgetNullBudget() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userCategoryBudgetService.updateUserCategoryBudget(null);
        });
        assertEquals("Category budget and ID cannot be null", exception.getMessage());
    }

    @Test
    void updateUserCategoryBudgetNullBudgetId() {
        UserCategoryBudget budgetWithoutId = UserCategoryBudget.builder().build(); // No ID

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userCategoryBudgetService.updateUserCategoryBudget(budgetWithoutId);
        });
        assertEquals("Category budget and ID cannot be null", exception.getMessage());
    }

    @Test
    void deleteUserCategoryBudgetSuccess() {
        when(userCategoryBudgetRepository.existsById(1L)).thenReturn(true);
        userCategoryBudgetService.deleteUserCategoryBudget(1L);
        verify(userCategoryBudgetRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUserCategoryBudgetNotFound() {
        when(userCategoryBudgetRepository.existsById(1L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userCategoryBudgetService.deleteUserCategoryBudget(1L);
        });
        assertEquals("Category budget with ID 1 does not exist", exception.getMessage());
    }
}

