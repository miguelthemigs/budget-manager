//package org.example.budgetmanager.controller;
//
//import org.example.budgetmanager.model.User;
//import org.example.budgetmanager.repository.impl.UserRepositoryImpl;
//import org.example.budgetmanager.service.UserService;
//import org.example.budgetmanager.service.impl.UserServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserControllerTest {
//    private UserServiceImpl userService;
//    private UserRepositoryImpl userRepository;
//
//    @BeforeEach
//    void setUp() {
//        userRepository = new UserRepositoryImpl();
//        userService = new UserServiceImpl(userRepository);
//
//        // Ensure the repository has sample data
//        userRepository.clearData();
//        userRepository.addSampleData();
//    }
//
//    @Test
//    void getUserById() {
//        User user = userService.findById(1L);
//        assertEquals("John Doe", user.getName(), "User with ID 1 should be John Doe");
//    }
//
//    @Test
//    void createUser() {
//        // Test creating a new user
//        User user = User.builder()
//                .id(3L)
//                .name("Cleber")
//                .email("cleber@gmail.com")
//                .password("capivara")
//                .build();
//
//        userService.addUser(user);
//        User cleber = userService.findById(user.getId());
//        assertEquals("Cleber", cleber.getName(), "User with ID 3 should be Cleber");
//    }
//
//    @Test
//    void deleteUser() {
//        // Test deleting an existing user
//        userService.deleteUser(1L);
//        assertNull(userRepository.findById(1L), "User with ID 1 should be deleted");
//
//        // Test deleting a non-existent user
//        userService.deleteUser(3L);
//        assertNull(userRepository.findById(3L), "User with ID 3 should not exist");
//    }
//}