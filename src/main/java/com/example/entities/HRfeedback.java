package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "HRfeedbacks")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HRfeedback implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull(message = "The feedback's name is required")
    private String nameFeedback;

    @NotNull(message = "registered date of the feedback is requiered")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfFeedBack;

    @NotNull(message = "The name of the person who gives the feedback is required")
    private String hrUser;

    @NotNull(message = "At least one feedback is required is requiered")
    @Size(max = 1000, message = "feedbacks must not exceed 1000 characters")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Intern intern; 


    // CORRECTO: NO MODIFICAR   
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Intern intern;
    
}
