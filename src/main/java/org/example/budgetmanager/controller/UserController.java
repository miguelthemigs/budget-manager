package org.example.budgetmanager.controller;

import org.example.budgetmanager.model.User;
import org.example.budgetmanager.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
                    .build());
        } else {
            return ResponseEntity.notFound().build();  // Return 404 if user is not found
        }
    }
}
