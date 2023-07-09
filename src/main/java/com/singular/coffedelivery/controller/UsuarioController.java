package com.singular.coffedelivery.controller;

import com.singular.coffedelivery.dto.PageDTO;
import com.singular.coffedelivery.dto.usuario.UsuarioCreateDTO;
import com.singular.coffedelivery.dto.usuario.UsuarioDTO;
import com.singular.coffedelivery.exception.RegraDeNegocioException;
import com.singular.coffedelivery.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @Operation(summary = "Lista todos os usuários", description = "Lista todos os usuários do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Lista todos os usuários do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar")
    public ResponseEntity<PageDTO<UsuarioDTO>> buscar(@NotNull Integer pagina, @NotNull Integer tamanho) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.buscar(pagina,tamanho), HttpStatus.OK);
    }

    @Operation(summary = "Lista um único usuário com o parâmetro id passado", description = "Lista um único usuário com o parâmetro id passado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Lista um único usuário com o parâmetro id passado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable("id") Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.buscarPorIdDto(idUsuario),HttpStatus.OK);
    }

    @Operation(summary = "Cria um usuário", description = "Cria um usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cria um usuário no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/criar")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos de entrada: <br>" +
            "<ul>" +
            "<li>**__email__**: Email do Usuário.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>" +
            "<li>**__nome__**: Nome do Usuário.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>"+
            "<li>**__Senha__**: Senha do Usuário.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>"+
            "</ul>")
    public ResponseEntity<UsuarioDTO> criar(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) {
        return new ResponseEntity<>(usuarioService.criar(usuarioCreateDTO), HttpStatus.CREATED);
    }
    @Operation(summary = "Atualiza um usuário", description = "Atualiza um usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Atualiza um usuário no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos de entrada: <br>" +
            "<ul>" +
            "<li>**__email__**: Email do Usuário.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>" +
            "<li>**__nome__**: Nome do Usuário.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>"+
            "<li>**__Senha__**: Senha do Usuário.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>"+
            "</ul>")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO, @PathVariable("id") Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.atualizar(idUsuario,usuarioCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Deleta um usuário", description = "Deleta um usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Deleta um usuário no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Integer idProduto) throws RegraDeNegocioException {
        usuarioService.deletar(idProduto);
        return ResponseEntity.ok().build();
    }
}
