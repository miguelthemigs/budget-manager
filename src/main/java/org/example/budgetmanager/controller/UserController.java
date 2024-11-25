package org.example.budgetmanager.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.service.UserService;
import org.example.budgetmanager.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok) // Return the User object if present
                .orElseGet(() -> ResponseEntity.notFound().build()); // Return 404 if user is not found
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user); // Return the created user
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build(); // Return 200 OK
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editUser(@PathVariable("id") Long id, @RequestBody User user) {
        user.setId(id); // Set the ID from the path variable
        userService.editUser(user); // Pass User directly, service handles conversion
        return ResponseEntity.ok().build(); // Return 200 OK
    }

    // ex: http://localhost:8080/user/10/monthlyBudget?budget=1000
    @PatchMapping("/{id}/monthlyBudget=1000")
    public ResponseEntity<Void> defineMonthlyBudget(@PathVariable("id") Long id, @RequestParam("budget") double budget) {
        userService.defineMonthlyBudget(id, budget);
        return ResponseEntity.ok().build(); // Return 200 OK
    }

    @RolesAllowed({"ADMIN"})
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

}
