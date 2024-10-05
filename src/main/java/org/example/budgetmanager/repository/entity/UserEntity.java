package org.example.budgetmanager.repository.entity;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import  jakarta.validation.constraints.NotNull;
import org.example.budgetmanager.model.Category;
import org.example.budgetmanager.model.Currency;
import org.example.budgetmanager.model.Role;
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
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @JsonIgnore
    private String password;

    @Builder.Default
    private double balance = 0.0;

    private Currency preferredCurrency;

    private double monthlyBudget;
    private Role role;

}

