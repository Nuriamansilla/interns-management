package com.example.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    private int id;
}
