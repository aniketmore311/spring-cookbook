package com.aniketmore.springsec1.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aniketmore.springsec1.repositories.UserRepository;

@Service
public class JPAUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    JPAUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepo.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("username not found");
        }
        return new SecurityUserDetails(user.get());
    }

}
