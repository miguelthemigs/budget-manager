package org.example.budgetmanager.security.token.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.budgetmanager.model.Role;
import org.example.budgetmanager.security.token.AccessToken;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final String email;
    private final Long userId;
    private final Role role;

    public AccessTokenImpl(String email, Long userId, Role role) {
        this.email = email;
        this.userId = userId;
        this.role = role;
    }

}
