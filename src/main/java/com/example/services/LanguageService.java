package com.example.services;

import java.util.List;

import com.example.entities.Language;

public interface LanguageService {
    List<Language> findAll();
    Language saveLanguage(Language language);
    Language findByIdLanguage(int id); 

}
