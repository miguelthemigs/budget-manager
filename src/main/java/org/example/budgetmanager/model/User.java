package org.example.budgetmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class User {

    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Email cannot be null")
    private String email;

    private String password;

    @Builder.Default
    private double balance = 0.0;

    @Enumerated(EnumType.STRING)
    private Currency preferredCurrency;

    private double monthlyBudget;

    @Enumerated(EnumType.STRING)
    private Role role;


}
