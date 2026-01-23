package com.example.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entities.Intern;

@Repository
public interface InternDao extends JpaRepository<Intern, Integer>{

    Intern findByGlobalID(Long globalID);
    Intern findByName(String name);
    Intern findBySurname1(String surname1);
    boolean existsByGlobalID(Long globalID);

    
        @Query("""
       SELECT i FROM Intern i
       WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :query, '%'))
          OR LOWER(i.surname1) LIKE LOWER(CONCAT('%', :query, '%'))
          OR LOWER(CAST(i.globalID AS string)) LIKE LOWER(CONCAT('%', :query, '%'))
          OR LOWER(CAST(i.center AS string)) LIKE LOWER(CONCAT('%', :query, '%'))
       """)

        List<Intern> searchInterns(@Param("query") String query);


    Optional<Intern> findOptionalByGlobalID(Long globalID);
}
