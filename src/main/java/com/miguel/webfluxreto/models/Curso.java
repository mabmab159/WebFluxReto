package com.miguel.webfluxreto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "curso")
public class Curso {
    @Id
    private String id;
    @Field
    private String nombre;
    @Field
    private String siglas;
    @Field
    private Boolean estado;
}
