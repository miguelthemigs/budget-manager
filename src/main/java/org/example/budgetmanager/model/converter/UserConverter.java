package org.example.budgetmanager.model.converter;

import org.example.budgetmanager.model.User;
import org.example.budgetmanager.repository.entity.UserEntity;

public class UserConverter {

    // Convert User to UserEntity
    public static UserEntity toUserEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setBalance(user.getBalance());
        userEntity.setPreferredCurrency(user.getPreferredCurrency());
        userEntity.setMonthlyBudget(user.getMonthlyBudget());
        userEntity.setRole(user.getRole());

        return userEntity;
    }

    // Convert UserEntity to User
    public static User toUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        User user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        user.setBalance(userEntity.getBalance());
        user.setPreferredCurrency(userEntity.getPreferredCurrency());
        user.setMonthlyBudget(userEntity.getMonthlyBudget());
        user.setRole(userEntity.getRole());

        return user;
    }
}

