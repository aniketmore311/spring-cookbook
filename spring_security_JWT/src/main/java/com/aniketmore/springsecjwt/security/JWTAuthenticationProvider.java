package com.aniketmore.springsecjwt.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
class JWTAuthenticationProvider implements AuthenticationProvider {

    @Value("${app.jwt.secret}")
    String secret;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwts = (String) authentication.getCredentials();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT jwt = jwtVerifier.verify(jwts);
            var successfulAuthentication = JWTAuthenticationToken.authenticated(jwt);
            return successfulAuthentication;
        } catch (JWTVerificationException ex) {
            throw new BadCredentialsException("invalid token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        if (authentication.equals(JWTAuthenticationToken.class)) {
            return true;
        } else {
            return false;
        }
    }
}
