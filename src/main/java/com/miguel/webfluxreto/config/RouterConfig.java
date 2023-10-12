package com.miguel.webfluxreto.config;

import com.miguel.webfluxreto.handler.CursoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(CursoHandler cursoHandler) {
        return route(GET("/v2/curso"), cursoHandler::findAll)
                .andRoute(GET("/v2/curso/{id}"), cursoHandler::findById)
                .andRoute(POST("/v2/curso/{id}"), cursoHandler::create)
                .andRoute(PUT("/v2/curso/{id}"), cursoHandler::updateById)
                .andRoute(DELETE("/v2/curso/{id}"), cursoHandler::delete);
    }
}
