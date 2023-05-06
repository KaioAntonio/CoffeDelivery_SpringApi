package com.singular.coffedelivery.controller;

import com.singular.coffedelivery.dto.produto.ProdutoCreateDTO;
import com.singular.coffedelivery.dto.produto.ProdutoDTO;
import com.singular.coffedelivery.service.FileService;
import com.singular.coffedelivery.service.ProdutoService;
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
@RequestMapping("produto")
public class ProdutoController {
    private final ProdutoService produtoService;
    private final FileService fileService;

    @PostMapping()
    ResponseEntity<ProdutoDTO> create(@RequestBody @Valid ProdutoCreateDTO produtoCreateDTO) {
        return new ResponseEntity<>(produtoService.create(produtoCreateDTO), HttpStatus.OK);
    }


}
