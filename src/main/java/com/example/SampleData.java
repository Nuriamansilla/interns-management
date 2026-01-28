package com.example;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.entities.AcademicInformation;
import com.example.entities.Center;
import com.example.entities.EducationCenter;
import com.example.entities.EducationLevel;
import com.example.entities.Gender;
import com.example.entities.HRfeedback;
import com.example.entities.Intern;
import com.example.entities.Language;
import com.example.entities.LanguageName;
import com.example.entities.Level;
import com.example.services.AcademicInformationService;
import com.example.services.HRfeedbackService;
import com.example.services.InternService;
import com.example.services.LanguageService;
import com.example.spring_security_jwt.models.ERole;
import com.example.spring_security_jwt.models.Role;
import com.example.spring_security_jwt.models.User;
import com.example.spring_security_jwt.repository.RoleRepository;
import com.example.spring_security_jwt.repository.UserRepository;


@Configuration
public class SampleData {

    @Bean
    public CommandLineRunner samplesData(InternService internService, 
            AcademicInformationService academicInformationService,
            LanguageService languageService,
            RoleRepository roleRepository,
            UserRepository userRepository,
            HRfeedbackService hRfeedbackService,
            PasswordEncoder passwordEncoder) {

        return args -> {
    
        internService.save(
            Intern.builder()
                .globalID(12345678L)
                .name("Nuria")
                .surname1("Mansilla")
                .surname2("Lopez")
                .dateOfBirth(LocalDate.of(1998, Month.NOVEMBER, 12))
                .gender(Gender.WOMAN)
                .center(Center.VALENCIA)
                .build());
                
        internService.save(
            Intern.builder()
                .globalID(98765432L)
                .name("Teresa Maria")
                .surname1("Magnaldi")
                .surname2("Lago")
                .dateOfBirth(LocalDate.of(1995, Month.NOVEMBER, 21))
                .gender(Gender.WOMAN)
                .center(Center.MURCIA)
                .build());

        internService.save(
            Intern.builder()
                .globalID(11223344L)
                .name("Dhekra")
                .surname1("Belloumi")
                .surname2("")
                .dateOfBirth(LocalDate.of(1997, Month.NOVEMBER, 15))
                .gender(Gender.WOMAN)
                .center(Center.VALENCIA)
                .build());

        internService.save(
            Intern.builder()
                .globalID(55667788L)
                .name("Amel")
                .surname1("Belamria")
                .surname2("Belahmal")
                .dateOfBirth(LocalDate.of(1996, Month.NOVEMBER, 30))
                .gender(Gender.WOMAN)
                .center(Center.MURCIA)
                .build());


        // Academic information

        academicInformationService.saveAcademicInformation(AcademicInformation.builder()             
                                  .educationLevel(EducationLevel.DEGREE)
                                  .title("Degree in Physics")
                                  .startDate(LocalDate.of(2020, Month.JANUARY, 3))
                                  .endDate(LocalDate.of(2024, Month.JUNE, 3))
                                  .educationCenter(EducationCenter.UNIVERSITY)
                                  .universityOrIES("UPV")
                                  .intern(internService.findById(1))
                                  .build());

        academicInformationService.saveAcademicInformation(AcademicInformation.builder()             
                                  .educationLevel(EducationLevel.FP)
                                  .title("Advanced Vocational Diploma in Computer Science")
                                  .startDate(LocalDate.of(2020, Month.JANUARY, 10))
                                  .endDate(LocalDate.of(2024, Month.JUNE, 6))
                                  .educationCenter(EducationCenter.IES)
                                  .universityOrIES("Abastos")
                                  .intern(internService.findById(2))
                                  .build());

        academicInformationService.saveAcademicInformation(AcademicInformation.builder()             
                                  .educationLevel(EducationLevel.FP)
                                  .title("Vocational Training in Tourism")
                                  .startDate(LocalDate.of(2020, Month.JANUARY, 10))
                                  .endDate(LocalDate.of(2024, Month.JUNE, 6))
                                  .educationCenter(EducationCenter.IES)
                                  .universityOrIES("Pablo Picasso")
                                  .intern(internService.findById(3))
                                  .build());
        
        academicInformationService.saveAcademicInformation(AcademicInformation.builder()             
                                  .educationLevel(EducationLevel.DEGREE)
                                  .title("Bachelor of Fine Arts (BFA)")
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

                       
        // Agregamos los roles de USER y ADMIN

        Role adminRole = roleRepository.save(Role.builder().name(ERole.ROLE_ADMINISTRATOR).build());
        Role userRole = roleRepository.save(Role.builder().name(ERole.ROLE_HRUSER).build());

        userRepository.save(User.builder()
                .username("admin1")
                .email("admin1@gmail.com")
                .roles(Set.of(adminRole))
                .password(passwordEncoder.encode("Lemur2026$$"))
                .build()); 

        userRepository.save(User.builder()
                .username("user1")
                .email("user1@gmail.com")
                .roles(Set.of(userRole))
                .password(passwordEncoder.encode("Lemur2026$$"))
                .build());




        //datos de muestra de hr feedback 

        hRfeedbackService.save(HRfeedback.builder()
                        .nameFeedback("Initial test")
                        .dateOfFeedBack(LocalDate.of(2025, Month.DECEMBER, 9))
                        .hrUser("Juliette Dubois")
                        .comments("Good start on the initial test.")
                        .intern(internService.findById(1)).build());
        
        
        hRfeedbackService.save(HRfeedback.builder()
                .nameFeedback("Technical Aptitude Review")
                .dateOfFeedBack(LocalDate.of(2025, Month.DECEMBER, 15))
                .hrUser("Juliette Dubois")
                .comments("Good start.")
                .intern(internService.findById(2)).build());

                
        hRfeedbackService.save(HRfeedback.builder()
                .nameFeedback("Logic & Reasoning Test")
                .dateOfFeedBack(LocalDate.of(2025, Month.DECEMBER, 18))
                .hrUser("Antoine Dupont")
                .comments("Shows potential.")
                .intern(internService.findById(3)).build());

                
        hRfeedbackService.save(HRfeedback.builder()
                .nameFeedback("Introductory Skills Review")
                .dateOfFeedBack(LocalDate.of(2025, Month.DECEMBER, 20))
                .hrUser("Gabriel Chevalier")
                .comments("Could improve.")
                .intern(internService.findById(2)).build());
                
        hRfeedbackService.save(HRfeedback.builder()
                .nameFeedback("Entry-Level Competency Check")
                .dateOfFeedBack(LocalDate.of(2025, Month.DECEMBER, 21))
                .hrUser("Sophie Marchand")
                .comments("Solid basics.")
                .intern(internService.findById(4)).build());



        };
     }
 }
