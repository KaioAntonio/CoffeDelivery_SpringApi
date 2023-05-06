package com.singular.coffedelivery.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singular.coffedelivery.dto.usuario.UsuarioCreateDTO;
import com.singular.coffedelivery.dto.usuario.UsuarioDTO;
import com.singular.coffedelivery.entity.UsuarioEntity;
import com.singular.coffedelivery.exception.RegraDeNegocioException;
import com.singular.coffedelivery.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    private final PasswordEncoder passwordEncoder;
    public Optional<UsuarioEntity> findByEmailAndSenha(String email, String senha){
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }
    public Optional<UsuarioEntity> findByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO){
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        String encode = passwordEncoder.encode(usuarioEntity.getSenha());
        usuarioEntity.setSenha(encode);
        usuarioRepository.save(usuarioEntity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        usuarioDTO.setIdUsuario(usuarioEntity.getIdUsuario());
        return usuarioDTO;
    }


    public Integer getIdLoggedUser(){
        return Integer.parseInt( (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public UsuarioDTO getLoggedUser() throws RegraDeNegocioException {
        UsuarioEntity usuarioLogado = findByIdEntity(getIdLoggedUser());
        return objectMapper.convertValue(usuarioLogado,UsuarioDTO.class);
    }

    public UsuarioEntity findByIdEntity(Integer idUsuario) throws RegraDeNegocioException{
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new RegraDeNegocioException("Usuario nao encontrado"));
    }

}
