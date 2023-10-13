package com.miguel.webfluxreto.repositories;

import com.miguel.webfluxreto.models.Usuario;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UsuarioRepo extends ReactiveMongoRepository<Usuario, String> {
    Mono<Usuario> findOneByUsername(String username);
}
