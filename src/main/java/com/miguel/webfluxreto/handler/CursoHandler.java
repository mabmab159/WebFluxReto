package com.miguel.webfluxreto.handler;

import com.miguel.webfluxreto.models.Curso;
import com.miguel.webfluxreto.service.CursoService;
import com.miguel.webfluxreto.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CursoHandler {
    private final CursoService cursoService;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> findAll(ServerRequest req) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cursoService.findAll(), Curso.class);
    }
}
