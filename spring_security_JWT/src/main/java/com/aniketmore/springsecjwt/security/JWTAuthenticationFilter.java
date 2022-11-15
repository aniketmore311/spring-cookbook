package com.aniketmore.springsecjwt.security;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;


class JWTAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;
    private JWTAuthenticationConverter converter = new JWTAuthenticationConverter();
    private AuthenticationEntryPoint authenticationEntryPoint;

    JWTAuthenticationFilter(AuthenticationManager authenticationManager,
            AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            JWTAuthenticationToken authenticationRequest = converter.convert(request);
            // no applicable for jwt authentication
            if (authenticationRequest == null) {
                this.logger.trace("unable to do jwt authentication, token not found in Authorization header");
            } else {
                String username = authenticationRequest.getName();
                this.logger.trace("performing jwt authentication for username: " + username);
                if (isAuthencationRequired(username)) {
                    Authentication authenticationResult = this.authenticationManager
                            .authenticate(authenticationRequest);
                    SecurityContext newContext = SecurityContextHolder.createEmptyContext();
                    newContext.setAuthentication(authenticationResult);
                    SecurityContextHolder.setContext(newContext);
                    this.logger.debug("set security context to: " + authenticationResult.toString());
                }
            }
        } catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            this.logger.debug("failed to do jwt authentication", ex);
            authenticationEntryPoint.commence(request, response, ex);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isAuthencationRequired(String principal) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }
        if (existingAuth instanceof JWTAuthenticationToken && !existingAuth.getName().equals(principal)) {
            return true;
        }
        return false;
    }
}