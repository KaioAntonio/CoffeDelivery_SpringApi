package com.singular.coffedelivery.controller;

import com.singular.coffedelivery.dto.produto.FileDTO;
import com.singular.coffedelivery.dto.produto.ProdutoCreateDTO;
import com.singular.coffedelivery.dto.produto.ProdutoDTO;
import com.singular.coffedelivery.exception.RegraDeNegocioException;
import com.singular.coffedelivery.service.FileService;
import com.singular.coffedelivery.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("produto")
public class ProdutoController {
    private final ProdutoService produtoService;
    private final FileService fileService;

    @Operation(summary = "Cria um produto", description = "Cria um produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cria um produto no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/criar")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos de entrada: <br>" +
            "<ul>" +
            "<li>**__nome__**: Nome do Produto.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>" +
            "<li>**__descricao__**: Descrição do Produto.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>"+
            "<li>**__tipo__**: Tipo do Produto.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>"+
            "<li>**__preco__**: Preço do Produto.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 algarismo e máxima 4.**</li>" +
            "<li>**O valor mínimo é 0.01 e máximo 9999.99**</li>" +
            "</ul>" +
            "</li>"+
            "</ul>"
    )
    public ResponseEntity<ProdutoDTO> criar(@RequestBody @Valid ProdutoCreateDTO produtoCreateDTO) {
        return new ResponseEntity<>(produtoService.criar(produtoCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Upload da imagem do produto", description = "Upload da imagem do produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Upload da imagem do produto"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/criarImagem")
    public ResponseEntity<FileDTO> uploadImage(@RequestParam("file") MultipartFile file,
                                               @RequestParam("id") Integer idProduto) throws RegraDeNegocioException {
        return new ResponseEntity<>(fileService.store(file,idProduto), HttpStatus.CREATED);
    }

    @Operation(summary = "Busca a imagem do produto em base64", description = "Busca a imagem do produto em base64")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Recupear a imagem do produto em base64"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscarImagem")
    public ResponseEntity<String> buscarImagem(@RequestParam("id") Integer idProduto) throws RegraDeNegocioException {
        return new ResponseEntity<>(fileService.getImage(idProduto), HttpStatus.OK);
    }

    @Operation(summary = "Lista os produtos por id", description = "Lista os produtos por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista os produtos por id"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable("id") Integer idProduto) throws RegraDeNegocioException {
        return new ResponseEntity<>(produtoService.buscarPorId(idProduto), HttpStatus.OK);
    }

    @Operation(summary = "Lista todos os produtos", description = "Lista todos os produtos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista todos os produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar")
    public ResponseEntity<List<ProdutoDTO>> buscar(){
        return new ResponseEntity<>(produtoService.buscar(), HttpStatus.OK);
    }

    @Operation(summary = "Atualiza o produto por id", description = "Atualiza o produto por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza todos os produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos de entrada: " +
            "<ul>" +
            "<li>**__nome__**: Nome do Produto.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>" +
            "<li>**__descricao__**: Descrição do Produto.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>"+
            "<li>**__tipo__**: Tipo do Produto.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>"+
            "<li>**__preco__**: Preço do Produto.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 algarismo e máxima 4.**</li>" +
            "<li>**O valor mínimo é 0.01 e máximo 9999.99**</li>" +
            "</ul>" +
            "</li>"+
            "</ul>"
    )
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@RequestBody @Valid ProdutoCreateDTO produtoCreateDTO,
                                                    @PathVariable("id") Integer idProduto) throws RegraDeNegocioException {
        return new ResponseEntity<>(produtoService.atualizar(produtoCreateDTO, idProduto), HttpStatus.OK);
    }

    @Operation(summary = "Deleta o produto por id", description = "Deleta o produto por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deleta todos os produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Integer idProduto) throws RegraDeNegocioException {
        produtoService.delete(idProduto);
        return ResponseEntity.noContent().build();
    }
}
