package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.AcademicInformationDao;
import com.example.entities.AcademicInformation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcademicInformationServiceImpl implements AcademicInformationService {

    private final AcademicInformationDao academicInformationDao;

    @Override
    public List<AcademicInformation> findAllAcademicInformations() {
        return academicInformationDao.findAll();
    }

    @Override
    public AcademicInformation findAcademicInformationById(int id) {
        return academicInformationDao.findById(id).get();
    }

    @Override
    public AcademicInformation saveAcademicInformation(AcademicInformation academicInformation) {
        return academicInformationDao.save(academicInformation);
    }

    @Override
    public AcademicInformation getAcademicInformationById(int id) {
        return academicInformationDao.findById(id).get();
    }

}
