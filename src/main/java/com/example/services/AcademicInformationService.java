package com.example.services;

import java.util.List;

import com.example.entities.AcademicInformation;

public interface AcademicInformationService {

    List<AcademicInformation> findAllAcademicInformations();
    AcademicInformation findAcademicInformationById(int id);
    AcademicInformation saveAcademicInformation(AcademicInformation academicInformation);
    AcademicInformation getAcademicInformationById(int id);
   


}
