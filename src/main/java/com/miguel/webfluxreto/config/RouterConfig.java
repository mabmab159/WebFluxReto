package com.miguel.webfluxreto.config;

import com.miguel.webfluxreto.handler.CursoHandler;
import com.miguel.webfluxreto.handler.EstudianteHandler;
import com.miguel.webfluxreto.handler.RegistroHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routesCurso(CursoHandler cursoHandler) {
        return route(GET("/v2/curso"), cursoHandler::findAll)
                .andRoute(GET("/v2/curso/{id}"), cursoHandler::findById)
                .andRoute(POST("/v2/curso"), cursoHandler::create)
                .andRoute(PUT("/v2/curso/{id}"), cursoHandler::updateById)
                .andRoute(DELETE("/v2/curso/{id}"), cursoHandler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> routesEstudiante(EstudianteHandler estudianteHandler) {
        return route(GET("/v2/estudiante"), estudianteHandler::findAll)
                .andRoute(GET("/v2/estudiante/{id}"), estudianteHandler::findById)
                .andRoute(POST("/v2/estudiante"), estudianteHandler::create)
                .andRoute(PUT("/v2/estudiante/{id}"), estudianteHandler::updateById)
                .andRoute(DELETE("/v2/estudiante/{id}"), estudianteHandler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> routesRegistro(RegistroHandler registroHandler) {
        return route(GET("/v2/registro"), registroHandler::findAll)
                .andRoute(GET("/v2/registro/{id}"), registroHandler::findById)
                .andRoute(POST("/v2/registro"), registroHandler::create)
                .andRoute(PUT("/v2/registro/{id}"), registroHandler::updateById)
                .andRoute(DELETE("/v2/registro/{id}"), registroHandler::delete);
    }
}
