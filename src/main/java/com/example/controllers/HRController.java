package com.example.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Intern;
import com.example.services.HRfeedbackService;
import com.example.services.InternService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hrfeedbacks")
@RequiredArgsConstructor
public class HRController {

   private final HRfeedbackService hRfeedbackService;
   private final InternService internService;

@GetMapping("/interns/{globalID}")
public ResponseEntity<Map<String, Object>> findInternByGlobalIDforHR(
        @PathVariable(name = "globalID", required = true) long globalID) {

    ResponseEntity<Map<String, Object>> responseEntity = null;
        var responseAsMap = new HashMap<String, Object>();

try {
            Intern intern = internService.findByGlobalID(globalID);
            if (intern !=null) {
                String successMessage = "Intern with global ID " + globalID + " has been found";
                responseAsMap.put("message", successMessage);
                responseAsMap.put("intern", intern);
                responseAsMap.put("feedback", intern.getHrfeedbacks());
                responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
            } else {
                 String notFoundMessage = "Intern with global ID " + globalID + " has not been found"; 
                responseAsMap.put("notFoundMessage", notFoundMessage);
                responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.NOT_FOUND);     
            }
        } catch (DataAccessException e) {
           String errorMessage = "Several error and the most likely cause is: " + e.getMostSpecificCause().getMessage();
            responseAsMap.put("message", errorMessage); 
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }



   return responseEntity; 
}



}