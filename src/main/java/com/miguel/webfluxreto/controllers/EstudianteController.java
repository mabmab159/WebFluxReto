package com.miguel.webfluxreto.controllers;

import com.miguel.webfluxreto.models.Estudiante;
import com.miguel.webfluxreto.service.EstudianteService;
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

    private final EstudianteService estudianteService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Estudiante>>> findAll(@RequestParam(defaultValue = "asc") String order) {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        order.equals("desc") ? estudianteService.findAll().sort(Comparator.reverseOrder())
                                : estudianteService.findAll().sort())
        );
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
}
