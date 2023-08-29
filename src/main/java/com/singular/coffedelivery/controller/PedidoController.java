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

    @PostMapping("/criar")
    public ResponseEntity<ResultUtilSucess> criar(@RequestBody @Valid PedidoCreateDTO pedidoCreateDTO
    ) throws RegraDeNegocioException {
        return new ResponseEntity<>(new ResultUtilSucess(pedidoService.criar(pedidoCreateDTO)), HttpStatus.CREATED);
    }

}
