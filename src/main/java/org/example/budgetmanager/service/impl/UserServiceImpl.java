package org.example.budgetmanager.service.impl;

import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.UserRepository;
import org.example.budgetmanager.repository.impl.UserRepositoryImpl;
import org.example.budgetmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepositoryImpl UserRepositoryImpl;
    @Autowired
    public UserServiceImpl(UserRepositoryImpl userRepository) {
        this.UserRepositoryImpl = userRepository;  // Inject UserRepository, not Long
    }

    public User findById(Long id) {
        return UserRepositoryImpl.findById(id);
    }
}
