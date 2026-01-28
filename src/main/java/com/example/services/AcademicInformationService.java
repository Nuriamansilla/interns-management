package com.example.services;

import java.util.List;

import com.example.entities.AcademicInformation;
import com.example.entities.Intern;

public interface AcademicInformationService {

    List<AcademicInformation> findAllAcademicInformations();
    AcademicInformation findAcademicInformationById(int id);
    AcademicInformation saveAcademicInformation(AcademicInformation academicInformation);
    AcademicInformation getAcademicInformationById(int id);
    // AcademicInformation findByIntern(Intern intern);
    List<AcademicInformation> findByIntern(Intern intern);

}
