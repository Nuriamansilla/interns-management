package com.example.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<HRfeedback> findAll(Pageable pageable) {
        
        return hRfeedbackDao.findAll(pageable);
    }

    @Override
    public List<HRfeedback> findAll(Sort sort) {
        
        return hRfeedbackDao.findAll(sort);
    }


    @Override
    public List<HRfeedback> findByGlobalIDWithOrden(Long globalID) {

        return hRfeedbackDao.findByGlobalIDWithOrden(globalID);
    }
    

}
