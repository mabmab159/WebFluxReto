package com.miguel.webfluxreto.service.Impl;

import com.miguel.webfluxreto.models.Usuario;
import com.miguel.webfluxreto.repositories.RolRepo;
import com.miguel.webfluxreto.repositories.UsuarioRepo;
import com.miguel.webfluxreto.security.User;
import com.miguel.webfluxreto.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUsuarioService {
    private final UsuarioRepo usuarioRepo;
    private final RolRepo rolRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Mono<Usuario> saveHash(Usuario usuario) {
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        return usuarioRepo.save(usuario);
    }

    @Override
    public Mono<User> searchByUser(String username) {
        Mono<Usuario> monoUser = usuarioRepo.findOneByUsername(username);
        List<String> roles = new ArrayList<>();
        return monoUser.flatMap(u -> Flux.fromIterable(u.getRoles())
                        .flatMap(rol -> rolRepo.findById(rol.getId())
                                .map(r -> {
                                    roles.add(r.getName());
                                    return r;
                                })).collectList().flatMap(list -> {
                            u.setRoles(list);
                            return Mono.just(u);
                        }))
                .flatMap(u ->
                        Mono.just(new User(u.getUsername()
                                , u.getPassword()
                                , u.isStatus(), roles)));
    }
}
