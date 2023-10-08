package com.miguel.webfluxreto.repositories;

import com.miguel.webfluxreto.models.Curso;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CursoRepository extends ReactiveMongoRepository<Curso, String> {
}
