package com.miguel.webfluxreto.service;

import com.miguel.webfluxreto.models.Usuario;
import com.miguel.webfluxreto.security.User;
import reactor.core.publisher.Mono;

public interface IUsuarioService {
    Mono<Usuario> saveHash(Usuario usuario);

    Mono<User> searchByUser(String username);
}
