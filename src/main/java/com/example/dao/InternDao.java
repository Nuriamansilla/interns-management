package com.example.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Intern;

@Repository
public interface InternDao extends JpaRepository<Intern, Integer>{

    Intern findByGlobalID(Long globalID);
    Intern findByName(String name);
    Intern findBySurname1(String surname1);

    Optional<Intern> findOptionalByGlobalID(Long globalID);
}
