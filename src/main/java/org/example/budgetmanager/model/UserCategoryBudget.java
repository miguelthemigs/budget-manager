package org.example.budgetmanager.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCategoryBudget {
    private Long id;

    @NotNull(message = "User cannot be null")
    private Long user_id;

    @NotNull(message = "Category cannot be null")
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull(message = "Budget amount cannot be null")
    private Double budget_amount;
}

