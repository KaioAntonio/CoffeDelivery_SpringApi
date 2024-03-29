package com.singular.coffedelivery.config.security;


import com.singular.coffedelivery.domain.entity.UsuarioEntity;
import com.singular.coffedelivery.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioEntity> usuarioEntity = usuarioService.buscarPorEmail(username);
        return usuarioEntity
                .orElseThrow(() -> new UsernameNotFoundException("Usuário inválido!"));
    }
}