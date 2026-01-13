package com.example.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.entities.Intern;

public interface InternService {

    Page<Intern> findAll(Pageable pageable);
    List<Intern> findAll(Sort sort);
    Intern findById(int id);
    Intern save(Intern intern);
    void delete(Intern intern);
    List<Intern> findAll();
    Intern findByGlobalID(long globalID);
}
