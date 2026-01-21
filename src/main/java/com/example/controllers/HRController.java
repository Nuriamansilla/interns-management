package com.example.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    
   // ******************************************************
   // ADD A FEEDBACK
   // ******************************************************
   // ******************************************************
   // POST http://localhost:8080/interns/556677889/hRfeedbacks donde globalID es 55667799 *****

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

        //******************************************************************
        // ===================== US 2.1 – HR profile =====================
                // Muestra SOLO lo que pide la user story (y feedbacks)
        // GET   http://localhost:8080/interns/556677889/hr-profile ********* donde globalID es 55667799 *****
        // *********************************************+++*******************   

   
@GetMapping("/{globalId}/hr-profile")
    public ResponseEntity<Map<String, Object>> getHrProfile(
            @PathVariable("globalId") Long globalId) {

        var map = new HashMap<String, Object>();

        
try {
            // 1) Verificar intern por globalID
            Intern intern = internService.findByGlobalID(globalId);
            if (intern == null) {
                return new ResponseEntity<>(
                        errorBody("El intern con globalId " + globalId + " no se ha encontrado"),
                        HttpStatus.NOT_FOUND
                );
            }

            // 2) Cargar feedbacks ordenados desc por fecha
            List<HRfeedback> hRfeedbacks = hRfeedbackService.findAllByInternGlobalIdOrdered(globalId);

            // 3) Construir el "profile" mínimo que pide la US
            Map<String, Object> profile = new LinkedHashMap<>();
            profile.put("name", intern.getName());
            profile.put("surname1", intern.getSurname1());
            profile.put("surname2", intern.getSurname2());
            profile.put("globalID", intern.getGlobalID());     // respeta tu casing
            profile.put("gender", intern.getGender().name());
            profile.put("center", intern.getCenter().name());

            // Mapear feedbacks -> List<Map<String,Object>>
            List<Map<String, Object>> fbList = new ArrayList<>();
            for (HRfeedback f : hRfeedbacks) {
                Map<String, Object> fMap = new LinkedHashMap<>();
                fMap.put("id", f.getId());
                fMap.put("nameFeedback", f.getNameFeedback());
                fMap.put("dateOfFeedBack", f.getDateOfFeedBack().format(ISO_DATE));
                fMap.put("hrUser", f.getHrUser());
                fMap.put("comments", f.getComments());
                fbList.add(fMap);
            }
            profile.put("hRfeedbacks", fbList);

            // 4) Tu patrón de respuesta
            map.put("message", "Perfil HR del intern " + globalId);
            map.put("profile", profile);
            return ResponseEntity.ok(map);

        } catch (DataAccessException e) {
            map.put("message", "Error grave y la causa más probable es: " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}