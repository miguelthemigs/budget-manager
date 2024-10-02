package org.example.budgetmanager.model;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import  jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
@Entity  // Mark this class as a JPA entity
@Table(name = "users")  // Map this entity to the "users" table in the database
public class User {

    @NotNull(message = "ID cannot be null")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate sequential IDs (1, 2, 3, ...)
    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Email cannot be null")
    private String email;

    @JsonIgnore
    private String password;

    @Builder.Default
    private double balance = 0.0;

    @Enumerated(EnumType.STRING)
    private Currency preferredCurrency;

    private double monthlyBudget;

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "category_budgets")
    private Map<Category, Double> categoryBudgets = new HashMap<>();

    @Enumerated(EnumType.STRING)
    private Role role;



}
