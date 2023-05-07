package com.singular.coffedelivery.controller.interfaces;


import com.singular.coffedelivery.dto.produto.FileDTO;
import com.singular.coffedelivery.dto.produto.ProdutoCreateDTO;
import com.singular.coffedelivery.dto.produto.ProdutoDTO;
import com.singular.coffedelivery.exception.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

public interface ProdutoControllerInterface {

    @Operation(summary = "Cria um produto", description = "Cria um produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cria um produto no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping()
    ResponseEntity<ProdutoDTO> create(@RequestBody @Valid ProdutoCreateDTO produtoCreateDTO);

    @Operation(summary = "Upload da imagem do produto", description = "Upload da imagem do produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Upload da imagem do produto"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/uploadImage")
    ResponseEntity<FileDTO> uploadImage(@RequestParam("file") MultipartFile file,
                                        @RequestParam("id") Integer idProduto) throws RegraDeNegocioException;


    @Operation(summary = "Recupear a imagem do produto em base64", description = "Recupear a imagem do produto em base64")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Recupear a imagem do produto em base64"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/image")
    ResponseEntity<String> recuperarImagem(@RequestParam("id") Integer idProduto) throws RegraDeNegocioException;

    @Operation(summary = "Lista os produtos por id", description = "Lista os produtos por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista os produtos por id"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<ProdutoDTO> listProductsById(@PathVariable("id") Integer idProduto) throws RegraDeNegocioException;

    @Operation(summary = "Lista todos os produtos", description = "Lista todos os produtos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista todos os produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping()
    ResponseEntity<List<ProdutoDTO>> listAllProducts();

    @Operation(summary = "Atualiza o produto por id", description = "Atualiza o produto por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza todos os produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<ProdutoDTO> updateProduct(@RequestBody @Valid ProdutoCreateDTO produtoCreateDTO,
                                                    @PathVariable("id") Integer idProduto) throws RegraDeNegocioException;

    @Operation(summary = "Deleta o produto por id", description = "Deleta o produto por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deleta todos os produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer idProduto) throws RegraDeNegocioException;
}
