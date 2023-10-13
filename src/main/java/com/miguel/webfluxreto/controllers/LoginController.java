package com.miguel.webfluxreto.controllers;

import com.miguel.webfluxreto.security.AuthRequest;
import com.miguel.webfluxreto.security.AuthResponse;
import com.miguel.webfluxreto.security.ErrorLogin;
import com.miguel.webfluxreto.security.JwtUtil;
import com.miguel.webfluxreto.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final JwtUtil jwtUtil;
    private final IUsuarioService service;

    @PostMapping
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest authRequest) {
        return service.searchByUser(authRequest.getUsername())
                .map(user -> {
                    if (BCrypt.checkpw(authRequest.getPassword(), user.getPassword())) {
                        String token = jwtUtil.generateToken(user);
                        Date expiration = jwtUtil.getExpirationDateFromToken(token);
                        return ResponseEntity.ok(
                                new AuthResponse(token, expiration)
                        );
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(new ErrorLogin("Bad Credentials", new Date()));
                    }
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
