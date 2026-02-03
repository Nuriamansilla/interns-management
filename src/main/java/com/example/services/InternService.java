package com.example.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.dtos.InternResponse;
import com.example.entities.Intern;

public interface InternService {

    Page<Intern> findAll(Pageable pageable);
    List<Intern> findAll(Sort sort);
    List<Intern> findAll();

    Intern findById(int id);
    Intern save(Intern intern);
    void delete(Intern intern);
    Intern findByGlobalId(long globalId);

    InternResponse getInternByGlobalId(Long globalId);
    InternResponse findByName(String name);
    InternResponse findBySurname1(String surname1);

    List<InternResponse> searchInterns(String query);
    boolean existsByGlobalId(Long globalId);
    
}
