package org.example.budgetmanager.service.impl;

import jakarta.validation.*;
import org.example.budgetmanager.model.Currency;
import org.example.budgetmanager.model.Role;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// verify() checks that a method was called on a mock and with what arguments.
// assert checks that the output or state matches the expected value.

class UserServiceImplTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);

        // Create a sample user object for testing
        user = new User(1L, "John Doe", "john@example.com", "password123", 500.0, Currency.AUD, 1000.0, null, Role.USER);
    }

    @Test
    void findById() {
        // Given
        when(userRepository.findById(1L)).thenReturn(user);

        // When
        User foundUser = userService.findById(1L);

        // Then
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getName(), foundUser.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void addUser() {
        // When
        userService.addUser(user);

        // Then
        verify(userRepository, times(1)).addUser(user);
    }

    @Test
    void deleteUser() {
        // When
        userService.deleteUser(1L);

        // Then
        verify(userRepository, times(1)).deleteUser(1L);
    }

    @Test
    public void testEditUserSuccess() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        userService.editUser(user);
        verify(userRepository).editUser(user);

    }

    @Test
    public void testEditUserNullUser() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.editUser(null);
        });
        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    public void testEditUserNullUserId() {
        User user = new User();
        user.setId(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.editUser(user);
        });
        assertEquals("User ID cannot be null", exception.getMessage());
    }

    @Test
    public void testEditUserNullUserIdValidation() {
        User user = new User();
        user.setId(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.editUser(user);
        });
        assertTrue(exception.getMessage().contains("ID cannot be null"));
    }



}
