package com.miguel.webfluxreto.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "matriculas")
public class Registro {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    @NotNull
    @NotBlank
    private LocalDateTime fechaMatricula;
    @NotNull
    @NotEmpty
    private Estudiante estudiante;
    @NotNull
    @NotEmpty
    private List<Curso> cursos;
    @NotNull
    @NotBlank
    private Boolean estado;
}
