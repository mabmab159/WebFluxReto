package com.miguel.webfluxreto.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "curso")
public class Curso {
    @Id
    private String id;
    @NotBlank
    @Size(min = 4)
    private String nombre;
    @NotBlank
    @Size(min = 2)
    private String siglas;
    @NotNull
    @NotBlank
    private Boolean estado;
}
