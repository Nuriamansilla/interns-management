package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    //@NotEmpty(message = "The intern's name cannot be empty")
    private String name;

    //@NotEmpty(message = "The intern's surname1 cannot be empty")
    private String surname1;

    private String surname2;
    
    //@NotNull(message = "The intern's date of birth is required")
    //@Past(message = "The intern's date of birth is required and must be earlier than today")
    @DateTimeFormat(pattern = "DD/MM/YYYY")
    private LocalDate dateOfBirth; 

    //@NotNull(message = "The intern's global ID  is required and cannot be null")
    private Long globalID;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    //@NotNull(message = "The intern's center is required")
    @Enumerated(EnumType.STRING)
    private Center center;

    // @OneToOne
    private AcademicInformation academicInformation;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "intern")
    // @JsonIgnore
    private List<Language> languages;

    // @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "intern")
    // @JsonIgnore
    private HRfeedback hrfeedback;
}
