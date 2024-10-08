package org.example.budgetmanager.controller;

import lombok.AllArgsConstructor;
import org.example.budgetmanager.model.UserCategoryBudget;
import org.example.budgetmanager.service.impl.UserCategoryBudgetServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/category-budgets")
public class UserCategoryBudgetController {

    private final UserCategoryBudgetServiceImpl userCategoryBudgetService;

    public UserCategoryBudgetController(UserCategoryBudgetServiceImpl userCategoryBudgetService) {
        this.userCategoryBudgetService = userCategoryBudgetService;
    }

    @PostMapping("")
    public ResponseEntity<UserCategoryBudget> addUserCategoryBudget(@RequestBody UserCategoryBudget userCategoryBudget) {
        userCategoryBudgetService.addUserCategoryBudget(userCategoryBudget);
        return ResponseEntity.status(201).body(userCategoryBudget); // Created status
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserCategoryBudget>> getUserCategoryBudgets(@PathVariable Long userId) {
        Optional<List<UserCategoryBudget>> userCategoryBudgets = userCategoryBudgetService.getCategoryBudgetsForUser(userId);
        return ResponseEntity.ok(userCategoryBudgets.orElse(Collections.emptyList())); // Return list or empty
    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<UserCategoryBudget> getCategoryBudgetById(@PathVariable Long budgetId) {
        try {
            UserCategoryBudget budget = userCategoryBudgetService.getCategoryBudgetById(budgetId);
            return ResponseEntity.ok(budget);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Not found if ID is invalid
        }
    }

    @PutMapping("/{budgetId}")
    public ResponseEntity<UserCategoryBudget> updateUserCategoryBudget(@PathVariable Long budgetId, @RequestBody UserCategoryBudget userCategoryBudget) {
        try {
            // Ensure the path ID matches the request body ID
            if (!budgetId.equals(userCategoryBudget.getId())) {
                return ResponseEntity.badRequest().build(); // Bad request if IDs don't match
            }
            userCategoryBudgetService.updateUserCategoryBudget(userCategoryBudget);
            return ResponseEntity.ok(userCategoryBudget);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Not found if ID is invalid
        }
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<Void> deleteUserCategoryBudget(@PathVariable Long budgetId) {
        try {
            userCategoryBudgetService.deleteUserCategoryBudget(budgetId);
            return ResponseEntity.noContent().build(); // No content status on success
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Not found if ID is invalid
        }
    }
}
