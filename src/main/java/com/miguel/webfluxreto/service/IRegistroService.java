package com.miguel.webfluxreto.service;

import com.miguel.webfluxreto.models.Registro;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IRegistroService {
    Flux<Registro> findAll();

    Mono<Registro> findById(String estudiante);

    Mono<Registro> save(Registro registroMatricula);

    Mono<Boolean> deleteById(String id);
}
