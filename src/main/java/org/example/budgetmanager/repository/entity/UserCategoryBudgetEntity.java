package org.example.budgetmanager.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.User;

@Entity
@Table(name = "user_category_budgets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCategoryBudgetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;  // Replacing the 'User' entity with just 'userId'

    @Enumerated(EnumType.STRING)
    private Category category;

    private Double budget_amount;
}
