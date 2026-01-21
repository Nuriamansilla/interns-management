package com.example.spring_security_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_security_jwt.models.ERole;
import com.example.spring_security_jwt.models.Role;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    // Lo llamo Optional porque cuando busco algo podría encontrarlo o no
    Optional<Role> findByName(ERole name);
}