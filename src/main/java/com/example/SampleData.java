package com.example;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entities.Center;
import com.example.entities.Gender;
import com.example.entities.Intern;
import com.example.services.InternService;

@Configuration
public class SampleData {

    @Bean
    public CommandLineRunner samplesData(InternService internService) {

        return args -> {
    
        internService.save(
            Intern.builder()
                .name("Nuria")
                .surname1("Mansilla")
                .surname2("Lopez")
                .dateOfBirth(LocalDate.of(1998, 5, 12))
                .globalID(123456789L)
                .gender(Gender.WOMAN)
                .center(Center.VALENCIA)
                .build());
                
        internService.save(
            Intern.builder()
                .name("Teresa Maria")
                .surname1("Magnaldi")
                .surname2("Lago")
                .dateOfBirth(LocalDate.of(1995, 3, 21))
                .globalID(987654321L)
                .gender(Gender.WOMAN)
                .center(Center.VALENCIA)
                .build());

        internService.save(
            Intern.builder()
                .name("Dhekra")
                .surname1("Belloumi")
                .surname2("")
                .dateOfBirth(LocalDate.of(1997, 8, 15))
                .globalID(112233445L)
                .gender(Gender.WOMAN)
                .center(Center.VALENCIA)
                .build()
        );

        internService.save(
            Intern.builder()
                .name("Amel")
                .surname1("Belamria")
                .surname2("Belahmal")
                .dateOfBirth(LocalDate.of(1996, 11, 30))
                .globalID(556677889L)
                .gender(Gender.WOMAN)
                .center(Center.VALENCIA)
                .build());

        };
    }
}
