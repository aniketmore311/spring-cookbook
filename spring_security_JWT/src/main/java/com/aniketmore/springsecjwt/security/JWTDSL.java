package com.aniketmore.springsecjwt.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

class JWTDSL extends AbstractHttpConfigurer<JWTDSL, HttpSecurity> {

    private AuthenticationEntryPoint authenticationEntryPoint;
    private JWTAuthenticationProvider jwtAuthenticationProvider;

    JWTDSL(JWTAuthenticationProvider jwtAuthenticationProvider, AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        var atm = http.getSharedObject(AuthenticationManager.class);
        http.authenticationProvider(jwtAuthenticationProvider)
                .addFilterBefore(new JWTAuthenticationFilter(atm,
                        this.authenticationEntryPoint),
                        UsernamePasswordAuthenticationFilter.class);
    }

}
