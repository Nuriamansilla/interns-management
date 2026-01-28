package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "interns")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Intern implements Serializable {

    private static final long serialVersionUID = 1L;

    // TRES LÍNEAS COMENTADAS PORQUE DAN ERROR: NO DESCOMENTAR
    // // @Min(value = 10000000, message = "globalID must have exactly 8 digit")
    // // @Max(value = 99999999, message = "globalID must have exactly 8 digit")
    // // @Column(name = "global_id", nullable = false, unique = true)
    @NotNull(message = "The intern's global ID  is required and cannot be null")
    private Long globalID;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // VALIDACIONES Y RELACIONES CORRECTAS: NO MODIFICAR

    @NotEmpty(message = "The intern's name cannot be empty")
    private String name;

    @NotEmpty(message = "The intern's surname1 cannot be empty")
    private String surname1;

    private String surname2;

    @NotNull(message = "The intern's date of birth is required")
    @Past(message = "The intern's date of birth is required and must be earlier than today")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotNull(message = "The intern's gender is required")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "The intern's center is required")
    @Enumerated(EnumType.STRING)
    private Center center;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "intern_id", referencedColumnName = "id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private List<AcademicInformation> academicInformation;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "intern_id", referencedColumnName = "id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private List<Language> languages;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "intern_id", referencedColumnName = "id")
    // @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy =
    // "intern")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private List<HRfeedback> hrfeedbacks;

}
