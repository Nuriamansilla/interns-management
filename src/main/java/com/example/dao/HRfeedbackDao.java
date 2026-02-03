package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entities.HRfeedback;

@Repository
public interface HRfeedbackDao extends JpaRepository<HRfeedback, Integer> {

    @Query("""
            select f
            from HRfeedback f
            where f.intern.globalId = :globalId
            order by f.dateOfFeedback desc
            """)
    List<HRfeedback> findAllByInternGlobalIdOrdered(@Param("globalId") Long globalId);

}
