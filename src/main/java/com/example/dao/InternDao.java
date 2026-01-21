package com.example.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Intern;



@Repository
public interface InternDao extends JpaRepository<Intern, Integer>{

    Intern findByGlobalID(Long globalID);
    boolean existsByGlobalID(Long globalID);

}  
