package org.example.budgetmanager.security.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.budgetmanager.security.token.AccessToken;
import org.example.budgetmanager.security.token.AccessTokenDecoder;
import org.example.budgetmanager.security.token.exception.InvalidAccessTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationRequestFilter extends OncePerRequestFilter {
    private static final String SPRING_SECURITY_ROLE_PREFIX = "ROLE_";

    @Autowired
    private AccessTokenDecoder accessTokenDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String accessTokenString = requestTokenHeader.substring(7);

        try {
            AccessToken accessToken = accessTokenDecoder.decode(accessTokenString);
            setupSpringSecurityContext(accessToken);
            chain.doFilter(request, response);
        } catch (InvalidAccessTokenException e) {
            logger.error("Error validating access token", e);
            sendAuthenticationError(response);
        }
    }

    private void sendAuthenticationError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.flushBuffer();
    }

private void setupSpringSecurityContext(AccessToken accessToken) {
    // Map the single role from the enum to a SimpleGrantedAuthority
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(SPRING_SECURITY_ROLE_PREFIX + accessToken.getRole().name());

    // Create a UserDetails object with the mapped authority
    UserDetails userDetails = new User(accessToken.getEmail(), "", List.of(authority));

    // Create the authentication token with the UserDetails and set it in the SecurityContext
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
    authenticationToken.setDetails(accessToken);
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
}


}
