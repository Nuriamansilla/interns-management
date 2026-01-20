package com.example.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dao.InternDao;
import com.example.dtos.InternResponse;
import com.example.entities.AcademicInformation;
import com.example.entities.Intern;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InternServiceImpl implements InternService{


    private final InternDao internDao;
    private final AcademicInformationService academicInformationService;
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
    public InternResponse findByGlobalID(long globalID) {

        InternResponse internResponse = null;

        Intern intern = internDao.findByGlobalID(globalID);

        AcademicInformation academicInformation = academicInformationService
                            .getAcademicInformationById(intern.getId());

        internResponse = internMapper.mapInternAndAcademicInformationToInternResponse(intern, academicInformation);
        return internResponse;

    }
   
     @Override    
    public Intern findByName(String name) {
        return internDao.findByName(name);
    }

    @Override
    public Intern findBySurname1(String surname1) {
       return internDao.findBySurname1(surname1);
    }

     public InternResponse getInternByGlobalId(Long globalID) {

        InternResponse internResponse = null;

        Intern intern = internDao.findByGlobalID(globalID);

        AcademicInformation academicInformation = academicInformationService
                            .getAcademicInformationById(intern.getId());

        internResponse = internMapper.mapInternAndAcademicInformationToInternResponse(intern, academicInformation);
        return internResponse;
     
     }
}
