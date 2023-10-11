package com.miguel.webfluxreto.service.Impl;

import com.miguel.webfluxreto.models.Curso;
import com.miguel.webfluxreto.models.Registro;
import com.miguel.webfluxreto.repositories.CursoRepository;
import com.miguel.webfluxreto.repositories.EstudianteRepository;
import com.miguel.webfluxreto.repositories.RegistroRepository;
import com.miguel.webfluxreto.service.RegistroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistroServiceImpl implements RegistroService {

    private final RegistroRepository registroRepository;
    private final CursoRepository cursoRepository;
    private final EstudianteRepository estudianteRepository;

    @Override
    public Flux<Registro> findAll() {
        return registroRepository.findAll()
                .publishOn(Schedulers.boundedElastic())
                .mapNotNull(e -> findById(e.getId()).block());
    }

    @Override
    public Mono<Registro> findById(String id) {
        return registroRepository.findById(id)
                .flatMap(this::llenarEstudiante)
                .flatMap(this::llenarCurso);
    }

    @Override
    public Mono<Registro> save(Registro registro) {
        return registroRepository.save(registro);
    }

    @Override
    public Mono<Boolean> deleteById(String id) {
        return registroRepository.findById(id).hasElement().flatMap(e ->
                e ? registroRepository.deleteById(id).thenReturn(true) : Mono.just(false));
    }

    private Mono<Registro> llenarEstudiante(Registro registro) {
        return estudianteRepository.findById(registro.getEstudiante().getId())
                .map(estudiante -> {
                    registro.setEstudiante(estudiante);
                    return registro;
                });
    }

    private Mono<Registro> llenarCurso(Registro registro) {
        List<Mono<Curso>> list = registro.getCursos().stream()
                .map(item -> cursoRepository.findById(item.getId())
                        .map(curso -> {
                            item.setNombre(curso.getNombre());
                            item.setSiglas(curso.getSiglas());
                            item.setEstado(curso.getEstado());
                            return item;
                        })
                ).toList();
        return Mono.when(list).then(Mono.just(registro));
    }
}
