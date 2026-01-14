package com.example.services;

import java.util.List;

import com.example.entities.HRfeedback;

public interface HRfeedbackService {
    List<HRfeedback> findAll();
    HRfeedback findHRfeedbackById(int id);
    HRfeedback saveHRfeedback(HRfeedback hRfeedback);

}
