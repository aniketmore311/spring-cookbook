package com.aniketmore.springsec1.runners;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.aniketmore.springsec1.entities.Role;
import com.aniketmore.springsec1.entities.User;
import com.aniketmore.springsec1.repositories.RoleRepository;
import com.aniketmore.springsec1.repositories.UserRepository;

@Component
public class SeedRunner implements CommandLineRunner {

    @Value("${app.startup.seed}")
    private boolean doSeed;

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (doSeed) {

            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepo.save(adminRole);

            Role userRole = new Role();
            userRole.setName("USER");
            roleRepo.save(userRole);

            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setName("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRoles(new HashSet<Role>(List.of(adminRole)));
            userRepo.save(admin);

            User user = new User();
            user.setEmail("user@gmail.com");
            user.setName("user");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(new HashSet<Role>(List.of(userRole)));
            userRepo.save(user);
        }

    }

}
