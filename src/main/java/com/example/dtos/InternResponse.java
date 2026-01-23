package com.example.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record InternResponse(

     int id,
     String name,
     String surname1,
     String surname2,
     Long globalID,
     String gender,
     String center, 

     // campos para mapear la entidad academic information , a continuacion 

     String title 

) {}
