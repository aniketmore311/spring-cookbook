package com.aniketmore.springsec1.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aniketmore.springsec1.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
