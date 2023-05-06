package com.singular.coffedelivery.controller;


import com.singular.coffedelivery.controller.interfaces.AuthControllerInterface;
import com.singular.coffedelivery.dto.usuario.UsuarioDTO;
import com.singular.coffedelivery.entity.UsuarioEntity;
import com.singular.coffedelivery.dto.usuario.UsuarioCreateDTO;
import com.singular.coffedelivery.dto.usuario.LoginDTO;
import com.singular.coffedelivery.exception.RegraDeNegocioException;
import com.singular.coffedelivery.security.TokenService;
import com.singular.coffedelivery.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController implements AuthControllerInterface {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        try {
            UsernamePasswordAuthenticationToken userAuthDTO = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                    loginDTO.getSenha());
            Authentication authentication = authenticationManager.authenticate(userAuthDTO);
            Object principal = authentication.getPrincipal();
            UsuarioEntity usuarioEntity = (UsuarioEntity) principal;
            return new ResponseEntity<>(tokenService.getToken(usuarioEntity), HttpStatus.OK);
        }
        catch (Exception e){
            throw new RegraDeNegocioException("Erro ao realizar o login!");
        }

    }

    @Override
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO){
        return new ResponseEntity<>(usuarioService.create(usuarioCreateDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UsuarioDTO> pegarUsuarioLogado() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLoggedUser(), HttpStatus.OK);
    }
}