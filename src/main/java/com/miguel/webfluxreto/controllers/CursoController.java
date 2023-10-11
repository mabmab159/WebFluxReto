package com.miguel.webfluxreto.controllers;

import com.miguel.webfluxreto.models.Curso;
import com.miguel.webfluxreto.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/curso")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Curso>>> findAll() {
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cursoService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Curso>> findById(@PathVariable("id") String id) {
        return cursoService.findById(id).map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> save(@Valid @RequestBody Curso curso, final ServerHttpRequest request) {
        return cursoService.save(curso)
                .map(e -> ResponseEntity.created(
                                URI.create(request.getURI()
                                        .toString()
                                        .concat("/")
                                        .concat(e.getId())
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteById(@PathVariable("id") String id) {
        return cursoService.deleteById(id)
                .flatMap(e -> e ? Mono.just(ResponseEntity.noContent().build())
                        : Mono.just(ResponseEntity.notFound().build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
