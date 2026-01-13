package com.example.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Intern;
import com.example.services.InternService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/interns")
@RequiredArgsConstructor
public class InternController {

    private final InternService internService; 

    @GetMapping
    public ResponseEntity<List<Intern>> getInterns( 
        @RequestParam (name = "page", required = false) Integer page,
        @RequestParam(name = "size", required = false) Integer size) {
            List<Intern> interns = null; 
            Sort sort = Sort.by("name");

            if (page !=null && size !=null) {
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Intern> paginaDeInterns = internService.findAll(pageable);
            interns = paginaDeInterns.getContent();     
            }else{
                interns = internService.findAll(sort);
            }

        return new ResponseEntity<List<Intern>>(interns, HttpStatus.OK); 
    }


    
    @GetMapping("/{id}")
     public ResponseEntity<Map<String, Object>> findInternById(
        @PathVariable(name = "id", required = true) int id) {
        
        ResponseEntity<Map<String, Object>> responseEntity = null;
        var responseAsMap = new HashMap<String, Object>();

        try {
            Intern intern = internService.findById(id);
            if (intern !=null) {
                String successMessage = "Se ha encontrado el intern con id" + id;
                responseAsMap.put("mensaje", successMessage);
                responseAsMap.put("intern", intern);
                responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
            } else {
                 String notFoundMessage = "El intern con id" + id + ", no ha sido encontrado"; 
                responseAsMap.put("notFoundMessage", notFoundMessage);
                responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.NOT_FOUND);     
            }
        } catch (DataAccessException e) {
           String errorMessage = "Error grave y la causa mas probable es: " + e.getMostSpecificCause().getMessage();
            responseAsMap.put("mensaje", errorMessage); 
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity; 
     }

}
