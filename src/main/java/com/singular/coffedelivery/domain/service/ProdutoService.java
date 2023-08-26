package com.singular.coffedelivery.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singular.coffedelivery.domain.dto.produto.ProdutoCreateDTO;
import com.singular.coffedelivery.domain.dto.produto.ProdutoDTO;
import com.singular.coffedelivery.domain.entity.ProdutoEntity;
import com.singular.coffedelivery.util.enums.Situacao;
import com.singular.coffedelivery.config.exception.RegraDeNegocioException;
import com.singular.coffedelivery.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;

    public ProdutoDTO criar(ProdutoCreateDTO produtoCreateDTO){
        ProdutoEntity produto = objectMapper.convertValue(produtoCreateDTO, ProdutoEntity.class);
        produto.setSituacao(Situacao.ATIVO);
        produto.setDtCriacao(LocalDateTime.now());
        produtoRepository.save(produto);
        return objectMapper.convertValue(produto,ProdutoDTO.class);
    }

    public ProdutoDTO buscarPorId(Integer id) throws RegraDeNegocioException {
        ProdutoDTO produtoDTO = objectMapper.convertValue(produtoRepository.findById(id)
                .orElseThrow(() ->  new RegraDeNegocioException("Produto não foi encontrado!")),
                ProdutoDTO.class);
        return produtoDTO;

    }

    public List<ProdutoDTO> buscar() {
        return produtoRepository.findAll()
                .stream()
                .map(produtoEntity -> objectMapper.convertValue(produtoEntity, ProdutoDTO.class))
                .collect(Collectors.toList());
    }

    public ProdutoDTO atualizar(ProdutoCreateDTO produtoCreateDTO, Integer idProduto) throws RegraDeNegocioException {
        ProdutoEntity produtoAntigo = objectMapper.convertValue(
                buscarPorId(idProduto), ProdutoEntity.class);

        produtoAntigo.setDescricao(produtoCreateDTO.getDescricao());
        produtoAntigo.setTipo(produtoCreateDTO.getTipo());
        produtoAntigo.setNome(produtoCreateDTO.getNome());
        produtoAntigo.setPreco(produtoAntigo.getPreco());


        ProdutoDTO produtoAtualizado = objectMapper.convertValue(
                produtoRepository.save(produtoAntigo), ProdutoDTO.class);
        return produtoAtualizado;
    }

    public void atualizarQuantidade(Integer idProduto, Integer quantidade) throws RegraDeNegocioException {
        ProdutoDTO produtoDTO = buscarPorId(idProduto);
        ProdutoEntity produto = objectMapper.convertValue(produtoDTO, ProdutoEntity.class);
        produto.setQtProduto(produto.getQtProduto() - quantidade);
        produtoRepository.save(produto);
    }

    public void delete(Integer idProduto) throws RegraDeNegocioException {
        ProdutoEntity produto = objectMapper.convertValue(
                buscarPorId(idProduto), ProdutoEntity.class);
        produto.setSituacao(Situacao.INATIVO);
        produtoRepository.save(produto);

    }

}
