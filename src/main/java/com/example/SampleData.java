package com.example;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entities.AcademicInformation;
import com.example.entities.Center;
import com.example.entities.EducationCenter;
import com.example.entities.EducationLevel;
import com.example.entities.Gender;
import com.example.entities.Intern;
import com.example.entities.Language;
import com.example.entities.LanguageName;
import com.example.entities.Level;
import com.example.services.AcademicInformationService;
import com.example.services.InternService;
import com.example.services.LanguageService;


@Configuration
public class SampleData {

    @Bean
    public CommandLineRunner samplesData(InternService internService, 
            AcademicInformationService academicInformationService,
            LanguageService languageService) {

        return args -> {
    
        internService.save(
            Intern.builder()
                .name("Nuria")
                .surname1("Mansilla")
                .surname2("Lopez")
                .dateOfBirth(LocalDate.of(1998, Month.NOVEMBER, 12))
                .globalID(12345678L)
                .gender(Gender.WOMAN)
                .center(Center.VALENCIA)
                .build());
                
        internService.save(
            Intern.builder()
                .name("Teresa Maria")
                .surname1("Magnaldi")
                .surname2("Lago")
                .dateOfBirth(LocalDate.of(1995, Month.NOVEMBER, 21))
                .globalID(98765432L)
                .gender(Gender.WOMAN)
                .center(Center.MURCIA)
                .build());

        internService.save(
            Intern.builder()
                .name("Dhekra")
                .surname1("Belloumi")
                .surname2("")
                .dateOfBirth(LocalDate.of(1997, Month.NOVEMBER, 15))
                .globalID(11223344L)
                .gender(Gender.WOMAN)
                .center(Center.VALENCIA)
                .build());

        internService.save(
            Intern.builder()
                .name("Amel")
                .surname1("Belamria")
                .surname2("Belahmal")
                .dateOfBirth(LocalDate.of(1996, Month.NOVEMBER, 30))
                .globalID(55667788L)
                .gender(Gender.WOMAN)
                .center(Center.VALENCIA)
                .build());


        // Academic information

        academicInformationService.saveAcademicInformation(AcademicInformation.builder()             
                                  .educationLevel(EducationLevel.DEGREE)
                                  .title("Grado en Física")
                                  .startDate(LocalDate.of(2020, Month.JANUARY, 3))
                                  .endDate(LocalDate.of(2024, Month.JUNE, 3))
                                  .educationCenter(EducationCenter.UNIVERSITY)
                                  .universityOrIES("Pepito")
                                  .intern(internService.findById(1))
                                  .build());

        academicInformationService.saveAcademicInformation(AcademicInformation.builder()             
                                  .educationLevel(EducationLevel.FP)
                                  .title("Informática")
                                  .startDate(LocalDate.of(2020, Month.JANUARY, 10))
                                  .endDate(LocalDate.of(2024, Month.JUNE, 6))
                                  .educationCenter(EducationCenter.IES)
                                  .universityOrIES("Junito")
                                  .intern(internService.findById(1))
                                  .build());

        academicInformationService.saveAcademicInformation(AcademicInformation.builder()             
                                  .educationLevel(EducationLevel.FP)
                                  .title("Turismo")
                                  .startDate(LocalDate.of(2020, Month.JANUARY, 10))
                                  .endDate(LocalDate.of(2024, Month.JUNE, 6))
                                  .educationCenter(EducationCenter.IES)
                                  .universityOrIES("Pablo Picasso")
                                  .intern(internService.findById(3))
                                  .build());
        
        academicInformationService.saveAcademicInformation(AcademicInformation.builder()             
                                  .educationLevel(EducationLevel.DEGREE)
                                  .title("Bellas Artes")
                                  .startDate(LocalDate.of(2020, Month.JANUARY, 10))
                                  .endDate(LocalDate.of(2024, Month.JUNE, 6))
                                  .educationCenter(EducationCenter.UNIVERSITY)
                                  .universityOrIES("UPV")
                                  .intern(internService.findById(4))
                                  .build());
                                  

        // Languages

        languageService.save(Language.builder()
                       .languageName(LanguageName.ENGLISH)
                       .level(Level.B2)
                       .intern(internService.findById(1))
                       .build());
        
        languageService.save(Language.builder()
                       .languageName(LanguageName.FRENCH)
                       .level(Level.B2)
                       .intern(internService.findById(1))
                       .build());
        
        languageService.save(Language.builder()
                       .languageName(LanguageName.SPANISH)
                       .level(Level.B2)
                       .intern(internService.findById(2))
                       .build());
        
        languageService.save(Language.builder()
                       .languageName(LanguageName.FRENCH)
                       .level(Level.B2)
                       .intern(internService.findById(3))
                       .build());
        languageService.save(Language.builder()
                       .languageName(LanguageName.ENGLISH)
                       .level(Level.B2)
                       .intern(internService.findById(4))
                       .build());
        
        languageService.save(Language.builder()
                       .languageName(LanguageName.SPANISH)
                       .level(Level.B2)
                       .intern(internService.findById(4))
                       .build());

    


        };
     }
 }
