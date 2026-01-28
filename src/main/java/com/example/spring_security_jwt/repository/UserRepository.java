package com.example.spring_security_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_security_jwt.models.User;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /* Como el username y el email son unique (no se pueden repetir), antes de dar de alta
       a un usuario hay que preguntar si el usuario existe o no. */
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}