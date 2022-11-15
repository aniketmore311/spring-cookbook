package com.aniketmore.springsec1.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aniketmore.springsec1.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long id);
}
