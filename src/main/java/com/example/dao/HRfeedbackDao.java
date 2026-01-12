package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.HRfeedback;

@Repository
public interface HRfeedbackDao extends JpaRepository<HRfeedback, Integer>{

}
