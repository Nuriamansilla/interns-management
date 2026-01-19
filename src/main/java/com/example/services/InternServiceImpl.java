package com.example.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dao.InternDao;
import com.example.entities.Intern;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternServiceImpl implements InternService{


    private final InternDao internDao;

    private final InternMapper internMapper;

    @Override
    public Page<Intern> findAll(Pageable pageable) {
        return internDao.findAll(pageable);
    }

    @Override
    public List<Intern> findAll(Sort sort) {
        return internDao.findAll(sort);
    }

    @Override
    public Intern findById(int id) {
        return internDao.findById(id).get();
    }

    @Override
    public Intern save(Intern intern) {
        return internDao.save(intern);
    }

    @Override
    public void delete(Intern intern) {
        internDao.delete(intern);
    }

    @Override
    public List<Intern> findAll() {
        return internDao.findAll();
    }

    @Override
    public Intern findByGlobalID(long globalID) {
        return internDao.findByGlobalID(globalID);
    }

    @Override
    public InternDto getInternById(Long id) {

        InternDto internDto = null;

        Intern intern = internDao.findByGlobalID(id).orElseThrow
        return internDto
    }
}
