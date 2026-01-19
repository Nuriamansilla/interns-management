package com.example.services;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.dtos.InternResponse;
import com.example.entities.AcademicInformation;
import com.example.entities.Intern;


@Mapper(componentModel = "spring")
public interface InternMapper {


    @Mapping(source = "intern.id", target = "id")
    InternResponse mapInternAndAcademicInformationToInternResponse(
        Intern intern, AcademicInformation academicInformation);

}
