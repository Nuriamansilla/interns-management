package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.HRfeedbackDao;
import com.example.entities.HRfeedback;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HRfeedbackServiceImpl implements HRfeedbackService {

    private final HRfeedbackDao hRfeedbackDao;
    
    @Override
    public List<HRfeedback> findAll() {
        return hRfeedbackDao.findAll() ;
    }

    @Override
    public HRfeedback findById(int id) {
       return hRfeedbackDao.findById(id).get();
    }

    @Override
    public HRfeedback save(HRfeedback hRfeedback) {
        return hRfeedbackDao.save(hRfeedback);
    }

}
