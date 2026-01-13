package com.example.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.services.InternService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/interns")
@RequiredArgsConstructor
public class InternController {

    private final InternService internService; 
    

    // DELETE

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteIntern(@PathVariable(name="id", required=true) int id) {
        
        ResponseEntity<Map<String, Object>> responseEntity = null;
        var responseAsMap = new HashMap<String, Object>();

        try {
            internService.delete(internService.findById(id));
            String successMessage = "The intern with id " + id + " has been deleted.";
            responseAsMap.put("message", successMessage);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
        } catch (DataAccessException e) {
            String errorMessage = "The product with id " + id + " could not be deleted, the most likely cause of the error is:"
                                  + e.getMostSpecificCause();
            responseAsMap.put("message", errorMessage);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }

}
