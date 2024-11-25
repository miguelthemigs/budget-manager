package org.example.budgetmanager.security.token;

import org.springframework.stereotype.Component;

@Component
public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
