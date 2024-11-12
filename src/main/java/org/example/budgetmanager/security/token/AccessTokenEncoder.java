package org.example.budgetmanager.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
