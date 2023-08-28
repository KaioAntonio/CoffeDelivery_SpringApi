package com.singular.coffedelivery.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singular.coffedelivery.config.exception.RegraDeNegocioException;
import com.singular.coffedelivery.domain.dto.pedido.PedidoCreateDTO;
import com.singular.coffedelivery.domain.dto.pedido.PedidoDTO;
import com.singular.coffedelivery.domain.entity.PedidoEntity;
import com.singular.coffedelivery.domain.entity.ProdutoEntity;
import com.singular.coffedelivery.domain.repository.PedidoRepository;
import com.singular.coffedelivery.domain.vo.DadosCliente;
import com.singular.coffedelivery.domain.vo.Endereco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Double vlTotal = 0.0;
        List<ProdutoEntity> produtos = new ArrayList<>();
        for(int i = 0; i < pedidoCreateDTO.getProduto().size(); i++) {
            ProdutoEntity produto = (objectMapper.convertValue(produtoService.buscarPorId(pedidoCreateDTO.getProduto().get(i).getIdProduto()), ProdutoEntity.class));
            produtoService.atualizarQuantidade(produto.getIdProduto(), pedidoCreateDTO.getProduto().get(i).getQtProdutos());
            vlTotal += (pedidoCreateDTO.getProduto().get(i).getQtProdutos() * produto.getPreco());
            produtos.add(produto);
        }
        pedido.setProdutos(new HashSet<>(produtos));
        pedido.setDadosCliente(new DadosCliente(pedidoCreateDTO.getTelefone(), pedidoCreateDTO.getNome()));
        pedido.setEndereco(new Endereco(pedidoCreateDTO.getCep(), pedidoCreateDTO.getRua(), pedidoCreateDTO.getNumero(),
                pedidoCreateDTO.getComplemento(), pedidoCreateDTO.getBairro(), pedidoCreateDTO.getCidade(),
                pedidoCreateDTO.getUf()));
        pedido.setVlTotal(vlTotal);
        pedidoRepository.save(pedido);
        PedidoDTO pedidoDTO = objectMapper.convertValue(pedido, PedidoDTO.class);
        pedidoDTO.setNome(pedidoCreateDTO.getNome());
        pedidoDTO.setTelefone(pedidoCreateDTO.getTelefone());
        pedidoDTO.setCep(pedidoCreateDTO.getCep());
        pedidoDTO.setCidade(pedidoCreateDTO.getCidade());
        pedidoDTO.setBairro(pedidoCreateDTO.getBairro());
        pedidoDTO.setUf(pedidoCreateDTO.getUf());
        pedidoDTO.setComplemento(pedidoCreateDTO.getComplemento());
        pedidoDTO.setBairro(pedidoCreateDTO.getBairro());
        pedidoDTO.setRua(pedidoCreateDTO.getRua());
        pedidoDTO.setNumero(pedidoCreateDTO.getNumero());
        pedidoDTO.setVlTotal(vlTotal);
        return pedidoDTO;
    }

}
