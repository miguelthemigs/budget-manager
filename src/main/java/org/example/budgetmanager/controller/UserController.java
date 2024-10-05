package org.example.budgetmanager.controller;

import jakarta.validation.Valid;
import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.entity.UserEntity;
import org.example.budgetmanager.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") Long id) {
        Optional<UserEntity> user = userService.findById(id);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            return ResponseEntity.ok(UserEntity.builder()
                    .id(userEntity.getId())
                    .email(userEntity.getEmail())
                    .name(userEntity.getName())
                    .monthlyBudget(userEntity.getMonthlyBudget())
                    .preferredCurrency(userEntity.getPreferredCurrency())
                    .balance(userEntity.getBalance())
                    .role(userEntity.getRole())
                    .build());
        } else {
            return ResponseEntity.notFound().build();  // Return 404 if user is not found
        }
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserEntity user) {
        userService.addUser(user); // This should save the user entity
        return ResponseEntity.status(HttpStatus.CREATED).body(user); // Return the created user
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> editUser(@PathVariable("id") Long id, @RequestBody UserEntity user) {
        user.setId(id);
        userService.editUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/monthlyBudget")
    // ex: http://localhost:8080/user/1/monthlyBudget?budget=1200 to set the monthly budget for user with id 1 to 1200
    public ResponseEntity<User> defineMonthlyBudget(@PathVariable("id") Long id, @RequestParam("budget") double budget) {
        userService.defineMonthlyBudget(id, budget);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @PatchMapping("/{id}/categoryBudget")
//    // ex: http://localhost:8080/user/1/categoryBudget?budget=125&category=RESTAURANTS to set the budget for category FOOD for user with id 1 to 1200
//    public ResponseEntity<User> setCategoryBudget(@PathVariable("id") Long id, @RequestParam("budget") double budget, @RequestParam("category") String category) {
//       // userService.setCategoryBudget(id, budget, Category.valueOf(category));
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
}
