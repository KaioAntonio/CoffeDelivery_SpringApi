package com.singular.coffedelivery.controller;

import com.singular.coffedelivery.controller.interfaces.UsuarioControllerInterface;
import com.singular.coffedelivery.dto.PageDTO;
import com.singular.coffedelivery.dto.usuario.UsuarioCreateDTO;
import com.singular.coffedelivery.dto.usuario.UsuarioDTO;
import com.singular.coffedelivery.exception.RegraDeNegocioException;
import com.singular.coffedelivery.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("usuario")
public class UsuarioController implements UsuarioControllerInterface {
    private final UsuarioService usuarioService;


    @Override
    public ResponseEntity<PageDTO<UsuarioDTO>> listUsers(Integer pagina, Integer tamanho){
        return new ResponseEntity<>(usuarioService.listUsers(pagina,tamanho), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UsuarioDTO> findById(@PathVariable("id") Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.findByIdDto(idUsuario),HttpStatus.OK);
    }
    @Override
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) {
        return new ResponseEntity<>(usuarioService.create(usuarioCreateDTO), HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<UsuarioDTO> updateUser(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO, @PathVariable("id") Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.update(idUsuario,usuarioCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer idProduto) throws RegraDeNegocioException {
        usuarioService.delete(idProduto);
        return ResponseEntity.ok().build();
    }
}
