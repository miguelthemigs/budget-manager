package org.example.budgetmanager.service;

import org.example.budgetmanager.repository.DTO.UserLoginDTO;
import org.example.budgetmanager.repository.DTO.UserRegistrationDTO;

public interface AuthService {
    void registerUser(UserRegistrationDTO user);
    String loginUser(UserLoginDTO user);
    void checkIfUserIsOwnerOrAdmin(Long targetUserId);
}
