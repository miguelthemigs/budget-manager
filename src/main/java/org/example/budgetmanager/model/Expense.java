package org.example.budgetmanager.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Expense {
    @NotNull(message = "ID cannot be null")
    private Long id; // Can be null for new expenses

    @NotNull(message = "Category cannot be null")
    private Category category;

    private String description; // optional

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount; // Use Double to allow null

    @NotNull(message = "Date cannot be null")
    private String date;

    @NotNull(message = "User ID cannot be null")
    private Long userId;





}
