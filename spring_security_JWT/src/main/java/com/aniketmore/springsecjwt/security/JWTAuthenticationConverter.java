package com.aniketmore.springsecjwt.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

class JWTAuthenticationConverter implements AuthenticationConverter {

    private final String AUTHENTICATION_SCHEME_JWT = "JWT";

    /**
     * create Authentication object from request
     * return null if not applicable
     * throw error if invalid format
     */
    @Override
    public JWTAuthenticationToken convert(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            return null;
        }
        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_JWT)) {
            return null;
        }
        if (header.equalsIgnoreCase(AUTHENTICATION_SCHEME_JWT)) {
            throw new BadCredentialsException("Empty jwt token");
        }
        String[] tokens = header.split(" ");
        if (tokens.length < 2) {
            throw new BadCredentialsException("invalid header format");
        }
        String jwts = tokens[1];
        try {
            DecodedJWT jwt = JWT.decode(jwts);
            JWTAuthenticationToken authentication = JWTAuthenticationToken.unauthenticated(jwt);
            return authentication;
        } catch (JWTDecodeException ex) {
            throw new BadCredentialsException("invalid jwt format");
        }
    }

}