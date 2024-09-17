package org.example.budgetmanager.model;

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
    private String password;


}
