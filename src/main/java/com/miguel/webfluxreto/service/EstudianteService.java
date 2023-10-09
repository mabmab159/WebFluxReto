package com.miguel.webfluxreto.service;

import com.miguel.webfluxreto.models.Estudiante;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EstudianteService {
    Flux<Estudiante> findAll();

    Mono<Estudiante> save(Estudiante estudiante);
}
