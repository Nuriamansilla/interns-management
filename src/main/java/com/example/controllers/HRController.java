package com.example.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.HRfeedback;
import com.example.entities.Intern;
import com.example.services.HRfeedbackService;
import com.example.services.InternService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/interns")
@RequiredArgsConstructor
public class HRController {

   private final HRfeedbackService hRfeedbackService;
   private final InternService internService;
   
   private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   
   
   // ====== helpers privados (validación y utilidades) ======
    private static String asString(Map<String, Object> map, String key) {
        Object v = map.get(key);
        return v == null ? null : v.toString();
    }

    
      private static boolean isValidIsoDate(String date) {
        return date != null && date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private static Map<String, Object> errorBody(String message) {
        Map<String, Object> m = new HashMap<>();
        m.put("message", message);
        return m;
}


      // ============ Añadir UN feedback ============
      // POST /interns/{globalId}/feedbacks  -> añade UN feedback por llamada
    // Body esperado: { "name": "...", "date": "DD/MM/YYYY", "hrUser": "...", "comments": "..." }

    @PostMapping("/{globalId}/hRfeedbacks")
    public ResponseEntity<Map<String, Object>> addOne(
            @PathVariable(name = "globalId", required = true) Long globalId,
            @RequestBody Map<String, Object> body) {

        // Mapa de respuesta
        var map = new HashMap<String, Object>();

        try {
            // 1) Verificar que el intern existe
            Intern intern = internService.findByGlobalID(globalId);
          
         if (intern == null) {
                return new ResponseEntity<>(errorBody("El intern con globalId " + globalId + " no se ha encontrado"),
                        HttpStatus.NOT_FOUND);
            }

            // 2) Extraer campos desde el body (sin DTO) y validar manualmente
            String nameFeedback = asString(body, "nameFeedback");
            String dateOfFeedBack = asString(body, "dateOfFeedBack");
            String hrUser = asString(body, "hrUser");
            String comments = asString(body, "comments");

            if (nameFeedback== null || dateOfFeedBack == null || hrUser == null || comments == null) {
            
                   return new ResponseEntity<>(errorBody
                    ("Faltan campos obligatorios: name, date, hrUser, comments"),
                        HttpStatus.BAD_REQUEST);
            };

            if (!isValidIsoDate(dateOfFeedBack)) {

                return new ResponseEntity<>(errorBody("date debe tener formato DD/MM/YYYY"),
                        HttpStatus.BAD_REQUEST);
            }
            
            if (comments.length() > 1000) {

                return new ResponseEntity<>(errorBody("comments no puede superar 1000 caracteres"),
                        HttpStatus.BAD_REQUEST);
            }
           
          // 3) Construir la entidad Feedback y persistir
            HRfeedback f = HRfeedback.builder()
                  .intern(intern)
                  .nameFeedback(nameFeedback)
                  .dateOfFeedBack(LocalDate.parse(dateOfFeedBack, ISO_DATE))
                  .hrUser(hrUser)
                  .comments(comments)
                  .build();
            

            HRfeedback saved = hRfeedbackService.save(f); // <-- UNO: save(...)

            // 4) Respuesta OK
            map.put("message", "Se ha añadido el feedback al intern con globalId " + globalId);

            
               map.put("feedbackId", saved.getId());

            return ResponseEntity
                    .created(URI.create("/interns/" + globalId + "/feedbacks/" + saved.getId()))
                    .body(map);

             } catch (DataAccessException e) {
            // Errores de BBDD
            map.put("message", "Error grave y la causa más probable es: " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    

}
}