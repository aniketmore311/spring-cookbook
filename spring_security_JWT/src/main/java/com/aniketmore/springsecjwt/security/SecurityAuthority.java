package com.aniketmore.springsecjwt.security;

import org.springframework.security.core.GrantedAuthority;

class SecurityAuthority implements GrantedAuthority {
    private String authority;

    SecurityAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
