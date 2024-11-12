package org.example.budgetmanager.security.token;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
