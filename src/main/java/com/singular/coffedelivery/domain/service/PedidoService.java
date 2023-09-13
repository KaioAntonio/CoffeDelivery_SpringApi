package com.singular.coffedelivery.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singular.coffedelivery.config.exception.RegraDeNegocioException;
import com.singular.coffedelivery.domain.dto.PageDTO;
import com.singular.coffedelivery.domain.dto.pedido.PedidoCreateDTO;
import com.singular.coffedelivery.domain.dto.pedido.PedidoDTO;
import com.singular.coffedelivery.domain.dto.pedido.PedidoGetDTO;
import com.singular.coffedelivery.domain.dto.produto.ProdutoIdQuantidadeDTO;
import com.singular.coffedelivery.domain.entity.PedidoEntity;
import com.singular.coffedelivery.domain.entity.ProdutoEntity;
import com.singular.coffedelivery.domain.repository.PedidoRepository;
import com.singular.coffedelivery.domain.vo.DadosCliente;
import com.singular.coffedelivery.domain.vo.Endereco;
import com.singular.coffedelivery.util.enums.Situacao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        double vlTotal = 0.0;
        List<ProdutoEntity> produtos = new ArrayList<>();
        List<ProdutoIdQuantidadeDTO> produtoIdQuantidadeDTOList = new ArrayList<>();

        for(int i = 0; i < pedidoCreateDTO.getProduto().size(); i++) {
            Integer qtProdutos = pedidoCreateDTO.getProduto().get(i).getQtProdutos();
            Integer idProduto = pedidoCreateDTO.getProduto().get(i).getIdProduto();
            ProdutoEntity produto = (objectMapper.convertValue(produtoService.buscarPorId(idProduto), ProdutoEntity.class));
            produtoService.atualizarQuantidade(idProduto, qtProdutos);
            vlTotal += (qtProdutos * produto.getPreco());
            produtos.add(produto);
            produtoIdQuantidadeDTOList.add(new ProdutoIdQuantidadeDTO(qtProdutos, idProduto));
        }

        pedido.setProdutos(new HashSet<>(produtos));
        pedido.setDadosCliente(new DadosCliente(pedidoCreateDTO.getTelefone(), pedidoCreateDTO.getNome()));
        pedido.setEndereco(new Endereco(pedidoCreateDTO.getCep(), pedidoCreateDTO.getRua(), pedidoCreateDTO.getNumero(),
                pedidoCreateDTO.getComplemento(), pedidoCreateDTO.getBairro(), pedidoCreateDTO.getCidade(),
                pedidoCreateDTO.getUf()));
        pedido.setVlTotal(vlTotal);
        pedido.setSituacao(Situacao.EM_ANALISE);
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
        pedidoDTO.setProduto(produtoIdQuantidadeDTOList);
        pedidoDTO.setFormaPagamento(pedidoCreateDTO.getFormaPagamento());
        pedidoDTO.setSituacao(Situacao.EM_ANALISE);
        return pedidoDTO;
    }

    public PageDTO<PedidoGetDTO> buscar(Integer pagina, Integer tamanho) {
        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);
        Page<PedidoEntity> pedido = pedidoRepository.findAll(solicitacaoPagina);
        List<PedidoGetDTO> pedidoDTOS = pedido.getContent().stream()
                .map(p -> objectMapper.convertValue(p, PedidoGetDTO.class))
                .collect(Collectors.toList());
        for (int i = 0; i < pedidoDTOS.size(); i++){
            pedidoDTOS.get(i).setProdutos(buscarPedidoComProdutos(pedidoDTOS.get(i).getIdPedido()));
        }

        return new PageDTO<>(pedido.getTotalElements(),
                pedido.getTotalPages(),
                pagina,
                tamanho,
                pedidoDTOS);

    }

    public void enviarPedido(Integer idPedido) throws RegraDeNegocioException {
        alterarSituacaoPedido(idPedido, Situacao.ENVIADO);
    }

    public void finalizarPedido(Integer idPedido) throws RegraDeNegocioException {
        alterarSituacaoPedido(idPedido, Situacao.FINALIZADO);
    }

    public void cancelarPedido(Integer idPedido) throws RegraDeNegocioException {
        alterarSituacaoPedido(idPedido, Situacao.CANCELADO);
    }

    private void alterarSituacaoPedido(Integer idPedido, Situacao situacao) throws RegraDeNegocioException {
        PedidoEntity pedido = buscarPorId(idPedido);
        pedido.setSituacao(situacao);
        pedidoRepository.save(pedido);
    }

    public PedidoEntity buscarPorId(Integer idPedido) throws RegraDeNegocioException {
        return objectMapper.convertValue(pedidoRepository.findById(idPedido)
                        .orElseThrow(() ->  new RegraDeNegocioException("Pedido não foi encontrado!")),
                PedidoEntity.class);
    }

    public Set<ProdutoEntity> buscarPedidoComProdutos(Integer idPedido) {
        PedidoEntity pedido = pedidoRepository.findById(idPedido).orElse(null);
        if (pedido != null) {
            return new HashSet<>(pedido.getProdutos());
        }
        return null;
    }

}
