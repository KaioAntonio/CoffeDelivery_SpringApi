package com.singular.coffedelivery.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.singular.coffedelivery.domain.service.UsuarioService;
import com.singular.coffedelivery.domain.dto.PageDTO;
import com.singular.coffedelivery.domain.dto.usuario.UsuarioCreateDTO;
import com.singular.coffedelivery.domain.dto.usuario.UsuarioDTO;
import com.singular.coffedelivery.domain.entity.UsuarioEntity;
import com.singular.coffedelivery.config.exception.RegraDeNegocioException;
import com.singular.coffedelivery.domain.repository.UsuarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private SecurityContextHolder securityContextHolder;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }

    private final Integer pagina = 4;
    private final Integer quantidade = 10;

    @DisplayName("Deve testar buscar com sucesso")
    @Test
    public void deveTestarBuscarComSucesso() throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        List<UsuarioEntity> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(usuarioEntity);
        Page<UsuarioEntity> usuarioEntityPage = new PageImpl<>(listaUsuarios);
        when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(usuarioEntityPage);

        PageDTO<UsuarioDTO> usuarioEntityPageDTO = usuarioService.buscar(pagina, quantidade);

        assertNotNull(usuarioEntityPageDTO);
        assertEquals(1, usuarioEntityPageDTO.getElementos().size());
    }

    @DisplayName("Deve testar criar com sucesso")
    @Test
    public void deveTestarCriarComSucesso() {
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioDTO();
        when(passwordEncoder.encode(any())).thenReturn("@dakwkdawnt");
        when(usuarioRepository.save(any())).thenReturn(usuarioEntity);

        UsuarioCreateDTO usuarioCriado = usuarioService.criar(usuarioCreateDTO);

        assertNotNull(usuarioCriado);
        assertEquals("Kaio", usuarioCriado.getNome());
    }


    private UsuarioCreateDTO getUsuarioDTO() {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioCreateDTO.setEmail("kaio@gmail.com");
        usuarioCreateDTO.setSenha("1234");
        usuarioCreateDTO.setNome("kaio");
        return usuarioCreateDTO;
    }


    private UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setSenha("123");
        usuarioEntity.setNome("Kaio");
        usuarioEntity.setEmail("Kaio@gmail.com");
        return usuarioEntity;
    }



}
