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

    private String name;
    private String surname1;
    private String surname2;

    @DateTimeFormat(pattern = "DD/MM/YYYY")
    private LocalDate dateOfBirth; 

    private Long globalID;

    @Enumerated(EnumType.STRING)
    private Gender gender;

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
