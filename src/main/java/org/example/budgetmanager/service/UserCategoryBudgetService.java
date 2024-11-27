package org.example.budgetmanager.service;
import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.UserCategoryBudget;
import java.util.List;
import java.util.Optional;

public interface UserCategoryBudgetService {
    void addUserCategoryBudget(UserCategoryBudget userCategoryBudget);
    Optional<List<UserCategoryBudget>> getCategoryBudgetsForUser(Long userId);
    UserCategoryBudget getCategoryBudgetById(Long budgetId);
    void updateUserCategoryBudget(UserCategoryBudget userCategoryBudget);
    void deleteUserCategoryBudget(Long budgetId);


}
