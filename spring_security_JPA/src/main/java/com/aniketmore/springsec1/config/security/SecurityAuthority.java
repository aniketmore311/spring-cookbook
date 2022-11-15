package com.aniketmore.springsec1.config.security;

import org.springframework.security.core.GrantedAuthority;

import com.aniketmore.springsec1.entities.Role;

public class SecurityAuthority implements GrantedAuthority {

    private final Role role;

    SecurityAuthority(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + role.getName();
    }

}
