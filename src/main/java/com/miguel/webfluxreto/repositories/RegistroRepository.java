package com.miguel.webfluxreto.repositories;

import com.miguel.webfluxreto.models.Registro;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RegistroRepository extends ReactiveMongoRepository<Registro, String> {
}
