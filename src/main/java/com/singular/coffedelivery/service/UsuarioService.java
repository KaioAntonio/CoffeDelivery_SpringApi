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

    public PageDTO<UsuarioDTO> buscar(Integer pagina, Integer tamanho) throws RegraDeNegocioException {

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

    public Optional<UsuarioEntity> buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioDTO criar(UsuarioCreateDTO usuarioCreateDTO){
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        String encode = passwordEncoder.encode(usuarioEntity.getSenha());
        usuarioEntity.setSenha(encode);
        usuarioRepository.save(usuarioEntity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        usuarioDTO.setIdUsuario(usuarioEntity.getIdUsuario());
        return usuarioDTO;
    }

    public UsuarioDTO atualizar(Integer idUsuario,UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity recuperaUsuario = buscarPorId(idUsuario);
        recuperaUsuario.setNome(usuarioCreateDTO.getNome());
        usuarioRepository.save(recuperaUsuario);
        return objectMapper.convertValue(recuperaUsuario, UsuarioDTO.class);
    }

    public void deletar(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = buscarPorId(idUsuario);
        usuarioRepository.delete(usuarioEntity);
    }

    public Integer buscarUsuarioLogadoPorId(){
        return Integer.parseInt( (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public UsuarioDTO buscarUsuarioLogado() throws RegraDeNegocioException {
        UsuarioEntity usuarioLogado = buscarPorId(buscarUsuarioLogadoPorId());
        return objectMapper.convertValue(usuarioLogado,UsuarioDTO.class);
    }

    public UsuarioEntity buscarPorId(Integer idUsuario) throws RegraDeNegocioException{
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new RegraDeNegocioException("Usuario nao encontrado"));

    }

    public UsuarioDTO buscarPorIdDto(Integer idUsuario) throws RegraDeNegocioException{
        UsuarioEntity usuarioEntity = usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new RegraDeNegocioException("Usuario nao encontrado"));
        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }
}
