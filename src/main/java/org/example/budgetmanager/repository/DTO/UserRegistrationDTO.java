package org.example.budgetmanager.repository.DTO;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String name;
    private String email;
    private String password;
    private String repeatedPassword;

}
