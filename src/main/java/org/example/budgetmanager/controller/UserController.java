package org.example.budgetmanager.controller;

import jakarta.validation.Valid;
import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(User.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .monthlyBudget(user.getMonthlyBudget())
                    .preferredCurrency(user.getPreferredCurrency())
                    .balance(user.getBalance())
                    .categoryBudgets(user.getCategoryBudgets())
                    .build());
        } else {
            return ResponseEntity.notFound().build();  // Return 404 if user is not found
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable("id") Long id, @RequestBody User user) {
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

    @PatchMapping("/{id}/categoryBudget")
    // ex: http://localhost:8080/user/1/categoryBudget?budget=125&category=RESTAURANTS to set the budget for category FOOD for user with id 1 to 1200
    public ResponseEntity<User> setCategoryBudget(@PathVariable("id") Long id, @RequestParam("budget") double budget, @RequestParam("category") String category) {
        userService.setCategoryBudget(id, budget, Category.valueOf(category));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
