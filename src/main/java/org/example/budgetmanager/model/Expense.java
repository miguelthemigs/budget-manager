package org.example.budgetmanager.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Data
public class Expense {

    private Long id;

    @NotNull(message = "Category cannot be null")
    private Category category;

    private String description; // Optional field

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount; // Using Double to allow null

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "User ID cannot be null")
    private Long userId; // Relating to the user who created the expense
}
