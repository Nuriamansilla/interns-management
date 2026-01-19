package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "academicInformations")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AcademicInformation implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // VALIDACIONES Y RELACIONES CORRECTAS: NO MODIFICAR

    @NotNull(message = "The education level is required")
    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;

    @NotNull(message = "The title is required and cannot be null")
    @NotEmpty(message = "The title cannot be empty")
    private String title;

    @NotNull(message = "The start date is required")
    @Past(message = "The start date must be earlier than today")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "The education center is required")
    @Enumerated(EnumType.STRING)
    private EducationCenter educationCenter;

    @NotNull(message = "The education center name is required and cannot be null")
    @NotEmpty(message = "The education center name cannot be empty")
    private String universityOrIES;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Intern intern;
    
}
