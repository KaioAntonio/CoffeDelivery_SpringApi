package com.singular.coffedelivery.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singular.coffedelivery.config.exception.RegraDeNegocioException;
import com.singular.coffedelivery.domain.dto.pedido.PedidoCreateDTO;
import com.singular.coffedelivery.domain.dto.pedido.PedidoDTO;
import com.singular.coffedelivery.domain.dto.produto.ProdutoDTO;
import com.singular.coffedelivery.domain.entity.PedidoEntity;
import com.singular.coffedelivery.domain.entity.ProdutoEntity;
import com.singular.coffedelivery.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final ObjectMapper objectMapper;

    @Transactional
    public PedidoDTO criar(PedidoCreateDTO pedidoCreateDTO) throws RegraDeNegocioException {
        PedidoEntity pedido = objectMapper.convertValue(pedidoCreateDTO, PedidoEntity.class);
        pedido.setDataPedido(LocalDateTime.now());

        for(int i = 0; i < pedidoCreateDTO.getProduto().size(); i++){
            ProdutoEntity produto = (objectMapper.convertValue(produtoService.buscarPorId(pedidoCreateDTO.getProduto().get(i).getIdProduto()), ProdutoEntity.class));
            produtoService.atualizarQuantidade(produto.getIdProduto(), pedidoCreateDTO.getProduto().get(i).getQtProdutos());
        }
        pedidoRepository.save(pedido);
        return objectMapper.convertValue(pedido, PedidoDTO.class);
    }

}
