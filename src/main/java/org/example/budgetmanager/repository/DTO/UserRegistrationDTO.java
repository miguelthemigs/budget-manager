package org.example.budgetmanager.repository.DTO;

import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UserRegistrationDTO {
    private String name;
    @Email
    private String email;
    private String password;
    private String repeatedPassword;

}
