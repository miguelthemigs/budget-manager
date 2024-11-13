package org.example.budgetmanager.repository.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserRegistrationDTO {
    private String name;
    private String email;
    private String password;
    private String repeatedPassword;

}
