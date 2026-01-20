package com.example.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.entities.HRfeedback;


public interface HRfeedbackService {

    List<HRfeedback> findAll();
    HRfeedback findById(int id);
    HRfeedback save(HRfeedback hRfeedback);
    
    Page<HRfeedback> findAll(Pageable pageable);
    List<HRfeedback> findAll(Sort sort);

    List<HRfeedback> findByGlobalIDWithOrden(Long globalID);

}
