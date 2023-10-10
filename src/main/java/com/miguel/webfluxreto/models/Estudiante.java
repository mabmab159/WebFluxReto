package com.miguel.webfluxreto.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "estudiante")
public class Estudiante implements Comparable<Estudiante> {
    @Id
    @EqualsAndHashCode.Include
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

    @Override
    public int compareTo(Estudiante o) {
        return Integer.compare(this.getEdad(), o.getEdad());
    }
}
