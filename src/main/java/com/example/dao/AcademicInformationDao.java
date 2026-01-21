package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.AcademicInformation;
import com.example.entities.Intern;

@Repository
public interface AcademicInformationDao extends JpaRepository<AcademicInformation, Integer>{

    List<AcademicInformation> findByIntern(Intern intern);
}


