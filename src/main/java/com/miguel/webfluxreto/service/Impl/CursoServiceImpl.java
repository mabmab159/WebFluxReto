package com.miguel.webfluxreto.service.Impl;

import com.miguel.webfluxreto.models.Curso;
import com.miguel.webfluxreto.repositories.CursoRepository;
import com.miguel.webfluxreto.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    @Override
    public Flux<Curso> findAll() {
        return cursoRepository.findAll();
    }

    @Override
    public Mono<Curso> findById(String id) {
        return cursoRepository.findById(id);
    }

    @Override
    public Mono<Curso> save(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public Mono<Curso> updateById(String id, Curso curso) {
        return cursoRepository.findById(id).flatMap(e ->
        {
            if (e != null)
                curso.setId(id);
            return cursoRepository.save(curso);
        });
    }

    @Override
    public Mono<Boolean> deleteById(String id) {
        return cursoRepository.findById(id).hasElement().flatMap(e ->
                e ? cursoRepository.deleteById(id).thenReturn(true) : Mono.just(false));
    }
}
