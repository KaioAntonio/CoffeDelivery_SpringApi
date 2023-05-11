package com.singular.coffedelivery.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singular.coffedelivery.dto.PageDTO;
import com.singular.coffedelivery.dto.usuario.UsuarioCreateDTO;
import com.singular.coffedelivery.dto.usuario.UsuarioDTO;
import com.singular.coffedelivery.entity.UsuarioEntity;
import com.singular.coffedelivery.exception.RegraDeNegocioException;
import com.singular.coffedelivery.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
<<<<<<< HEAD

    public PageDTO<UsuarioDTO> listUsers(Integer pagina, Integer tamanho) {

        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);
        Page<UsuarioEntity> usuario = usuarioRepository.findAll(solicitacaoPagina);
        List<UsuarioDTO> usuarioDTO = usuario.getContent().stream()
                .map(x -> objectMapper.convertValue(x, UsuarioDTO.class))
                .collect(Collectors.toList());

        return new PageDTO<>(usuario.getTotalElements(),
                usuario.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTO);
    }
    //TODO
    public Optional<UsuarioEntity> findByEmailAndSenha(String email, String senha){
        return usuarioRepository.findByEmailAndSenha(email, senha);
=======
    public Optional<UsuarioEntity> findByEmailAndSenha(String email, String senha) throws RegraDeNegocioException {
        return Optional.ofNullable(usuarioRepository.findByEmailAndSenha(email, senha)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado!")));
>>>>>>> feat/produto
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

    public UsuarioDTO update(Integer idUsuario,UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity recuperaUsuario = findByIdEntity(idUsuario);
        recuperaUsuario.setNome(usuarioCreateDTO.getNome());
        usuarioRepository.save(recuperaUsuario);
        return objectMapper.convertValue(recuperaUsuario, UsuarioDTO.class);
    }

    public void delete(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findByIdEntity(idUsuario);
        usuarioRepository.delete(usuarioEntity);
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

    public UsuarioDTO findByIdDto(Integer idUsuario) throws RegraDeNegocioException{
        UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new RegraDeNegocioException("Usuario nao encontrado"));
        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }
}
