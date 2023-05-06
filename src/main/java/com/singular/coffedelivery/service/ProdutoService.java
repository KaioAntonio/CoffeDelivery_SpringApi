package com.singular.coffedelivery.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singular.coffedelivery.dto.produto.ProdutoCreateDTO;
import com.singular.coffedelivery.dto.produto.ProdutoDTO;
import com.singular.coffedelivery.entity.ProdutoEntity;
import com.singular.coffedelivery.enums.Situacao;
import com.singular.coffedelivery.exception.RegraDeNegocioException;
import com.singular.coffedelivery.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;

    public ProdutoDTO create(ProdutoCreateDTO produtoCreateDTO){
        ProdutoEntity produto = objectMapper.convertValue(produtoCreateDTO, ProdutoEntity.class);
        produto.setSituacao(Situacao.ATIVO);
        produtoRepository.save(produto);
        return objectMapper.convertValue(produto,ProdutoDTO.class);
    }

    public ProdutoDTO findById(Integer id) throws RegraDeNegocioException {
        ProdutoDTO produtoDTO = objectMapper.convertValue(produtoRepository.findById(id)
                .orElseThrow(() ->  new RegraDeNegocioException("Produto não foi encontrado!")),
                ProdutoDTO.class);
        return produtoDTO;

    }

    public ProdutoDTO update(ProdutoCreateDTO produtoCreateDTO, Integer idProduto) throws RegraDeNegocioException {
        ProdutoEntity produtoAntigo = objectMapper.convertValue(
                findById(idProduto), ProdutoEntity.class);

        produtoAntigo.setDescricao(produtoCreateDTO.getDescricao());
        produtoAntigo.setTipo(produtoCreateDTO.getTipo());
        produtoAntigo.setNome(produtoCreateDTO.getNome());
        produtoAntigo.setPreco(produtoAntigo.getPreco());

        ProdutoDTO produtoAtualizado = objectMapper.convertValue(
                produtoRepository.save(produtoAntigo), ProdutoDTO.class);
        return produtoAtualizado;
    }

    public void delete(Integer idProduto) throws RegraDeNegocioException {
        ProdutoEntity produto = objectMapper.convertValue(
                findById(idProduto), ProdutoEntity.class);
        produto.setSituacao(Situacao.INATIVO);
        produtoRepository.save(produto);

    }

}
