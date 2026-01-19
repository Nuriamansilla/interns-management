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

import org.springframework.web.bind.annotation.DeleteMapping;
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

    // READ

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


    // FIND BY GLOBAL ID
    
    @GetMapping("/{globalID}")
    public ResponseEntity<Map<String, Object>> findInternByGlobalID(
        @PathVariable(name = "globalID", required = true) long globalID) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        var responseAsMap = new HashMap<String, Object>();

        try {
            Intern intern = internService.findByGlobalID(globalID);
            if (intern !=null) {
                String successMessage = "Intern with global ID " + globalID + " has been found";
                responseAsMap.put("message", successMessage);
                responseAsMap.put("intern", intern);
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

    
    // SAVE

    @PostMapping
    @Transactional
    public  ResponseEntity<Map<String, Object>> saveIntern(@Valid @RequestBody Intern intern,
        BindingResult results) {

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
            String message = "Intern has been successfully created";
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

    @PutMapping("/{globalID}")
    @Transactional
    public  ResponseEntity<Map<String, Object>> updateIntern(@Valid @RequestBody Intern intern,
        BindingResult results, @PathVariable(name = "globalID", required = true) Long globalID ){

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
            intern.setGlobalID(globalID);
            Intern internSaved = internService.save(intern);
            String message = "Intern has been successfully updated";
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


    // DELETE    
        
    @DeleteMapping("/{globalID}")
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteIntern(@PathVariable(name="globalID", required=true) long globalID) {
       
        ResponseEntity<Map<String, Object>> responseEntity = null;
        var responseAsMap = new HashMap<String, Object>();
 
        try {
            internService.delete(internService.findByGlobalID(globalID));
            String successMessage = "The intern with global ID " + globalID + " has been deleted.";
            responseAsMap.put("message", successMessage);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
        } catch (DataAccessException e) {
            String errorMessage = "The intern with global ID " + globalID + " could not be deleted, the most likely cause of the error is:"
                                  + e.getMostSpecificCause();
            responseAsMap.put("message", errorMessage);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
 
        return responseEntity;
    }



    //Busqueda por nombre http://localhost:8080/interns/interns-by-name?name=nuria

     @GetMapping(path = "/interns-by-name")
    public ResponseEntity<Map<String, Object>> findInternByName(
        @RequestParam(name = "name", required = true) String name) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        var responseAsMap = new HashMap<String, Object>();

        try {
            Intern intern = internService.findByName(name);
            if (intern !=null) {
                String successMessage = "Intern with name " + name + " has been found";
                responseAsMap.put("message", successMessage);
                responseAsMap.put("internList", intern);
                responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
            } else {
                 String notFoundMessage = "Intern with name" + name + " has not been found"; 
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

     //Busqueda por primer apellido  http://localhost:8080/interns/interns-by-surname?surname1=belamria
     
     @GetMapping(path = "/interns-by-surname")
    public ResponseEntity<Map<String, Object>> findInternBySurname1(
        @RequestParam(name = "surname1", required = true) String surname1) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        var responseAsMap = new HashMap<String, Object>();

        try {
            Intern intern = internService.findBySurname1(surname1);
            if (intern !=null) {
                String successMessage = "Intern with surname " + surname1 + " has been found";
                responseAsMap.put("message", successMessage);
                responseAsMap.put("internList", intern);
                responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
            } else {
                 String notFoundMessage = "Intern with surname" + surname1 + " has not been found"; 
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
