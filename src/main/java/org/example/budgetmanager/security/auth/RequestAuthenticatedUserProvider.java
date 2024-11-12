package org.example.budgetmanager.security.auth;


import org.example.budgetmanager.security.token.AccessToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@EnableWebSecurity
public class RequestAuthenticatedUserProvider {
    /*
    SCOPE_REQUEST: Sets the bean’s scope to request, which means that each HTTP request will get a fresh instance of the AccessToken bean.
    proxyMode = ScopedProxyMode.TARGET_CLASS: Creates a proxy for this bean,
    which allows the AccessToken to be evaluated and injected only when the request is active.
    * */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AccessToken getAuthenticatedUserInRequest() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }

        final Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return null;
        }

        final Object details = authentication.getDetails();
        if (!(details instanceof AccessToken)) {
            return null;
        }

        return (AccessToken) authentication.getDetails();
    }


}

