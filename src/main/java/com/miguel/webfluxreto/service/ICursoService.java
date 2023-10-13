package com.miguel.webfluxreto.service;

import com.miguel.webfluxreto.models.Curso;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICursoService {
    Flux<Curso> findAll();
    Mono<Curso> findById(String id);
    Mono<Curso> save(Curso curso);
    Mono<Curso> updateById(String id, Curso curso);
    Mono<Boolean> deleteById(String id);
}
