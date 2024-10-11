package org.example.budgetmanager.repository.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.budgetmanager.model.Currency;
import org.example.budgetmanager.model.Role;


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

    @Enumerated(EnumType.STRING)
    private Currency preferredCurrency;

    private double monthlyBudget;

    @Enumerated(EnumType.STRING)
    private Role role;

}

