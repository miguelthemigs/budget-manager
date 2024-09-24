package org.example.budgetmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import  jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {
    @NotNull(message = "ID cannot be null")
    private Long id;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Email cannot be null")
    private String email;
    @JsonIgnore
    private String password;


}
