package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.AcademicInformation;

@Repository
public interface AcademicInformationDao extends JpaRepository<AcademicInformation, Integer>{

}
