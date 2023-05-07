package com.singular.coffedelivery.controller;

import com.singular.coffedelivery.controller.interfaces.ProdutoControllerInterface;
import com.singular.coffedelivery.dto.produto.FileDTO;
import com.singular.coffedelivery.dto.produto.ProdutoCreateDTO;
import com.singular.coffedelivery.dto.produto.ProdutoDTO;
import com.singular.coffedelivery.exception.RegraDeNegocioException;
import com.singular.coffedelivery.service.FileService;
import com.singular.coffedelivery.service.ProdutoService;
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
public class ProdutoController implements ProdutoControllerInterface {
    private final ProdutoService produtoService;
    private final FileService fileService;

    @Override
    public ResponseEntity<ProdutoDTO> create(@RequestBody @Valid ProdutoCreateDTO produtoCreateDTO) {
        return new ResponseEntity<>(produtoService.create(produtoCreateDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FileDTO> uploadImage(@RequestParam("file") MultipartFile file,
                                               @RequestParam("id") Integer idProduto) throws RegraDeNegocioException {
        return new ResponseEntity<>(fileService.store(file,idProduto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> recuperarImagem(@RequestParam("id") Integer idProduto) throws RegraDeNegocioException {
        return new ResponseEntity<>(fileService.getImage(idProduto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProdutoDTO> listProductsById(@PathVariable("id") Integer idProduto) throws RegraDeNegocioException {
        return new ResponseEntity<>(produtoService.findById(idProduto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ProdutoDTO>> listAllProducts(){
        return new ResponseEntity<>(produtoService.list(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProdutoDTO> updateProduct(@RequestBody @Valid ProdutoCreateDTO produtoCreateDTO,
                                                    @PathVariable("id") Integer idProduto) throws RegraDeNegocioException {
        return new ResponseEntity<>(produtoService.update(produtoCreateDTO, idProduto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer idProduto) throws RegraDeNegocioException {
        produtoService.delete(idProduto);
        return ResponseEntity.noContent().build();
    }


}
