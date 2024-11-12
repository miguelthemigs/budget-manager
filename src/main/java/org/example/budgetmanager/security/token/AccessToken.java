package org.example.budgetmanager.security.token;

import org.example.budgetmanager.model.Role;

import java.util.Set;

public interface AccessToken {
    String getEmail();
    Role getRole();
    Long getUserId();
}
