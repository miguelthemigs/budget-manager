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
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @JsonIgnore
    private String password;


}
