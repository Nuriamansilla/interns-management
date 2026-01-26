package com.example.services;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.dtos.InternResponse;
import com.example.entities.AcademicInformation;
import com.example.entities.Intern;


@Mapper(componentModel = "spring")
public interface InternMapper {


    // Mapper DTO 1.4
    @Mapping(source = "intern.id", target = "id")
    @Mapping(source = "intern.name", target = "name")
    @Mapping(source = "intern.surname1", target = "surname1")
    @Mapping(source = "intern.surname2", target = "surname2")
    @Mapping(source = "intern.globalID", target = "globalID")
    @Mapping(source = "intern.gender", target = "gender")
    @Mapping(source = "intern.center", target = "center")
    @Mapping(source = "academicInformation.title", target = "title")

    InternResponse mapInternAndAcademicInformationToInternResponse(
        Intern intern, AcademicInformation academicInformation);

    //  MApper DTO 1.5
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "intern.name", target = "name")
    @Mapping(source = "intern.surname1", target = "surname1")
    @Mapping(source = "intern.surname2", target = "surname2")
    @Mapping(source = "intern.center", target = "center")
    @Mapping(source = "intern.globalID", target = "globalID")
    InternResponse mapToInternResponseUS15(Intern intern);
    List<InternResponse> mapToInternResponseUS15(List<Intern> interns);
 
}
