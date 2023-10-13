package com.miguel.webfluxreto.handler;

import com.miguel.webfluxreto.models.Estudiante;
import com.miguel.webfluxreto.service.IEstudianteService;
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
public class EstudianteHandler {
    private final IEstudianteService estudianteService;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> findAll(ServerRequest req) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(estudianteService.findAll(), Estudiante.class);
    }

    public Mono<ServerResponse> findById(ServerRequest req) {
        String id = req.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(estudianteService.findById(id), Estudiante.class);
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Estudiante.class)
                .flatMap(this.requestValidator::validate)
                .flatMap(estudianteService::save)
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
        return req.bodyToMono(Estudiante.class)
                .map(e -> {
                    Mono<Estudiante> p = estudianteService.findById(id);
                    if (p != null) {
                        e.setId(id);
                    }
                    return e;
                })
                .flatMap(this.requestValidator::validate)
                .flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(estudianteService.save(p), Estudiante.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        String id = req.pathVariable("id");
        return estudianteService.deleteById(id)
                .flatMap(e ->
                        e ? ServerResponse.noContent().build() :
                                ServerResponse.notFound().build()
                );
    }
}
