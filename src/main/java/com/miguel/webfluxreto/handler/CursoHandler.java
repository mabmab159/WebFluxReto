package com.miguel.webfluxreto.handler;

import com.miguel.webfluxreto.models.Curso;
import com.miguel.webfluxreto.service.ICursoService;
import com.miguel.webfluxreto.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class CursoHandler {
    private final ICursoService cursoService;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> findAll(ServerRequest req) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cursoService.findAll(), Curso.class);
    }

    public Mono<ServerResponse> findById(ServerRequest req) {
        String id = req.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cursoService.findById(id), Curso.class);
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Curso.class)
                .flatMap(this.requestValidator::validate)
                .flatMap(cursoService::save)
                .flatMap(e -> ServerResponse.created(
                                        URI.create(req.uri()
                                                .toString()
                                                .concat("/")
                                                .concat(e.getId()))
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(e))
                );
    }

    public Mono<ServerResponse> updateById(ServerRequest req) {
        String id = req.pathVariable("id");
        return req.bodyToMono(Curso.class)
                .map(e -> {
                    Mono<Curso> p = cursoService.findById(id);
                    if (p != null) {
                        e.setId(id);
                    }
                    return e;
                })
                .flatMap(this.requestValidator::validate)
                .flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(cursoService.save(p), Curso.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        String id = req.pathVariable("id");
        return cursoService.deleteById(id)
                .flatMap(e ->
                        e ? ServerResponse.noContent().build() :
                                ServerResponse.notFound().build()
                );
    }
}
