package org.example.budgetmanager.service;

import org.example.budgetmanager.repository.DTO.UserLoginDTO;
import org.example.budgetmanager.repository.DTO.UserRegistrationDTO;

public interface AuthService {
    public void registerUser(UserRegistrationDTO user);
    public String loginUser(UserLoginDTO user);
}
