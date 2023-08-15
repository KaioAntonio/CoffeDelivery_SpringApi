package com.singular.coffedelivery.controller;


import com.singular.coffedelivery.config.responses.ResultUtilSucess;
import com.singular.coffedelivery.dto.usuario.UsuarioDTO;
import com.singular.coffedelivery.entity.UsuarioEntity;
import com.singular.coffedelivery.dto.usuario.UsuarioCreateDTO;
import com.singular.coffedelivery.dto.usuario.LoginDTO;
import com.singular.coffedelivery.exception.RegraDeNegocioException;
import com.singular.coffedelivery.security.TokenService;
import com.singular.coffedelivery.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Efetuar o login do usuário", description = "Efetua o login do usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Efetua o login do usuário do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<ResultUtilSucess> auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        try {
            UsernamePasswordAuthenticationToken userAuthDTO = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                    loginDTO.getSenha());
            Authentication authentication = authenticationManager.authenticate(userAuthDTO);
            Object principal = authentication.getPrincipal();
            UsuarioEntity usuarioEntity = (UsuarioEntity) principal;
            return new ResponseEntity<>(new ResultUtilSucess(tokenService.getToken(usuarioEntity)), HttpStatus.OK);
        }
        catch (Exception e){
            throw new RegraDeNegocioException("Erro ao realizar o login!");
        }

    }

    @Operation(summary = "Criar um usuário", description = "Criar um usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Criar um usuário no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/criar")
    public ResponseEntity<ResultUtilSucess> criar(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO){
        return new ResponseEntity<>(new ResultUtilSucess(usuarioService.criar(usuarioCreateDTO)), HttpStatus.OK);
    }

    @Operation(summary = "Buscar usuário logado", description = "Buscar usuário logado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Buscar usuário logado do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/usuarioLogado")
    public ResponseEntity<ResultUtilSucess> buscarUsuarioLogado() throws RegraDeNegocioException {
        return new ResponseEntity<>(new ResultUtilSucess(usuarioService.buscarUsuarioLogado()), HttpStatus.OK);
    }
}