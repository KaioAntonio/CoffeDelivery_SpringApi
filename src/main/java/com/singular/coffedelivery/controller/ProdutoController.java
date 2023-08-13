package com.singular.coffedelivery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.singular.coffedelivery.config.responses.RespostaSucesso;
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
    private final ObjectMapper objectMapper;

    @Operation(summary = "Cria um produto", description = "Cria um produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cria um produto no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping(value = "/criar", consumes = {"multipart/form-data"})
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos de entrada: <br>" +
            "**Obs: o campo de entrada 'produto' recebe uma string e deverá ser passada como esse exemplo: <br>" +
            "- {\"nome\":\"Cafézinho\",\"descricao\":\"Café barato\",\"tipo\":\"quente\",\"preco\":5.50}" +
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
            "<li>**__file__**: Imagem do Produto.</li>" +
            "<ul>"+
            "<li>**Arquivo tem que ser uma imagem.**</li>" +
            "</ul>" +
            "</li>"+
            "</ul>"
    )
    public ResponseEntity<RespostaSucesso> criar(@RequestParam(value = "file", required = false) MultipartFile file,
                                               @RequestPart("produto") @Valid String produto) throws RegraDeNegocioException, JsonProcessingException {
        ProdutoCreateDTO produtoCreateDTO = objectMapper.readValue(produto, ProdutoCreateDTO.class);
        ProdutoDTO produtoDTO = produtoService.criar(produtoCreateDTO);
        fileService.store(file, produtoDTO.getIdProduto());
        return new ResponseEntity<>(new RespostaSucesso(produtoDTO), HttpStatus.CREATED);
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
    public ResponseEntity<RespostaSucesso> buscarPorId(@PathVariable("id") Integer idProduto) throws RegraDeNegocioException {
        ProdutoDTO produtoDTO = produtoService.buscarPorId(idProduto);
        produtoDTO.setImagem(fileService.getImage(idProduto));
        return new ResponseEntity<>(new RespostaSucesso(produtoDTO), HttpStatus.OK);
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
    public ResponseEntity<RespostaSucesso> buscar() throws RegraDeNegocioException {
        List<ProdutoDTO> produtoDTOList = fileService.getProductsAllImages();
        return new ResponseEntity<>(new RespostaSucesso(produtoDTOList), HttpStatus.OK);
    }

    @Operation(summary = "Atualiza o produto por id", description = "Atualiza o produto por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza todos os produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos de entrada: <br>" +
            "**Obs: o campo de entrada 'produto' recebe uma string e deverá ser passada como esse exemplo: <br>" +
            "- {\"nome\":\"Cafézinho\",\"descricao\":\"Café barato\",\"tipo\":\"quente\",\"preco\":5.50}" +
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
            "<li>**__file__**: Imagem do Produto.</li>" +
            "<ul>"+
            "<li>**Arquivo tem que ser uma imagem.**</li>" +
            "</ul>" +
            "</li>"+
            "</ul>"
    )
    @PutMapping(value = "/atualizar", consumes = {"multipart/form-data"})
    public ResponseEntity<RespostaSucesso> atualizar(@RequestPart("produto") @Valid String produto,
                                                    @RequestParam("id") Integer idProduto,
                                                @RequestParam(value = "file", required = false) MultipartFile file) throws RegraDeNegocioException, JsonProcessingException {
        ProdutoCreateDTO produtoCreateDTO = objectMapper.readValue(produto, ProdutoCreateDTO.class);
        produtoService.atualizar(produtoCreateDTO, idProduto);
        FileDTO fileDTO = fileService.store(file, idProduto);
        return new ResponseEntity<>(new RespostaSucesso(fileDTO), HttpStatus.OK);
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
    public ResponseEntity<RespostaSucesso> deletar(@PathVariable("id") Integer idProduto) throws RegraDeNegocioException {
        produtoService.delete(idProduto);
        return new ResponseEntity<>(new RespostaSucesso(null), HttpStatus.NO_CONTENT);
    }
}
