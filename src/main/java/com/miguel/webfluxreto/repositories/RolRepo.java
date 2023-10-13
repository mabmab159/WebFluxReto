package com.miguel.webfluxreto.repositories;

import com.miguel.webfluxreto.models.Rol;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RolRepo extends ReactiveMongoRepository<Rol, String> {
}
