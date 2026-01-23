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
     public Intern findByGlobalID(long globalID) {
        return internDao.findByGlobalID(globalID);
     }

     // DTO METHODS

    
    // public InternResponse findByName(String name) {
    //     Intern intern = internDao.findByName(name);
    //     AcademicInformation academicInformation =
    //         academicInformationService.findByIntern(intern);

    //     return internMapper.mapInternAndAcademicInformationToInternResponse(
    //         intern, academicInformation
    //     );
    // }


    // public InternResponse findBySurname1(String surname1) {

    //     Intern intern = internDao.findBySurname1(null);

    //     AcademicInformation academicInformation =
    //         academicInformationService.findByIntern(intern);

    //     InternResponse internResponse = internMapper.mapInternAndAcademicInformationToInternResponse(intern, academicInformation);
    //    return internResponse;
    // }


    //  public InternResponse getInternByGlobalId(Long globalID) {

    //     Intern intern = internDao.findByGlobalID(globalID);

    //     AcademicInformation academicInformation = academicInformationService
    //                         .getAcademicInformationById(intern.getId());

    //     InternResponse internResponse = internMapper.mapInternAndAcademicInformationToInternResponse(intern, academicInformation);
       
    //     return internResponse;
     
    //  }


    @Override
    public InternResponse getInternByGlobalId(Long globalID) {
    Intern intern = findByGlobalID(globalID);
        return buildInternResponse(intern);
    }

    
    public InternResponse findByName(String name) {
        Intern intern = internDao.findByName(name);
        return buildInternResponse(intern);
    }

    
    public InternResponse findBySurname1(String surname1) {
        Intern intern = internDao.findBySurname1(surname1);
        return buildInternResponse(intern);
    
    }
    

    private InternResponse buildInternResponse(Intern intern) {

        AcademicInformation academicInformation =
            academicInformationService.findByIntern(intern)
                .stream()
                .findFirst()
                .orElse(null);

        return internMapper.mapInternAndAcademicInformationToInternResponse(
            intern, academicInformation);

    }

    @Override
    public List<Intern> searchInterns(String query) {
        return internDao.searchInterns(query);
    }


}
