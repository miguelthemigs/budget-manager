package org.example.budgetmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import  jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class User {
    @NotNull(message = "ID cannot be null")
    private Long id;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Email cannot be null")
    private String email;
    @JsonIgnore
    private String password;
    private double balance = 0.0;
    private Currency preferredCurrency;
    private double monthlyBudget;
    private Map<Category, Double> categoryBudgets;

}
