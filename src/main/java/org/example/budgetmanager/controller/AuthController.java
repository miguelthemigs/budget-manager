package org.example.budgetmanager.controller;

import org.example.budgetmanager.repository.DTO.UserLoginDTO;
import org.example.budgetmanager.repository.DTO.UserRegistrationDTO;
import org.example.budgetmanager.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(UserRegistrationDTO user){
        try {
            authService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(UserLoginDTO user){
        try {
            String token = authService.loginUser(user);
            return ResponseEntity.ok("{\"accessToken\": \"" + token + "\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
