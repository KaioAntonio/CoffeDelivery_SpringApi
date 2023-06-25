package com.singular.coffedelivery.controller.interfaces;

import com.singular.coffedelivery.dto.PageDTO;
import com.singular.coffedelivery.dto.usuario.UsuarioCreateDTO;
import com.singular.coffedelivery.dto.usuario.UsuarioDTO;
import com.singular.coffedelivery.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface UsuarioControllerInterface {

    @Operation(summary = "Lista todos os usuários", description = "Lista todos os usuários do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Lista todos os usuários do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/list_users")
    ResponseEntity<PageDTO<UsuarioDTO>> listUsers(Integer pagina, Integer tamanho);

    @Operation(summary = "Lista um único usuário com o parâmetro id passado", description = "Lista um único usuário com o parâmetro id passado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Lista um único usuário com o parâmetro id passado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/list_by_id/{id}")
    ResponseEntity<UsuarioDTO> findById(@PathVariable("id") Integer idUsuario) throws RegraDeNegocioException;

    @Operation(summary = "Cria um usuário", description = "Cria um usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cria um usuário no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/create")
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
    ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO);


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
    @PutMapping("/update/{id}")
    ResponseEntity<UsuarioDTO> updateUser(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO, @PathVariable("id") Integer idUsuario) throws RegraDeNegocioException;

    @Operation(summary = "Deleta um usuário", description = "Deleta um usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Deleta um usuário no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable("id") Integer idProduto) throws RegraDeNegocioException;
}
