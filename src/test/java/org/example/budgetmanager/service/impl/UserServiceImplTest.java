package org.example.budgetmanager.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.*;
import org.example.budgetmanager.model.Currency;
import org.example.budgetmanager.model.Role;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.UserRepository;
import org.example.budgetmanager.repository.entity.UserEntity;
import org.example.budgetmanager.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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
    void findById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        Optional<User> foundUser = userService.findById(1L);
        assertTrue(foundUser.isPresent());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void addUser() {
        userService.addUser(user);

        //  This is the method being checked on the userRepository mock.
        //  It simulates the behavior of saving a UserEntity to the database.
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void deleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        doNothing().when(authService).checkIfUserIsOwnerOrAdmin(userEntity.getId());
        userService.deleteUser(1L);
        verify(authService, times(1)).checkIfUserIsOwnerOrAdmin(userEntity.getId());
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testEditUserSuccess() {
        user.setId(1L);

        doNothing().when(authService).checkIfUserIsOwnerOrAdmin(user.getId());
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        userService.editUser(user);
        verify(authService, times(1)).checkIfUserIsOwnerOrAdmin(userEntity.getId());
        verify(userRepository).save(any(UserEntity.class)); // Verify that save was called
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
        User userWithoutId = User.builder().build(); // No ID

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.editUser(userWithoutId);
        });
        assertEquals("User ID cannot be null", exception.getMessage());
    }

    @Test
    public void testDefineMonthlyBudgetSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        userService.defineMonthlyBudget(1L, 2000.0);

        verify(userRepository).save(any(UserEntity.class));
        assertEquals(2000.0, userEntity.getMonthlyBudget());
    }

    @Test
    public void testDefineMonthlyBudgetUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.defineMonthlyBudget(1L, 2000.0);
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void findByEmail_existingEmail_returnsUser() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(userEntity));

        Optional<User> foundUser = userService.findByEmail("john@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals(Optional.of(user), foundUser);
        verify(userRepository, times(1)).findByEmail("john@example.com");
    }

    @Test
    void findByEmail_nonExistingEmail_returnsEmptyOptional() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findByEmail("nonexistent@example.com");

        assertFalse(foundUser.isPresent());
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(List.of(userEntity));
        assertEquals(List.of(user), userService.findAll());
    }






}
