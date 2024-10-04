package org.example.budgetmanager.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Data
// @Entity  // Mark this class as a JPA entity if using a database
// @Table(name = "expenses")
public class Expense {
    @NotNull(message = "ID cannot be null")
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate sequential IDs (1, 2, 3, ...)
    private Long id;

    @NotNull(message = "Category cannot be null")
    private Category category;

    private String description; // optional

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount; // Use Double to allow null

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "User ID cannot be null")
    private Long userId;





}
