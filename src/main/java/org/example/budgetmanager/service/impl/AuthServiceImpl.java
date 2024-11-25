package org.example.budgetmanager.service.impl;

import lombok.AllArgsConstructor;
import org.example.budgetmanager.model.Role;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.DTO.UserLoginDTO;
import org.example.budgetmanager.repository.DTO.UserRegistrationDTO;
import org.example.budgetmanager.security.token.AccessTokenEncoder;
import org.example.budgetmanager.security.token.impl.AccessTokenImpl;
import org.example.budgetmanager.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;
    private AccessTokenEncoder jwtTokenProvider;

    public void registerUser(UserRegistrationDTO user) {
        if (!user.getPassword().equals(user.getRepeatedPassword())) {
            throw new RuntimeException("Passwords do not match!");
        }
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        userService.addUser(User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .build());
    }

    public String loginUser(UserLoginDTO user) {
        User userEntity = userService.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }
        return jwtTokenProvider.encode(new AccessTokenImpl(userEntity.getEmail(), userEntity.getId(), userEntity.getRole()));
    }

}
