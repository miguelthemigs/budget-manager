package org.example.budgetmanager.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.UserRepository;
import org.example.budgetmanager.repository.entity.UserEntity;

import org.example.budgetmanager.repository.impl.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl{

    private final UserRepository userRepository;
    private final UserRepositoryImpl userRepositoryImpl;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRepositoryImpl userRepositoryImpl) {
        this.userRepository = userRepository;
        this.userRepositoryImpl = userRepositoryImpl;
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    public void addUser(UserEntity user) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void editUser(@Valid UserEntity user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
       else if (user.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        userRepository.save(user);
}



    public void defineMonthlyBudget(Long id, double budget) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setMonthlyBudget(budget);
        userRepository.save(user);
    }
//
//    public void setCategoryBudget(Long id, double budget, Category category) {
//        userRepositoryImpl.setCategoryBudget(id, budget, category);
//    }
}
