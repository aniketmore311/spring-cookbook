package com.aniketmore.springsecjwt.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.ToString;

@ToString()
class JWTAuthenticationToken implements Authentication {

    // comes to authenticate in this case is the username
    private String principal;
    // comes to authenticate in this case is the jwts
    private String credentials;
    // populated after successful authentication
    private UserDetails details;
    private boolean isAuthenticated;

    public static JWTAuthenticationToken unauthenticated(DecodedJWT jwt) {
        return new JWTAuthenticationToken(jwt.getSubject(), jwt.getToken(), null, false);
    }

    public static JWTAuthenticationToken authenticated(DecodedJWT jwt) {
        return new JWTAuthenticationToken(jwt.getSubject(), jwt.getToken(), new SecurityUserDetails(jwt), true);
    }

    // when authenticated
    private JWTAuthenticationToken(String principal, String credentials, UserDetails details, boolean isAuthenticated) {
        this.principal = principal;
        this.credentials = credentials;
        this.details = details;
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (details == null) {
            return new ArrayList<SecurityAuthority>();
        } else {
            return details.getAuthorities();
        }
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;

    }

    @Override
    public String getName() {
        return principal;
    }

}