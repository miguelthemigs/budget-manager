package org.example.budgetmanager.repository.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UserLoginDTO {
    private String email;
    private String password;

}
