package com.singular.coffedelivery.controller;

import com.singular.coffedelivery.config.exception.RegraDeNegocioException;
import com.singular.coffedelivery.config.responses.ResultUtilSucess;
import com.singular.coffedelivery.domain.dto.pedido.PedidoCreateDTO;
import com.singular.coffedelivery.domain.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos de entrada: <br>" +
            "<ul>" +
            "<li>**__CEP__**: CEP do cliente.</li>" +
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
    @PostMapping("/criar")
    public ResponseEntity<ResultUtilSucess> criar(@RequestBody @Valid PedidoCreateDTO pedidoCreateDTO
    ) throws RegraDeNegocioException {
        return new ResponseEntity<>(new ResultUtilSucess(pedidoService.criar(pedidoCreateDTO)), HttpStatus.CREATED);
    }

}
