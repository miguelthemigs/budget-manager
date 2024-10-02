package org.example.budgetmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import  jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
//@Entity  // Mark this class as a JPA entity
//@Table(name = "expenses")
public class User {
    @NotNull(message = "ID cannot be null")
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate sequential IDs (1, 2, 3, ...)
    private Long id;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Email cannot be null")
    private String email;
    @JsonIgnore
    private String password;
    @Builder.Default
    private double balance = 0.0;
    private Currency preferredCurrency;
    private double monthlyBudget;
    private Map<Category, Double> categoryBudgets;

}
