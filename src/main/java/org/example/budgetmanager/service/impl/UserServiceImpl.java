package org.example.budgetmanager.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.UserRepository;
import org.example.budgetmanager.repository.entity.UserEntity;
import org.example.budgetmanager.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private User toModel(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .balance(userEntity.getBalance())
                .preferredCurrency(userEntity.getPreferredCurrency())
                .monthlyBudget(userEntity.getMonthlyBudget())
                .role(userEntity.getRole()) // Assuming you have a role in UserEntity as well
                .build();
    }

    private UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .balance(user.getBalance())
                .preferredCurrency(user.getPreferredCurrency())
                .monthlyBudget(user.getMonthlyBudget())
                .role(user.getRole()) // Assuming you have a role in User as well
                .build();
    }


    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(this::toModel);
    }

    public void addUser(User user) {
        UserEntity userEntity = toEntity(user);
        userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void editUser(@Valid User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        } else if (user.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        userRepository.save(toEntity(user));
    }

    public void defineMonthlyBudget(Long id, double budget) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userEntity.setMonthlyBudget(budget);
        userRepository.save(userEntity);
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(this::toModel);
    }

    public List<User> findAll() {
        return userRepository.findAll().stream().map(this::toModel).collect(Collectors.toList());
    }
}
