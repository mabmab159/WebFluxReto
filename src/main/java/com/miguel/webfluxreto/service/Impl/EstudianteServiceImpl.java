package com.miguel.webfluxreto.service.Impl;

import com.miguel.webfluxreto.models.Estudiante;
import com.miguel.webfluxreto.repositories.EstudianteRepository;
import com.miguel.webfluxreto.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Override
    public Flux<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    @Override
    public Mono<Estudiante> save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }
}
