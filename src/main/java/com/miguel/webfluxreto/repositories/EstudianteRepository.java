package com.miguel.webfluxreto.repositories;

import com.miguel.webfluxreto.models.Estudiante;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EstudianteRepository extends ReactiveMongoRepository<Estudiante, String> {
}
