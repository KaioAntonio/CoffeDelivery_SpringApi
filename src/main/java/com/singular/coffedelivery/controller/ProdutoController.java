package com.singular.coffedelivery.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.singular.coffedelivery.config.responses.ResultUtilSucess;
import com.singular.coffedelivery.domain.dto.produto.FileDTO;
import com.singular.coffedelivery.domain.dto.produto.ProdutoCreateDTO;
import com.singular.coffedelivery.domain.dto.produto.ProdutoDTO;
import com.singular.coffedelivery.config.exception.RegraDeNegocioException;
import com.singular.coffedelivery.domain.service.FileService;
import com.singular.coffedelivery.domain.service.ProdutoService;
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
    @PostMapping(value = "/criar", consumes = {"multipart/form-data"})
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
            "<li>**__qtProduto__**: Quantidade em estoque do Produto.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 algarismo e máxima 4.**</li>" +
            "<li>**O valor mínimo é 0 e máximo 9999**</li>" +
            "</ul>" +
            "<li>**__file__**: Imagem do Produto.</li>" +
            "<ul>"+
            "<li>**Arquivo tem que ser uma imagem.**</li>" +
            "</ul>" +
            "</li>"+
            "</ul>"
    )
    public ResponseEntity<ResultUtilSucess> criar(@RequestParam(value = "file", required = false) MultipartFile file,
                                                  @RequestParam("nome") @Valid String nome,
                                                  @RequestParam("descricao") @Valid String descricao,
                                                  @RequestParam("tipo") @Valid String tipo,
                                                  @RequestParam("preco") @Valid Double preco,
                                                  @RequestParam("qtProduto") @Valid Integer qtProduto
                                                  ) throws RegraDeNegocioException {
        ProdutoCreateDTO produtoCreateDTO = new ProdutoCreateDTO();
        produtoCreateDTO.setNome(nome);
        produtoCreateDTO.setDescricao(descricao);
        produtoCreateDTO.setPreco(preco);
        produtoCreateDTO.setTipo(tipo);
        produtoCreateDTO.setQtProduto(qtProduto);
        ProdutoDTO produtoDTO = produtoService.criar(produtoCreateDTO);
        FileDTO fileDTO = fileService.store(file, produtoDTO.getIdProduto());
        return new ResponseEntity<>(new ResultUtilSucess(fileDTO), HttpStatus.CREATED);
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
    public ResponseEntity<ResultUtilSucess> buscarPorId(@PathVariable("id") Integer idProduto) throws RegraDeNegocioException {
        ProdutoDTO produtoDTO = produtoService.buscarPorId(idProduto);
        produtoDTO.setImagem(fileService.getImage(idProduto));
        return new ResponseEntity<>(new ResultUtilSucess(produtoDTO), HttpStatus.OK);
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
    public ResponseEntity<ResultUtilSucess> buscar() throws RegraDeNegocioException {
        List<ProdutoDTO> produtoDTOList = fileService.getProductsAllImages();
        return new ResponseEntity<>(new ResultUtilSucess(produtoDTOList), HttpStatus.OK);
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
            "<li>**__qtProduto__**: Quantidade em estoque do Produto.</li>" +
            "<ul>"+
            "<li>**Quantidade mínima de 1 algarismo e máxima 4.**</li>" +
            "<li>**O valor mínimo é 0 e máximo 9999**</li>" +
            "</ul>" +
            "<li>**__file__**: Imagem do Produto.</li>" +
            "<ul>"+
            "<li>**Arquivo tem que ser uma imagem.**</li>" +
            "</ul>" +
            "</li>"+
            "</ul>"
    )
    @PutMapping(value = "/atualizar", consumes = {"multipart/form-data"})
    public ResponseEntity<ResultUtilSucess> atualizar(@RequestParam("nome") @Valid String nome,
                                                      @RequestParam("descricao") @Valid String descricao,
                                                      @RequestParam("tipo") @Valid String tipo,
                                                      @RequestParam("preco") @Valid Double preco,
                                                      @RequestParam("qtProduto") @Valid Integer qtProduto,
                                                      @RequestParam("id") Integer idProduto,
                                                      @RequestParam(value = "file", required = false) MultipartFile file) throws RegraDeNegocioException, JsonProcessingException {
        ProdutoCreateDTO produtoCreateDTO = new ProdutoCreateDTO();
        produtoCreateDTO.setNome(nome);
        produtoCreateDTO.setDescricao(descricao);
        produtoCreateDTO.setPreco(preco);
        produtoCreateDTO.setTipo(tipo);
        produtoCreateDTO.setQtProduto(qtProduto);
        produtoService.atualizar(produtoCreateDTO, idProduto);
        FileDTO fileDTO = fileService.store(file, idProduto);
        return new ResponseEntity<>(new ResultUtilSucess(fileDTO), HttpStatus.OK);
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
    public ResponseEntity<ResultUtilSucess> deletar(@PathVariable("id") Integer idProduto) throws RegraDeNegocioException {
        produtoService.delete(idProduto);
        return new ResponseEntity<>(new ResultUtilSucess(null), HttpStatus.NO_CONTENT);
    }
}
