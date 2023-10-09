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
@Document(collection = "estudiante")
public class Estudiante {
    @Id
    private String id;
    @NotBlank
    @Size(min = 4)
    private String nombres;
    @NotBlank
    @Size(min = 4)
    private String apellidos;
    @NotBlank
    @Size(min = 8)
    private String dni;
    @Min(value = 1)
    private int edad;
}
