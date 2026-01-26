package com.example.services;

import java.util.List;

import com.example.entities.Language;

public interface LanguageService {

    List<Language> findAll();
    Language save(Language language);
    Language findById(int id); 
}
