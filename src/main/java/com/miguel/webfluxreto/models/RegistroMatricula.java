package com.miguel.webfluxreto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "matriculas")
public class RegistroMatricula {
    private LocalDateTime fechaMatricula;
    private Estudiante estudiantes;
    private List<Curso> cursos;
    private Boolean estado;
}
