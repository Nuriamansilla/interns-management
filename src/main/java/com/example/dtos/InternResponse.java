package com.example.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record InternResponse(

          @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) int id,

          String name,
          String surname1,
          String surname2,
          Long globalId,
          String gender,
          String center,

          // campos para mapear la entidad academic information, a continuacion

          String title

) {
}
