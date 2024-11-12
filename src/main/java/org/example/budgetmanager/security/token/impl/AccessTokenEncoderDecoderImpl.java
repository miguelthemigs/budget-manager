package org.example.budgetmanager.security.token.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.example.budgetmanager.model.Role;
import org.example.budgetmanager.security.token.AccessToken;
import org.example.budgetmanager.security.token.AccessTokenDecoder;
import org.example.budgetmanager.security.token.AccessTokenEncoder;
import org.example.budgetmanager.security.token.exception.InvalidAccessTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.jsonwebtoken.security.Keys;

@Service
public class AccessTokenEncoderDecoderImpl implements AccessTokenEncoder, AccessTokenDecoder {
    private final Key key;

    public AccessTokenEncoderDecoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();

        if (accessToken.getUserId() != null) {
            claimsMap.put("userId", accessToken.getUserId());
        }

        if (accessToken.getRole() != null) {
            claimsMap.put("role", accessToken.getRole().name()); // Store role as a string
        }

        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(accessToken.getEmail())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

@Override
public AccessToken decode(String accessTokenEncoded) {
    try {
        Jwt<?, Claims> jwt = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(accessTokenEncoded);
        Claims claims = jwt.getBody();
        Long userId = claims.get("userId", Long.class);
        String role = claims.get("role", String.class); // Getting the role as a String

        return new AccessTokenImpl(claims.getSubject(), userId, Role.valueOf(role));
    } catch (JwtException e) {
        throw new InvalidAccessTokenException(e.getMessage());
    }
}

}
