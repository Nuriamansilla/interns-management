package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interns")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Intern implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private int id;


    @NotNull(message = "The intern's name is required and cannot be null")
    @NotEmpty(message = "The intern's name cannot be empty")
    private String name;

    @NotNull(message = "The intern's surname1 is required and cannot be null")
    @NotEmpty(message = "The intern's surname1 cannot be empty")
    private String surname1;

    private String surname2;
    
    @NotNull(message = "The intern's date of birth is required")
    @Past(message = "The intern's date of birth is required and must be earlier than today")
    @DateTimeFormat(pattern = "DD/MM/YYYY")
    private LocalDate dateOfBirth; 

    @NotNull(message = "The intern's global ID  is required and cannot be null")
    @NotEmpty(message = "The intern's global ID cannot be empty")
    private Long globalID;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "The intern's center is required")
    @Enumerated(EnumType.STRING)
    private Center center;

    // @OneToOne
    private AcademicInformation academicInformation;
    
    // @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "intern")
    // @JsonIgnore
    private Language language;

    // @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "intern")
    // @JsonIgnore
    private HRfeedback hrfeedback;
}
