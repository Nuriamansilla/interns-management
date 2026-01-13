package com.example.controllers;

import java.util.ArrayList;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    
// SAVE

@PostMapping
@Transactional
    public  ResponseEntity<Map<String, Object>> saveIntern(@Valid @RequestBody Intern intern,
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

// UPDATE

@PutMapping("/{id}")
@Transactional
    public  ResponseEntity<Map<String, Object>> updateIntern(@Valid @RequestBody Intern intern,
        BindingResult results, @PathVariable(name = "id", required = true) Integer id ){

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        if (results.hasErrors()) {
            
            List<ObjectError> objectErrors = results.getAllErrors();

            List<String> errorMessage = new ArrayList<>();

            objectErrors.forEach(objectError -> errorMessage.add(objectError.getDefaultMessage()));

            responseAsMap.put("errors", errorMessage);
            responseAsMap.put("intern", intern);

            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }
        

        try {
            intern.setId(id);
            Intern internSaved = internService.save(intern);
            String message = "Intern has been successfully updated ";
            responseAsMap.put("message", message);
            responseAsMap.put("intern", internSaved);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String errorMessage = "The intern could not be updated and the most likely cause is: "
                                 + e.getMostSpecificCause();
            responseAsMap.put("several error", errorMessage);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return responseEntity;
    }



         
        
        

      

   
}
