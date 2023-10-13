package com.miguel.webfluxreto.service.Impl;

import com.miguel.webfluxreto.models.Estudiante;
import com.miguel.webfluxreto.repositories.EstudianteRepository;
import com.miguel.webfluxreto.service.IEstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements IEstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Override
    public Flux<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    @Override
    public Mono<Estudiante> findById(String id) {
        return estudianteRepository.findById(id);
    }

    @Override
    public Mono<Estudiante> save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public Mono<Estudiante> updateById(String id, Estudiante estudiante) {
        return estudianteRepository.findById(id).flatMap(e -> {
            if (e != null) {
                estudiante.setId(id);
            }
            return estudianteRepository.save(estudiante);
        });
    }

    @Override
    public Mono<Boolean> deleteById(String id) {
        return estudianteRepository.findById(id).hasElement().flatMap(e ->
                e ? estudianteRepository.deleteById(id).thenReturn(true) : Mono.just(false));
    }
}
