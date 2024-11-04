package org.example.budgetmanager.service.impl;
import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.UserCategoryBudget;
import org.example.budgetmanager.repository.UserCategoryBudgetRepository;
import org.example.budgetmanager.repository.entity.UserCategoryBudgetEntity;
import org.example.budgetmanager.service.UserCategoryBudgetService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserCategoryBudgetServiceImpl implements UserCategoryBudgetService {

    private final UserCategoryBudgetRepository userCategoryBudgetRepository;

    public UserCategoryBudgetServiceImpl(UserCategoryBudgetRepository userCategoryBudgetRepository) {
        this.userCategoryBudgetRepository = userCategoryBudgetRepository;
    }

    // Add new category budget for a user
    public void addUserCategoryBudget(UserCategoryBudget userCategoryBudget) {
        UserCategoryBudgetEntity budgetEntity = toEntity(userCategoryBudget);

        userCategoryBudgetRepository.save(budgetEntity);  // Persist the category budget entity
    }

    // Get category budgets for a user using userId directly
    public Optional<List<UserCategoryBudget>> getCategoryBudgetsForUser(Long userId) {
        // Retrieve category budgets for the user using the correct repository method
        List<UserCategoryBudgetEntity> budgetEntities = userCategoryBudgetRepository.findByUserId(userId);

        // Map each UserCategoryBudgetEntity to UserCategoryBudget
        List<UserCategoryBudget> budgets = budgetEntities.stream()
                .map(this::toModel) // Correct mapping to UserCategoryBudget
                .collect(Collectors.toList());

        return Optional.of(budgets);
    }


    // Get a specific category budget by ID
    public UserCategoryBudget getCategoryBudgetById(Long budgetId) {
        // Find the category budget by ID and convert it to the model
        return userCategoryBudgetRepository.findById(budgetId)
                .map(this::toModel)
                .orElseThrow(() -> new IllegalArgumentException("Category budget with ID " + budgetId + " not found"));
    }

    // Update existing category budget
    public void updateUserCategoryBudget(UserCategoryBudget userCategoryBudget) {
        if (userCategoryBudget == null || userCategoryBudget.getId() == null) {
            throw new IllegalArgumentException("Category budget and ID cannot be null");
        }

        if (!userCategoryBudgetRepository.existsById(userCategoryBudget.getId())) {
            throw new IllegalArgumentException("Category budget with ID " + userCategoryBudget.getId() + " does not exist");
        }

        UserCategoryBudgetEntity budgetEntity = toEntity(userCategoryBudget);
        userCategoryBudgetRepository.save(budgetEntity);
    }

    // Delete a category budget by ID
    public void deleteUserCategoryBudget(Long budgetId) {
        if (!userCategoryBudgetRepository.existsById(budgetId)) {
            throw new IllegalArgumentException("Category budget with ID " + budgetId + " does not exist");
        }
        userCategoryBudgetRepository.deleteById(budgetId);
    }

    // Helper method to map model to entity
    private UserCategoryBudgetEntity toEntity(UserCategoryBudget budget) {
        return UserCategoryBudgetEntity.builder()
                .id(budget.getId())
                .userId(budget.getUser_id())  // Changed from user to userId
                .category(budget.getCategory())
                .budget_amount(budget.getBudget_amount())
                .build();
    }

    // Helper method to map entity to model
    private UserCategoryBudget toModel(UserCategoryBudgetEntity entity) {
        return UserCategoryBudget.builder()
                .id(entity.getId())
                .user_id(entity.getUserId())  // Changed from user to userId
                .category(entity.getCategory())
                .budget_amount(entity.getBudget_amount())
                .build();
    }
}
