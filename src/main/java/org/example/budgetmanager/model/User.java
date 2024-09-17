package org.example.budgetmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String email;
    @JsonIgnore
    private String password;


}
