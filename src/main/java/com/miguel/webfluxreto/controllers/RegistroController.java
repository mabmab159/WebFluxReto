package com.miguel.webfluxreto.controllers;

import com.miguel.webfluxreto.models.Registro;
import com.miguel.webfluxreto.service.RegistroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/registro")
@RequiredArgsConstructor
public class RegistroController {
    private final RegistroService registroService;
    @GetMapping
    public Mono<ResponseEntity<Flux<Registro>>> findAll() {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(registroService.findAll())
        );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Registro>> findById(@PathVariable("id") String id) {
        return registroService.findById(id)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                );
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> save(@RequestBody Registro registro, final ServerHttpRequest request) {
        registro.setFechaMatricula(LocalDateTime.now());
        return registroService.save(registro)
                .map(e -> ResponseEntity.created(
                                        URI.create(
                                                request.getURI()
                                                        .toString()
                                                        .concat("/")
                                                        .concat(e.getId())
                                        )
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .build()
                );
    }



}
