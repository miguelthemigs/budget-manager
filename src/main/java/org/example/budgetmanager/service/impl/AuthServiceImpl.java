package org.example.budgetmanager.service.impl;

import lombok.AllArgsConstructor;
import org.example.budgetmanager.model.Role;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.DTO.UserLoginDTO;
import org.example.budgetmanager.repository.DTO.UserRegistrationDTO;
import org.example.budgetmanager.security.token.AccessTokenEncoder;
import org.example.budgetmanager.security.token.impl.AccessTokenImpl;
import org.example.budgetmanager.service.AuthService;
import org.example.budgetmanager.service.UserService;
import org.example.budgetmanager.service.exceptions.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserService userService;
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

    public void checkIfUserIsOwnerOrAdmin(Long targetUserId) {
        if (targetUserId == null) {
            throw new IllegalArgumentException("Target user ID cannot be null");
        }

        // Retrieve the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();  // Using the email as the identifier

        // Fetch the current user's ID by their email
        Long currentUserId = userService.findByEmail(currentEmail)
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // Check if the user is an admin
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        // Check if the current user is the owner of the target or an admin
        if (!targetUserId.equals(currentUserId) && !isAdmin) {
            throw new UnauthorizedException("You are not authorized to perform this action");
        }
    }


}
