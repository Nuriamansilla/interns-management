package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.LanguageDao;
import com.example.entities.Language;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageDao languageDao;
    
    @Override
    public List<Language> findAll() {
        return languageDao.findAll();
    }

    @Override
    public Language save(Language language) {
        return languageDao.save(language);
    }

    @Override
    public Language findById(int id) {
        return languageDao.findById(id).get();
    }

}
