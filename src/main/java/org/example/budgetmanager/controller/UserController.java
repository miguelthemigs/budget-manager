package org.example.budgetmanager.controller;

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
                    .build());
        } else {
            return ResponseEntity.notFound().build();  // Return 404 if user is not found
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
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
}
