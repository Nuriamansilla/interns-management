package com.example.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Intern;
import com.example.services.InternService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/interns")
@RequiredArgsConstructor
public class InternController {

    private final InternService internService; 

    
// SAVE

@PostMapping
@Transactional
    public  ResponseEntity<Map<String, Object>> saveProduct(@Valid @RequestBody Intern intern,
        BindingResult results 
    ){

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        // Comprobar si el producto recibido en el cuerpo de la petición tiene errores

        if (results.hasErrors()) {
            
            //Recuperar todos los errores que tiene el producto recibido en formato JSON
            List<ObjectError> objectErrors = results.getAllErrors();

            /* Hay que recorrer la lista de ObjecError para recuperar los mensajes de error
            por defecto que voy a mostrar al cliente que ha realizado la petición, es decir,
            que ha enviado el producto mal formado */

            /*Los mensajes de error tienen que ser almacenados en una lista donde
            cada elemento de la lista sea un String*/
            List<String> errorMessage = new ArrayList<>();

            objectErrors.forEach(objectError -> errorMessage.add(objectError.getDefaultMessage()));

            responseAsMap.put("errors", errorMessage);
            responseAsMap.put("intern", intern);

            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }
        

        /* Si no hay errores vamos a persistir/guardar el intern recibido en el cuerpo de la petición
        y devolver información al respecto como se requiere para una API REST */

        try {
            Intern internSaved = internService.save(intern);
            String message = "Intern has been created successfully ";
            responseAsMap.put("mensaje", message);
            responseAsMap.put("intern", internSaved);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String errorMessage = "The intern could not be created and the most likely cause is: "
                                 + e.getMostSpecificCause();
            responseAsMap.put("several error", errorMessage);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return responseEntity;
    }



}
