package org.example.budgetmanager.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.User;

@Entity
@Table(name = "user_category_budgets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCategoryBudgetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_entity_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Double budgetAmount;

}

