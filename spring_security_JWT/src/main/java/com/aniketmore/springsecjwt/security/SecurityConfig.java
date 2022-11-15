package com.aniketmore.springsecjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
public class SecurityConfig {

    @Autowired
    public JWTAuthenticationProvider jwtAuthenticationProvider;

    @Bean
    public UserDetailsService userDetailsService() {
        var u1 = User.withUsername("user1")
                .password(this.passwordEncoder()
                        .encode("password"))
                .roles("USER").build();

        var u2 = User.withUsername("admin")
                .password(this.passwordEncoder().encode("password"))
                .roles("ADMIN").build();

        return new InMemoryUserDetailsManager(u1, u2);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain getsSecurityFilterChain(HttpSecurity http) throws Exception {
        // adding custom filter and provider
        http.apply(new JWTDSL(jwtAuthenticationProvider, new BasicAuthenticationEntryPoint()));
        http.authorizeHttpRequests()
                .mvcMatchers("/hello").permitAll()
                .mvcMatchers("/auth/login").permitAll()
                .mvcMatchers("/auth/register").permitAll()
                .mvcMatchers("/user/**").hasRole("USER")
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
        return http.build();
    }

}
