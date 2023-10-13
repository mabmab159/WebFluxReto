package com.miguel.webfluxreto.handler;

import com.miguel.webfluxreto.models.Registro;
import com.miguel.webfluxreto.service.IRegistroService;
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
public class RegistroHandler {
    private final IRegistroService registroService;
    private final RequestValidator requestValidator;

    public Mono<ServerResponse> findAll(ServerRequest req) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(registroService.findAll(), Registro.class);
    }

    public Mono<ServerResponse> findById(ServerRequest req) {
        String id = req.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(registroService.findById(id), Registro.class);
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(Registro.class)
                .flatMap(this.requestValidator::validate)
                .flatMap(registroService::save)
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
        return req.bodyToMono(Registro.class)
                .map(e -> {
                    Mono<Registro> p = registroService.findById(id);
                    if (p != null) {
                        e.setId(id);
                    }
                    return e;
                })
                .flatMap(this.requestValidator::validate)
                .flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(registroService.save(p), Registro.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        String id = req.pathVariable("id");
        return registroService.deleteById(id)
                .flatMap(e ->
                        e ? ServerResponse.noContent().build() :
                                ServerResponse.notFound().build()
                );
    }
}
