package org.example.budgetmanager.service.impl;


import org.example.budgetmanager.model.Role;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.DTO.UserLoginDTO;
import org.example.budgetmanager.repository.DTO.UserRegistrationDTO;
import org.example.budgetmanager.security.token.AccessTokenEncoder;
import org.example.budgetmanager.security.token.impl.AccessTokenImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;
    private UserRegistrationDTO registrationDTO;
    private UserLoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample User
        user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        // Sample registration DTO
        registrationDTO = new UserRegistrationDTO();
        registrationDTO.setName("John Doe");
        registrationDTO.setEmail("john@example.com");
        registrationDTO.setPassword("password123");
        registrationDTO.setRepeatedPassword("password123");

        // Sample login DTO
        loginDTO = new UserLoginDTO();
        loginDTO.setEmail("john@example.com");
        loginDTO.setPassword("password123");
    }

    @Test
    void testRegisterUserSuccess() {
        when(userService.findByEmail(registrationDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registrationDTO.getPassword())).thenReturn("encodedPassword");

        authService.registerUser(registrationDTO);

        verify(userService, times(1)).findByEmail(registrationDTO.getEmail());
        verify(passwordEncoder, times(1)).encode(registrationDTO.getPassword());
        verify(userService, times(1)).addUser(any(User.class));
    }

    @Test
    void testRegisterUserEmailAlreadyExists() {
        when(userService.findByEmail(registrationDTO.getEmail())).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.registerUser(registrationDTO));

        assertEquals("Email already exists!", exception.getMessage());
        verify(userService, times(1)).findByEmail(registrationDTO.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userService, never()).addUser(any(User.class));
    }

    @Test
    void testRegisterUserPasswordsDoNotMatch() {
        registrationDTO.setRepeatedPassword("differentPassword");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.registerUser(registrationDTO));

        assertEquals("Passwords do not match!", exception.getMessage());
        verify(userService, never()).findByEmail(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userService, never()).addUser(any(User.class));
    }

    @Test
    void testLoginUserSuccess() {
        when(userService.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtTokenProvider.encode(any(AccessTokenImpl.class))).thenReturn("mockedJwtToken");

        String token = authService.loginUser(loginDTO);

        assertNotNull(token);
        assertEquals("mockedJwtToken", token);
        verify(userService, times(1)).findByEmail(loginDTO.getEmail());
        verify(passwordEncoder, times(1)).matches(loginDTO.getPassword(), user.getPassword());
        verify(jwtTokenProvider, times(1)).encode(any(AccessTokenImpl.class));
    }

    @Test
    void testLoginUserNotFound() {
        when(userService.findByEmail(loginDTO.getEmail())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.loginUser(loginDTO));

        assertEquals("User not found!", exception.getMessage());
        verify(userService, times(1)).findByEmail(loginDTO.getEmail());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtTokenProvider, never()).encode(any());
    }

    @Test
    void testLoginUserInvalidPassword() {
        when(userService.findByEmail(loginDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.loginUser(loginDTO));

        assertEquals("Invalid password!", exception.getMessage());
        verify(userService, times(1)).findByEmail(loginDTO.getEmail());
        verify(passwordEncoder, times(1)).matches(loginDTO.getPassword(), user.getPassword());
        verify(jwtTokenProvider, never()).encode(any());
    }
}
