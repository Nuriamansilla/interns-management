package com.example.services;

import java.util.List;

import com.example.entities.HRfeedback;

public interface HRfeedbackService {
    List<HRfeedback> findAll();
    HRfeedback findById(int id);
    HRfeedback save(HRfeedback hRfeedback);

}
