package com.singular.coffedelivery.controller;

import com.singular.coffedelivery.config.exception.RegraDeNegocioException;
import com.singular.coffedelivery.config.responses.ResultUtilSucess;
import com.singular.coffedelivery.domain.dto.PageDTO;
import com.singular.coffedelivery.domain.dto.pedido.PedidoCreateDTO;
import com.singular.coffedelivery.domain.dto.pedido.PedidoDTO;
import com.singular.coffedelivery.domain.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    @Operation(summary = "Lista todos os pedidos", description = "Lista todos os pedidos do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Lista todos os pedidos do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/buscar")
    public ResponseEntity<ResultUtilSucess> buscar(
            @RequestParam(value = "0", defaultValue = "0") Integer pagina,
            @RequestParam(value = "15", defaultValue = "15") Integer tamanho
    ) {
        return new ResponseEntity<>(new ResultUtilSucess(pedidoService.buscar(pagina, tamanho)), HttpStatus.OK);
    }


    @Operation(summary = "Criar pedidos", description = "Cria pedidos no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cria pedidos no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Campos de entrada: <br>" +
            "<ul>" +
            "<li>**__CEP__**: CEP do Cliente.</li>" +
            "<ul>" +
            "<li>**O valor tem que seguir a máscara: xxxxx-xxx**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>" +
            "<li>**__rua__**: Rua do Cliente.</li>" +
            "<ul>" +
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>" +
            "<li>**__numero__**: Número da casa do Cliente.</li>" +
            "<ul>" +
            "<li>**Quantidade mínima de 1 algarismo e máxima 4.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "</li>" +
            "<li>**__complemento__**: Complemento do Endereço do Cliente.</li>" +
            "<ul>" +
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "</ul>" +
            "<li>**__bairro__**: Bairro do Cliente.</li>" +
            "<ul>" +
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "<li>**__cidade__**: Cidade do Cliente.</li>" +
            "<ul>" +
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "<li>**__uf__**: Unidade Federativa.</li>" +
            "<ul>" +
            "<li>**Quantidade mínima de 1 character e máxima 2.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "<li>**__telefone__**: Telefone do Cliente.</li>" +
            "<ul>" +
            "<li>**O formato deve ser (XX) X XXXX-XXXX.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "<li>**__nome__**: Nome do Cliente.</li>" +
            "<ul>" +
            "<li>**Quantidade mínima de 1 character e máxima 255.**</li>" +
            "<li>**O campo não pode ser vazio**</li>" +
            "</ul>" +
            "<li>**__formaPagamento__**: Forma de Pagamento.</li>" +
            "<ul>" +
            "<li>A forma de pagamento é um enumerador e só pode seguir o seguintes valores:</li>" +
            "<li>**DINHEIRO**</li>" +
            "<li>**DEBITO**</li>" +
            "<li>**CREDITO**</li>" +
            "</ul>" +
            "<li>**__produto__**: Produtos a serem comprados.</li>" +
            "<ul>" +
            "<li>Lista de produtos a serem comprados com os seguintes campos requeridos:</li>" +
            "<li>**qtProdutos (Inteiro)**</li>" +
            "<li>**idProduto (Inteiro)**</li>" +
            "</ul>" +
            "</li>" +
            "</ul>"
    )
    @PostMapping("/criar")
    public ResponseEntity<ResultUtilSucess> criar(@RequestBody @Valid PedidoCreateDTO pedidoCreateDTO
    ) throws RegraDeNegocioException {
        return new ResponseEntity<>(new ResultUtilSucess(pedidoService.criar(pedidoCreateDTO)), HttpStatus.CREATED);
    }


}
