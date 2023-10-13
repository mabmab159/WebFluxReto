package com.miguel.webfluxreto.controllers;

import com.miguel.webfluxreto.models.Estudiante;
import com.miguel.webfluxreto.service.IEstudianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Comparator;

@RestController
@RequestMapping("/estudiante")
@RequiredArgsConstructor
public class EstudianteController {

    private final IEstudianteService estudianteService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Estudiante>>> findAll(@RequestParam(defaultValue = "asc") String order) {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        order.equals("desc") ? estudianteService.findAll().sort(Comparator.reverseOrder())
                                : estudianteService.findAll().sort())
        );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Estudiante>> findById(@PathVariable("id") String id) {
        return estudianteService.findById(id).map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> save(@Valid @RequestBody Estudiante estudiante, final ServerHttpRequest request) {
        return estudianteService.save(estudiante).map(e ->
                ResponseEntity.created(
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

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Estudiante>> updateById(@PathVariable("id") String id
            , @Valid @RequestBody Estudiante estudiante) {
        return estudianteService.updateById(id, estudiante).map(e ->
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
        );
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteById(@PathVariable("id") String id) {
        return estudianteService.deleteById(id)
                .flatMap(e -> e ? Mono.just(ResponseEntity.noContent().build())
                        : Mono.just(ResponseEntity.notFound().build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
