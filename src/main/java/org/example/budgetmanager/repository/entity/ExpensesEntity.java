package org.example.budgetmanager.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.example.budgetmanager.model.Category;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Data
@Entity
@Table(name = "expenses")
public class ExpensesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate sequential IDs (1, 2, 3, ...)
    private Long id;

    @NotNull(message = "Category cannot be null")
    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount; // Use Double to allow null

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

}
